package colepp.app.wealthwisebackend.finance.controllers;


import colepp.app.wealthwisebackend.common.dtos.ErrorDto;
import colepp.app.wealthwisebackend.finance.dtos.AccountInformationResponse;
import colepp.app.wealthwisebackend.finance.dtos.AccountTransactionInformationResponse;
import colepp.app.wealthwisebackend.finance.dtos.ExchangePublicTokenRequest;
import colepp.app.wealthwisebackend.finance.exceptions.FailedPlaidRequest;
import colepp.app.wealthwisebackend.finance.exceptions.PlaidCreateLinkTokenException;
import colepp.app.wealthwisebackend.finance.services.PlaidFinanceService;
import colepp.app.wealthwisebackend.users.excpetions.UserNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@RestController
@RequestMapping("/plaid")
public class PlaidController {
    private final PlaidFinanceService plaidFinanceService;

    @PostMapping("/create_link_token")
    public ResponseEntity<String> createLinkToken(@RequestHeader(name = "Authorization") String token){
        try {
            var linkToken = plaidFinanceService.createLinkToken(token);
            return ResponseEntity.ok().body(linkToken);
        } catch (Exception e) {
            throw new PlaidCreateLinkTokenException(e.getMessage());
        }

    }

    @PostMapping("/exchange_public_token")
    public ResponseEntity<String> exchangePublicToken(@RequestBody ExchangePublicTokenRequest request, @RequestHeader("Authorization") String token) throws Exception {
        System.out.println(request.getPublicToken());
        plaidFinanceService.exchangePublicToken(request.getPublicToken(),token);
        return null;
    }

    @GetMapping("/account_info")
    public ResponseEntity<AccountInformationResponse> getAccountInformation(@RequestHeader("Authorization") String token) throws JsonProcessingException {
        AccountInformationResponse accountInformation = plaidFinanceService.getAccountInformation(token);
        return ResponseEntity.ok().body(accountInformation);
    }

    @GetMapping("/transaction_info")
    public ResponseEntity<AccountTransactionInformationResponse> getTransactionInformation(@RequestHeader("Authorization") String token) throws JsonProcessingException {
        AccountTransactionInformationResponse transactionInformation  = plaidFinanceService.getMonthlyTransactionalInformation(token);
        return ResponseEntity.ok().body(transactionInformation);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("User Not Found", LocalDateTime.now()));

    }

    @ExceptionHandler(FailedPlaidRequest.class)
    public ResponseEntity<ErrorDto> handleFailedPlaidRequest() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("Failed PlaidRequest", LocalDateTime.now()));
    }

}
