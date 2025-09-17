package colepp.app.wealthwisebackend.finance.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountInformationRequest {

    @JsonProperty("client_id")
    public String clientId;

    public String secret;

    @JsonProperty("access_token")
    public String accessToken;


}
