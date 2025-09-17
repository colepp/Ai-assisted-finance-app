package colepp.app.wealthwisebackend.finance.services;

import colepp.app.wealthwisebackend.common.services.JwtService;
import colepp.app.wealthwisebackend.finance.config.PlaidCredentials;

import colepp.app.wealthwisebackend.finance.dtos.*;
import colepp.app.wealthwisebackend.finance.exceptions.FailedPlaidRequest;

import colepp.app.wealthwisebackend.finance.tools.FinanceTools;
import colepp.app.wealthwisebackend.users.entities.User;
import colepp.app.wealthwisebackend.users.entities.UserBanking;
import colepp.app.wealthwisebackend.users.entities.UserLinkStatus;
import colepp.app.wealthwisebackend.users.excpetions.UserInfoNotFound;
import colepp.app.wealthwisebackend.users.excpetions.UserNotFoundException;
import colepp.app.wealthwisebackend.users.repositories.UserBankingRepository;
import colepp.app.wealthwisebackend.users.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;


import java.net.URI;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaidFinanceService {

    private final PlaidCredentials plaidCredentials;
    private final HttpClient httpClient;
    private final Jedis jedis;

    private final UserRepository userRepository;
    private final UserBankingRepository userBankingRepository;


    private final ObjectMapper objectMapper;


    private final int expireTime = 1800;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final LocalDate defaultStartDate = LocalDate.now().minusDays(30);

    private final JwtService jwtService;


    public String createLinkToken(String token) throws Exception{
        var user = createPlaidUser(jwtService.getEmailFromToken(jwtService.formatToken(token)));
        var linkTokenRequest = createLinkTokenRequest(user);
        var response = sendPostRequest(objectMapper.writeValueAsString(linkTokenRequest),"link/token/create");
        return response.body();
    }

    public void exchangePublicToken(String publicToken, String token) throws Exception {
        var user = userRepository.findByEmail(jwtService.getEmailFromToken(jwtService.formatToken(publicToken))).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User Not Found");
        }
        var userBankingInfo = getUserBankInformation(user);

        var response = sendPostRequest(objectMapper.writeValueAsString(createExchangePublicToken(publicToken)), "item/public_token/exchange");
        if (response == null) {
            throw new FailedPlaidRequest("Could not create token");
        }
        ExchangePublicTokenResponse responseMap = objectMapper.readValue(response.body(), ExchangePublicTokenResponse.class);
        userBankingInfo.setAccessToken(responseMap.getAccessToken());
        userBankingInfo.setAccountLinkStatus(UserLinkStatus.LINKED);
        userRepository.save(user);
    }

    public AccountInformationResponse getAccountInformation(String token) throws JsonProcessingException {
        var user  = userRepository.findByEmail(jwtService.getEmailFromToken(jwtService.formatToken(token))).orElse(null);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        var userId = user.getId();
        String redisRequest = jedis.get("user-id-" + userId);
        if (redisRequest == null) {
            var userBankingInfo = userBankingRepository.findById(userId).orElse(null);
            if(userBankingInfo == null){
                throw new UserNotFoundException("User banking information found");
            }
            var accountInfoRequest = AccountInformationRequest
                    .builder()
                    .accessToken(userBankingInfo.getAccessToken())
                    .clientId(plaidCredentials.getClientId())
                    .secret(plaidCredentials.getSecretKey())
                    .build();
            var response = sendPostRequest(objectMapper.writeValueAsString(accountInfoRequest),"auth/get");
            if(response == null){
                throw new FailedPlaidRequest("Could not get account information");
            }
            System.out.println("ACCOUNT INFORMATION: ");
            System.out.println(response.body());
            jedis.set("user-id-" + userId, response.body());
            jedis.expire("user-id-" + userId, expireTime);
            var mapped = objectMapper.readValue(response.body(), AccountInformationResponse.class);
            System.out.println("Mapped value: ");
            System.out.println(mapped);
            return mapped;
        }
        return objectMapper.readValue(redisRequest, AccountInformationResponse.class);
    }

    public AccountTransactionInformationResponse getTransactionalInformation(String token) throws JsonProcessingException {
        var user = userRepository.findByEmail(jwtService.getEmailFromToken(jwtService.formatToken(token))).orElse(null);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        var userId = user.getId();
        String redisRequest = jedis.get("user-id-transactions-" + userId);
        if (redisRequest == null) {
            var userBankingInfo = userBankingRepository.findById(userId).orElse(null);
            if(userBankingInfo == null){
                throw new UserNotFoundException("User banking information found");
            }
            var transactionInfoRequest = AccountTransactionInformationRequest.builder()
                .accessToken(userBankingInfo.getAccessToken())
                .clientId(plaidCredentials.getClientId())
                .secret(plaidCredentials.getSecretKey())
                .startDate(formatter.format(defaultStartDate))
                .endDate(formatter.format(LocalDate.now()))
                .build();
            System.out.println(transactionInfoRequest);
            var response = sendPostRequest(objectMapper.writeValueAsString(transactionInfoRequest),"/transactions/get");
            if(response == null){
                throw new FailedPlaidRequest("Could not get transaction information");
            }
            AccountTransactionInformationResponse transactions = objectMapper.readValue(response.body(), AccountTransactionInformationResponse.class);
            jedis.set("user-id-transactions-" + userId, objectMapper.writeValueAsString(transactions));
            jedis.expire("user-id-transactions-" + userId, expireTime);
            return transactions;
        }
        return objectMapper.readValue(redisRequest, AccountTransactionInformationResponse.class);
    }

    public MonthlyFinanceSummary createMonthlyFinanceSummary(String token) throws JsonProcessingException {
        var transactions = getTransactionalInformation(token);
        var accounts = getAccountInformation(token);
        var sortedCategories = FinanceTools.categorizeAllTransactions(transactions.getTransactions());
        System.out.println(sortedCategories);

        return new MonthlyFinanceSummary(accounts,transactions);
    }

    public UserBanking getUserBankInformation(User user){
        UserBanking userBankingInfo = userBankingRepository.findById(user.getId()).orElse(null);
        if(userBankingInfo == null){
            throw new UserInfoNotFound("User not found");
        }
        return userBankingInfo;
    }

    public HttpResponse<String> sendPostRequest(String requestJson,String url){
        try{
            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(new URI(plaidCredentials.getPlaidUrl() + url))
                    .header("Content-Type","application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestJson))
                    .build();
            return httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());}
        catch (Exception e){
            return null;
        }
    }

    public PlaidUserInfo createPlaidUser(String userEmail){

        PlaidUserInfo userInfo = new PlaidUserInfo();

        var user = userRepository.findByEmail(userEmail).orElse(null);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        userInfo.setClientUserId(String.valueOf(user.getId()));
        userInfo.setPhoneNumber(user.getPhoneNumber());
        return userInfo;
    }

    public CreateLinkTokenRequest createLinkTokenRequest(PlaidUserInfo user){
        return CreateLinkTokenRequest
                .builder()
                .clientId(plaidCredentials.getClientId())
                .secret(plaidCredentials.getSecretKey())
                .clientName("WealthWiseV2")
                .products(plaidCredentials.getProducts())
                .countryCodes(plaidCredentials.getCountryCodes())
                .language("en")
                .user(user)
                .build();
    }

    public ExchangePublicToken createExchangePublicToken(String publicToken){
        return ExchangePublicToken
                .builder()
                .secret(plaidCredentials.getSecretKey())
                .clientId(plaidCredentials.getClientId())
                .publicToken(publicToken)
                .build();
    }
}
