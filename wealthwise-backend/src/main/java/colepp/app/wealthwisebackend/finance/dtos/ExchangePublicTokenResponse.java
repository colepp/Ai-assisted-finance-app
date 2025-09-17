package colepp.app.wealthwisebackend.finance.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExchangePublicTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("item_id")
    private String itemID;

    @JsonProperty("request_id")
    private String requestID;
}
