package colepp.app.wealthwisebackend.finance.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDetailsDto {

    @JsonProperty("account_id")
    private String accountId;

    private AccountDetailsDto balances;

    private String holderCategory;

    private String mask;

    private String officialName;

}
