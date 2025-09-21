package colepp.app.wealthwisebackend.finance.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountTransactionInformationResponse {

    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("total_transactions")
    private int transactionCount;

    @JsonProperty
    private List<Transaction> transactions;

}
