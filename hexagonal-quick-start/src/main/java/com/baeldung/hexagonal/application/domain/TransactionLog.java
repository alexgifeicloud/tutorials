package com.baeldung.hexagonal.application.domain;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransactionLog {

    private List<Transaction> transactions;

    public static TransactionLog from(List<Transaction> transactions) {
        return new TransactionLog(transactions);
    }

    private TransactionLog(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public BigInteger calculateBalance(UUID accountId) {
        BigInteger depositBalance = depositBalance(accountId);
        BigInteger withdrawalBalance = withdrawalBalance(accountId);
        return depositBalance.add(withdrawalBalance.negate());
    }

    private BigInteger depositBalance(UUID accountId) {
        return transactions.stream()
                .filter(t -> t.destinationAccountId().equals(accountId))
                .map(Transaction::amount)
                .reduce(BigInteger.ZERO, BigInteger::add);
    }

    private BigInteger withdrawalBalance(UUID accountId) {
        return transactions.stream()
                .filter(t -> t.sourceAccountId().equals(accountId))
                .map(Transaction::amount)
                .reduce(BigInteger.ZERO, BigInteger::add);
    }

    public TransactionLog addTransaction(Transaction transaction) {
        transactions = Stream.concat(Stream.of(transaction),
                transactions.stream()).collect(Collectors.toList());
        return this;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
