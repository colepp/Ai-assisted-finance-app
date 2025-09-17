package colepp.app.wealthwisebackend.finance.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {
        @JsonProperty("transaction_id")
        private String transactionId;

        @JsonProperty("authorized_date")
        private LocalDate authorizedDate;

        private boolean pending;

        @JsonProperty("payment_channel")
        private String paymentChannel;

        @JsonProperty("counterparties")
        private List<Counterparty> counterparties;

        @JsonProperty("personal_finance_category")
        private PersonalFinanceCategory personalFinanceCategory;

        private double amount;

        @JsonProperty("account_owner")
        private String accountOwner;

        @JsonProperty("iso_currency_code")
        private String isoCurrencyCode;

        public String getFormattedAuthorizedDate() {
            return authorizedDate != null ? authorizedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
        }
}
