package colepp.app.wealthwisebackend.finance.config;

import lombok.Getter;

import java.util.List;

@Getter
public class PlaidCredentials {
    private final String clientId;
    private final String secretKey;
    private final List<String> products;
    private final List<String> countryCodes;
    private final String plaidUrl;

    public PlaidCredentials(String clientId, String secretKey, String products, String countryCodes,String plaidUrl) {
        this.clientId = clientId;
        this.secretKey = secretKey;
        this.products = List.of(products.split(","));
        this.countryCodes = List.of(countryCodes.split(","));
        this.plaidUrl = plaidUrl;
    }


}
