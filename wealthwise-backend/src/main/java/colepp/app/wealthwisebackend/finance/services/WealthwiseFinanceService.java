package colepp.app.wealthwisebackend.finance.services;

import colepp.app.wealthwisebackend.finance.dtos.MonthlyFinanceSummary;
import colepp.app.wealthwisebackend.finance.tools.FinanceTools;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WealthwiseFinanceService {

    private final PlaidFinanceService plaidFinanceService;


    public MonthlyFinanceSummary createMonthlyFinanceSummary(String token) throws JsonProcessingException {
        var transactions = plaidFinanceService.getMonthlyTransactionalInformation(token);
        var accounts = plaidFinanceService.getAccountInformation(token);
        var test = FinanceTools.createPlottedBalances(transactions.getTransactions(),accounts.getAccounts().get(0));
        var sortedCategories = FinanceTools.categorizeTransactions(transactions.getTransactions());
        System.out.println(sortedCategories);

        return new MonthlyFinanceSummary(accounts,transactions);
    }
}
