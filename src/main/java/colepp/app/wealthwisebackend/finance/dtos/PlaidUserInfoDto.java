package colepp.app.wealthwisebackend.finance.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PlaidUserInfoDto {

    @JsonProperty("client_user_id")
    private String clientUserId;

    @JsonProperty("phone_number")
    private String phoneNumber;
}
