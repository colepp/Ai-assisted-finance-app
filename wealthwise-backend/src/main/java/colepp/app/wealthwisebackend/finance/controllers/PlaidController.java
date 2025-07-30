package colepp.app.wealthwisebackend.finance.controllers;


import colepp.app.wealthwisebackend.common.dtos.ErrorDto;
import colepp.app.wealthwisebackend.finance.dtos.ExchangePublicTokenRequestDto;
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
@RequestMapping("/plaid/api")
public class PlaidController {
    private final PlaidFinanceService plaidFinanceService;

    @PostMapping("/create_link_token")
    @CrossOrigin
    public ResponseEntity<String> createLinkToken(){
        try {
            var linkToken = plaidFinanceService.createLinkToken();
            return ResponseEntity.ok().body(linkToken);
        } catch (Exception e) {
            throw new PlaidCreateLinkTokenException(e.getMessage());
        }

    }

    @PostMapping("/exchange_public_token")
    @CrossOrigin
    public ResponseEntity<String> exchangePublicToken(@RequestBody ExchangePublicTokenRequestDto request) throws Exception {
        System.out.println(request.getPublicToken());
        plaidFinanceService.exchangePublicToken(request.getPublicToken());
        return null;
    }

    @PostMapping("/account_info")
    @CrossOrigin
    public ResponseEntity<String> getAccountInformation() throws JsonProcessingException {
        String str = plaidFinanceService.getAccountInformation();
        return ResponseEntity.ok().body(str);
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
