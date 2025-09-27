package colepp.app.wealthwisebackend.finance.services;

import colepp.app.wealthwisebackend.agent.agents.AiAgent;
import colepp.app.wealthwisebackend.common.services.JwtService;
import colepp.app.wealthwisebackend.finance.dtos.MonthlyFinanceSummary;

import colepp.app.wealthwisebackend.users.excpetions.UserNotFoundException;
import colepp.app.wealthwisebackend.users.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WealthwiseFinanceService {

    private final PlaidFinanceService plaidFinanceService;
    private final AiAgent aiAgent;
    private final UserRepository userRepository;
    private final JwtService jwtService;


    public MonthlyFinanceSummary createMonthlyFinanceSummary(String token) throws JsonProcessingException {
        var user = userRepository.findByEmail(jwtService.getEmailFromToken(token)).orElse(null);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        var userId = user.getId();

        var transactions = plaidFinanceService.getMonthlyTransactionalInformation(userId);
        var accounts = plaidFinanceService.getAccountInformation(userId);
//        var test = FinanceTools.createPlottedBalances(transactions.getTransactions(),accounts.getAccounts().get(0));
        var monthlyFinanceSummary = new MonthlyFinanceSummary(accounts,transactions);
//        var aiResponse = aiAgent.createEval("monthly",monthlyFinanceSummary.toString());

        return new MonthlyFinanceSummary(accounts,transactions);
    }
}
