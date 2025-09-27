package colepp.app.wealthwisebackend.finance.controllers;

import colepp.app.wealthwisebackend.common.dtos.ErrorDto;
import colepp.app.wealthwisebackend.finance.dtos.MonthlyFinanceSummary;
import colepp.app.wealthwisebackend.finance.services.WealthwiseFinanceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@RestController
@RequestMapping("/wealthwise")
public class WealthWiseFinanceController {

    private final WealthwiseFinanceService wealthwiseFinanceService;

    @GetMapping("/monthly_summary")
    public ResponseEntity<MonthlyFinanceSummary> getSummary(@RequestHeader("Authorization") String token) throws JsonProcessingException {
        var monthlySummary = wealthwiseFinanceService.createMonthlyFinanceSummary(token);
        return ResponseEntity.ok().body(monthlySummary);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorDto> handleJwtError(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDto("Expired JWT", LocalDateTime.now()));
    }
}
