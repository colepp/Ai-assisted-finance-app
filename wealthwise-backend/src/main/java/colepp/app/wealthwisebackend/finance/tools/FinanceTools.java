package colepp.app.wealthwisebackend.finance.tools;

import colepp.app.wealthwisebackend.finance.dtos.AccountDetails;
import colepp.app.wealthwisebackend.finance.dtos.PersonalFinanceCategory;
import colepp.app.wealthwisebackend.finance.dtos.Transaction;

import java.time.LocalDate;
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

    public static double totalAccountsBalance(List<AccountDetails> accounts) {
        return accounts.stream()
                .mapToDouble(account -> account.getBalances().getAvailable())
                .sum();
    }

    public static double totalMonthlyIncome(List<Transaction> transactions) {
        return transactions.stream()
                .filter(transaction -> transaction.getAmount() > 0 && (
                        LocalDate.now().getMonth() == transaction.getAuthorizedDate().getMonth() &&
                                LocalDate.now().getYear() == transaction.getAuthorizedDate().getYear()))
                .mapToDouble(Transaction::getAmount)
                .sum();

    }

    public static double totalMonthlyExpenses(List<Transaction> transactions) {
        return transactions.stream()
            .filter(transaction -> transaction.getAmount() < 0 && (
                LocalDate.now().getMonth().equals(transaction.getAuthorizedDate().getMonth()) && LocalDate.now().getYear() == transaction.getAuthorizedDate().getYear()))
            .mapToDouble(Transaction::getAmount)
            .sum();
    }

    public static Map<String,List<Transaction>> categorizeTransactions(List<Transaction> transactions) {
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

    public static Map<String,List<Transaction>> categorizeTransactions(List<Transaction> transactions,String targetCategory) {
        Map<String,List<Transaction>> categorizedTransactions = new HashMap<>();
        transactions.forEach(transaction -> {
            PersonalFinanceCategory category = transaction.getPersonalFinanceCategory();
            if(category.getPrimary().equals(targetCategory)){
                if(!categorizedTransactions.containsKey(category.getPrimary())){
                    List<Transaction> transactionList = new ArrayList<>();
                    transactionList.add(transaction);
                    categorizedTransactions.put(category.getPrimary(), transactionList);
                }else{
                    categorizedTransactions.get(category.getPrimary()).add(transaction);
                }
            }
        });
        return categorizedTransactions;
    }

    public static Map<Integer,List<Transaction>> categorizeTransactionsByYear(List<Transaction> accounts) {
        Map<Integer,List<Transaction>> categorizedTransactions = new HashMap<>();
        accounts.forEach(transaction -> {
            int year = transaction.getAuthorizedDate().getYear();
            if(categorizedTransactions.containsKey(year)){
                categorizedTransactions.get(year).add(transaction);
            }
            else{
                List<Transaction> transactionList = new ArrayList<>();
                transactionList.add(transaction);
                categorizedTransactions.put(year, transactionList);
            }
        });
        return categorizedTransactions;
    }

    public static Map<Integer,List<Transaction>> categorizeTransactionsByYear(List<Transaction> accounts, int targetYear) {
        Map<Integer,List<Transaction>> categorizedTransactions = new HashMap<>();
        categorizedTransactions.put(targetYear,new ArrayList<>());
        accounts.forEach(transaction -> {
            int year = transaction.getAuthorizedDate().getYear();
            if(year == targetYear){
                categorizedTransactions.get(year).add(transaction);
            }
        });
        return categorizedTransactions;
    }
}
