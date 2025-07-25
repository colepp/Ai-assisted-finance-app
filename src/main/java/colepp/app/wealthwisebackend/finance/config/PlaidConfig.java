package colepp.app.wealthwisebackend.finance.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;


@Configuration
public class PlaidConfig {
    @Value("${plaid.client-id}")
    private String clientId;

    @Value("${plaid.secret}")
    private String secretKey;

    @Value("${plaid.env}")
    private String env;

    @Value("${plaid.products}")
    private String products;

    @Value("${plaid.country-codes}")
    private String countryCodes;


    @Bean
    public PlaidCredentials plaidCredentials() {
        return new PlaidCredentials(clientId,secretKey,products,countryCodes,getPlaidUrl());
    }

    @Bean
    public HttpClient plaidClient() {
        return HttpClient.newHttpClient();
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }


    public String getPlaidUrl(){
        switch (env){
            case "sandbox" -> {return "https://sandbox.plaid.com/";}
            case "production" -> {return "https://production.plaid.com/";}
            default -> {return "https://sandbox.plaid.com/";}
        }
    }

}
