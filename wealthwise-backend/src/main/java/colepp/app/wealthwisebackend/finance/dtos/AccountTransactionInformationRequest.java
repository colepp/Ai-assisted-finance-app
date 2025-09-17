package colepp.app.wealthwisebackend.finance.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AccountTransactionInformationRequest {

    @JsonProperty("client_id")
    public String clientId;

    public String secret;

    @JsonProperty("access_token")
    public String accessToken;

    @JsonProperty("start_date")
    public String startDate;

    @JsonProperty("end_date")
    public String endDate;

    public TransactionOptions options;




}

