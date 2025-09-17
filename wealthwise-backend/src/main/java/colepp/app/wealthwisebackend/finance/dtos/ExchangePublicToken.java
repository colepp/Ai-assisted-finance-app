package colepp.app.wealthwisebackend.finance.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExchangePublicToken {

    @JsonProperty("client_id")
    private String clientId;

    private String secret;

    @JsonProperty("public_token")
    private String publicToken;
}
