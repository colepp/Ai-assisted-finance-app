package colepp.app.wealthwisebackend.finance.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDetails {

    @JsonProperty("account_id")
    private String accountId;

    private Balances balances;

    private String mask;

    @JsonProperty("official_name")
    private String officialName;

    private String name;

    @JsonProperty("holder_category")
    private String holderCategory;

}
