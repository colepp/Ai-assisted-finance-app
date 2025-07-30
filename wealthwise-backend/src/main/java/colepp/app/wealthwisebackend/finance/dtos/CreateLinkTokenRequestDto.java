package colepp.app.wealthwisebackend.finance.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateLinkTokenRequestDto {

    @NotBlank(message = "client key required")
    @JsonProperty("client_id")
    private String clientId;

    @NotBlank(message = "secret key required")
    private String secret;

    @NotBlank(message = "user required")
    private PlaidUserInfoDto user;

    @NotBlank(message = "Client name required.")
    @JsonProperty("client_name")
    private String clientName;


    private List<String> products;

    @NotBlank(message = "Language required")
    private String language;


    @NotBlank(message = "Country Codes required")
    @JsonProperty("country_codes")
    private List<String> countryCodes;

}
