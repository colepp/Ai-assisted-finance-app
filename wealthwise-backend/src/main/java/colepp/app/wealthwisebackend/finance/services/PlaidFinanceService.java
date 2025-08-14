package colepp.app.wealthwisebackend.finance.services;

import colepp.app.wealthwisebackend.common.services.JwtService;
import colepp.app.wealthwisebackend.finance.config.PlaidCredentials;

import colepp.app.wealthwisebackend.finance.dtos.*;
import colepp.app.wealthwisebackend.finance.exceptions.FailedPlaidRequest;

import colepp.app.wealthwisebackend.users.entities.UserLinkStatus;
import colepp.app.wealthwisebackend.users.excpetions.UserNotFoundException;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaidFinanceService {

    private final PlaidCredentials plaidCredentials;
    private final UserRepository userRepository;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final Jedis jedis;
    private final int expireTime = 1800;
    private final JwtService jwtService;

    public String createLinkToken(String token) throws Exception{
        var user = createPlaidUser(jwtService.getEmailFromToken(token));
        var linkTokenRequest = createLinkTokenRequest(user);
        var response = sendPostRequest(objectMapper.writeValueAsString(linkTokenRequest),"link/token/create");
        return response.body();
    }

    public void exchangePublicToken(String publicToken, String token) throws Exception {

        var user = userRepository.findByEmail(jwtService.getEmailFromToken(token)).orElse(null);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        var response = sendPostRequest(objectMapper.writeValueAsString(createExchangePublicToken(publicToken)), "item/public_token/exchange");
        if (response == null) {
            throw new FailedPlaidRequest("Could not create token");
        }
        ExchangePublicTokenResponseDto responseMap = objectMapper.readValue(response.body(), ExchangePublicTokenResponseDto.class);
        user.setAccessToken(responseMap.getAccessToken());
        user.setAccountLinkStatus(UserLinkStatus.LINKED);
        userRepository.save(user);
    }

    public String getAccountInformation(String token) throws JsonProcessingException {
        var user  = userRepository.findByEmail(jwtService.getEmailFromToken(token)).orElse(null);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        var userId = user.getId();
        String jedisRequest = jedis.get("user-id-" + userId);
        if (jedisRequest == null) {
            var accountInfoRequest = AccountInformationRequestDto
                    .builder()
                    .accessToken(user.getAccessToken())
                    .clientId(plaidCredentials.getClientId())
                    .secret(plaidCredentials.getSecretKey())
                    .build();
            var response = sendPostRequest(objectMapper.writeValueAsString(accountInfoRequest),"auth/get");
            if(response == null){
                throw new FailedPlaidRequest("Could not get account information");
            }
            System.out.println(response.body());
            jedis.set("user-id" + userId, response.body());
            jedis.expire("user-id-" + userId, expireTime);
            return response.body();
        }
        return jedisRequest;
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

    public PlaidUserInfoDto createPlaidUser(String userEmail){

        PlaidUserInfoDto userInfo = new PlaidUserInfoDto();

        var user = userRepository.findByEmail(userEmail).orElse(null);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        userInfo.setClientUserId(String.valueOf(user.getId()));
        userInfo.setPhoneNumber(user.getPhoneNumber());
        return userInfo;
    }

    public CreateLinkTokenRequestDto createLinkTokenRequest(PlaidUserInfoDto user){
        return CreateLinkTokenRequestDto
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

    public ExchangePublicTokenDto createExchangePublicToken(String publicToken){
        return ExchangePublicTokenDto
                .builder()
                .secret(plaidCredentials.getSecretKey())
                .clientId(plaidCredentials.getClientId())
                .publicToken(publicToken)
                .build();
    }






}
