package colepp.app.wealthwisebackend.finance.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExchangePublicTokenRequest {
    @JsonProperty("public_token")
    private String publicToken;
}
