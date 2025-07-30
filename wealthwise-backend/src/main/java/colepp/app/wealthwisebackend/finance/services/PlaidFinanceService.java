package colepp.app.wealthwisebackend.finance.services;

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


import java.net.URI;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaidFinanceService {

    private final PlaidCredentials plaidCredentials;
    private final UserRepository userRepository;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;


    public String createLinkToken() throws Exception{
        var user = createPlaidUser();
        var linkTokenRequest = createLinkTokenRequest(user);
        var response = sendPostRequest(objectMapper.writeValueAsString(linkTokenRequest),"link/token/create");
        return response.body();
    }

    public ExchangePublicTokenResponseDto exchangePublicToken(String publicToken) throws Exception {

        var exchangePublicTokenRequestDto = createExchangePublicToken(publicToken);

        var user = userRepository.findById(1L).orElse(null);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        var response = sendPostRequest(objectMapper.writeValueAsString(exchangePublicTokenRequestDto), "item/public_token/exchange");
        if (response == null) {
            throw new FailedPlaidRequest("Could not create token");
        }
        ExchangePublicTokenResponseDto responseDto = objectMapper.readValue(response.body(), ExchangePublicTokenResponseDto.class);
        user.setAccessToken(responseDto.getAccessToken());
        user.setAccountLinkStatus(UserLinkStatus.LINK_STATUS_CREATED);
        userRepository.save(user);
        return responseDto;
    }

    public String getAccountInformation() throws JsonProcessingException {
        var user  = userRepository.findById(1L).orElse(null);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
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
        return "";


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

    public PlaidUserInfoDto createPlaidUser(){

        PlaidUserInfoDto userInfo = new PlaidUserInfoDto();

        var user = userRepository.findById(1L).orElse(null);
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
