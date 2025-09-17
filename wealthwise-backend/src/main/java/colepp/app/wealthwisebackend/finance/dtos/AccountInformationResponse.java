package colepp.app.wealthwisebackend.finance.dtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

// COME BACK TO THIS!
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountInformationResponse {
    List<AccountDetails> accounts;
}
