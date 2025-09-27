package colepp.app.wealthwisebackend.finance.dtos;

import colepp.app.wealthwisebackend.finance.tools.FinanceTools;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MonthlyFinanceSummary {

    private AccountTransactionInformationResponse transactionHistory;
    private AccountInformationResponse accountInformation;
    private double income;
    private double expense;
    private double accountsBalance;
    private String aiEvaluation;


    public MonthlyFinanceSummary(AccountTransactionInformationResponse transactionHistory) {
        this.transactionHistory = transactionHistory;
        var transactions = transactionHistory.getTransactions();
        if(transactions.isEmpty()){
            this.income = 0;
            this.expense = 0;
        }else{
            this.income = FinanceTools.TotalIncome(transactions);
            this.expense = FinanceTools.totalMonthlyExpenses(transactions);
        }
    }

    public MonthlyFinanceSummary(AccountInformationResponse accountInformation, AccountTransactionInformationResponse transactionHistory) {
        this.accountInformation = accountInformation;
        this.transactionHistory = transactionHistory;
        if (transactionHistory.getTransactions().isEmpty()) {
            this.income = 0;
            this.expense = 0;
        }
        else {
            this.income = FinanceTools.TotalIncome(transactionHistory.getTransactions());
            this.expense = FinanceTools.totalMonthlyExpenses(transactionHistory.getTransactions());
        }
    }

    public MonthlyFinanceSummary(AccountInformationResponse accountInformation, AccountTransactionInformationResponse transactionHistory,String aiEvaluation) {
        this.accountInformation = accountInformation;
        this.transactionHistory = transactionHistory;
        this.aiEvaluation = aiEvaluation;
        if (transactionHistory.getTransactions().isEmpty()) {
            this.income = 0;
            this.expense = 0;
        }
        else {
            this.income = FinanceTools.TotalIncome(transactionHistory.getTransactions());
        }
    }

}
