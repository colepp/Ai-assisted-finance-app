package colepp.app.wealthwisebackend.finance.tools;

import colepp.app.wealthwisebackend.finance.dtos.AccountDetails;
import colepp.app.wealthwisebackend.finance.dtos.PersonalFinanceCategory;
import colepp.app.wealthwisebackend.finance.dtos.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FinanceTools {

    public static double TotalIncome(List<Transaction> transactions) {
        return transactions.stream()
                .filter(transaction -> transaction.getAmount() > 0)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public static double TotalExpenses(List<Transaction> transactions) {
        return transactions.stream()
                .filter(transaction -> transaction.getAmount() < 0)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public static double MonthlyIncome(List<Transaction> transactions) {
        return transactions.stream()
                .filter(transaction -> transaction.getAmount() > 0 && (
                        LocalDate.now().getMonth() == transaction.getAuthorizedDate().getMonth() &&
                                LocalDate.now().getYear() == transaction.getAuthorizedDate().getYear()))
                .mapToDouble(Transaction::getAmount)
                .sum();

    }

    public static double monthlyExpenses(List<Transaction> transactions) {
        return transactions.stream()
            .filter(transaction -> transaction.getAmount() < 0 && (
                LocalDate.now().getMonth().equals(transaction.getAuthorizedDate().getMonth()) && LocalDate.now().getYear() == transaction.getAuthorizedDate().getYear()))
            .mapToDouble(Transaction::getAmount)
            .sum();
    }

    public static double monthlyExpenses(List<Transaction> transactions, Integer month, Integer year) {
        return transactions.stream()
            .filter(transaction -> transaction.getAmount() < 0 && (
                month.equals(transaction.getAuthorizedDate().getMonth()) && year.equals(transaction.getAuthorizedDate().getYear()))
            )
            .mapToDouble(Transaction::getAmount)
            .sum();
    }

    public static Map<String,List<Transaction>> categorizeAllTransactions(List<Transaction> transactions) {
        Map<String,List<Transaction>> categorizedTransactions = new HashMap<>();
        for (Transaction transaction : transactions) {
            PersonalFinanceCategory category = transaction.getPersonalFinanceCategory();
            if (categorizedTransactions.containsKey(category.getPrimary())){
                categorizedTransactions.get(category.getPrimary()).add(transaction);
            }else{
                List<Transaction> transactionList = new ArrayList<>();
                transactionList.add(transaction);
                categorizedTransactions.put(category.getPrimary(), transactionList);
            }
        }
        return categorizedTransactions;
    }

    public static Map<String,List<Transaction>> categorizeSpecificTransactions(List<Transaction> transactions,String targetCategory) {
        Map<String,List<Transaction>> categorizedTransactions = new HashMap<>();
        for (Transaction transaction : transactions) {
            PersonalFinanceCategory category = transaction.getPersonalFinanceCategory();
            if(category.getPrimary().equals(targetCategory)){
                if(categorizedTransactions.isEmpty()){
                    categorizedTransactions.put(category.getPrimary(), new ArrayList<>());
                }
                categorizedTransactions.get(category.getPrimary()).add(transaction);
            }
        }
        return categorizedTransactions;

    }

    public static double calculateTotalAccountsBalance(List<AccountDetails> accounts) {
        return accounts.stream()
                .mapToDouble(account -> account.getBalances().getAvailable())
                .sum();
    }
}
