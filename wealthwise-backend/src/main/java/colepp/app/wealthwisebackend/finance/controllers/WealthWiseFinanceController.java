package colepp.app.wealthwisebackend.finance.controllers;

import colepp.app.wealthwisebackend.finance.dtos.MonthlyFinanceSummary;
import colepp.app.wealthwisebackend.finance.services.WealthwiseFinanceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
