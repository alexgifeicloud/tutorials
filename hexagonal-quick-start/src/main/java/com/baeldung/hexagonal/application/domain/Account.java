package com.baeldung.hexagonal.application.domain;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

public class Account {

    private final UUID id;
    private final BigInteger startingBalance;
    private final TransactionLog transactionLog;

    private Account(UUID id, BigInteger balance, TransactionLog transactionLog) {
        this.id = id;
        this.startingBalance = balance;
        this.transactionLog = transactionLog;
    }

    public static Account of(UUID accountId, BigInteger startingBalance, TransactionLog transactionLog) {
        return new Account(accountId, startingBalance, transactionLog);
    }

    public boolean withdraw(BigInteger amount, UUID destinationAccountId) {
        LocalDateTime timestamp = LocalDateTime.now();
        Transaction withdrawal = Transaction.from(this.id, this.id, destinationAccountId, timestamp, amount);
        transactionLog.addTransaction(withdrawal);
        return true;
    }

    public boolean deposit(BigInteger amount, UUID sourceAccountId) {
        LocalDateTime timestamp = LocalDateTime.now();
        Transaction deposit = Transaction.from(this.id, sourceAccountId, this.id, timestamp, amount);
        transactionLog.addTransaction(deposit);
        return true;
    }

    public UUID id() {
        return id;
    }

    public BigInteger startingBalance() {
        return startingBalance;
    }

    public BigInteger currentBalance() {
        return this.startingBalance.add(this.transactionLog.calculateBalance(this.id));
    }

    public TransactionLog getTransactionLog() {
        return transactionLog;
    }
}
