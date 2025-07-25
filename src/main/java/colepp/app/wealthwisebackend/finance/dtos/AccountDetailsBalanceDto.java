package colepp.app.wealthwisebackend.finance.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDetailsBalanceDto {
    @JsonProperty("available")
    private BigDecimal availableBalance;

    @JsonProperty("current")
    private BigDecimal currentBalance;

    @JsonProperty("iso_currency_code")
    private String isoCurrencyCode;

    // find out what limit is
    private Object limit;

    @JsonProperty("unofficialCurrencyCode")
    private String unofficialCurrencyCode;
}
