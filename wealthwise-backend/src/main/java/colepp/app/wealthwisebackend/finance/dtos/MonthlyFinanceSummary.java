package colepp.app.wealthwisebackend.finance.dtos;

import colepp.app.wealthwisebackend.finance.tools.FinanceTools;
import lombok.Data;

@Data
public class MonthlyFinanceSummary {

    private AccountTransactionInformationResponse transactionHistory;
    private AccountInformationResponse accountInformation;
    private double income;
    private double expense;
    private double accountsBalance;


    public MonthlyFinanceSummary(AccountTransactionInformationResponse transactionHistory) {
        this.transactionHistory = transactionHistory;
        var transactions = transactionHistory.getTransactions();
        if(transactions.isEmpty()){
            this.income = 0;
            this.expense = 0;
        }else{
            this.income = FinanceTools.TotalIncome(transactions);
            this.expense = FinanceTools.monthlyExpenses(transactions);
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
            this.expense = FinanceTools.monthlyExpenses(transactionHistory.getTransactions());
        }
    }

}
