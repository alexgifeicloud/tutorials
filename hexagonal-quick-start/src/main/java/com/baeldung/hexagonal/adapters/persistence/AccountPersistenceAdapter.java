package com.baeldung.hexagonal.adapters.persistence;

import com.baeldung.hexagonal.adapters.persistence.model.AccountEntity;
import com.baeldung.hexagonal.adapters.persistence.model.TransactionEntity;
import com.baeldung.hexagonal.adapters.persistence.repository.AccountRepository;
import com.baeldung.hexagonal.adapters.persistence.repository.TransactionRepository;
import com.baeldung.hexagonal.domain.Account;
import com.baeldung.hexagonal.domain.Transaction;
import com.baeldung.hexagonal.domain.TransactionLog;
import com.baeldung.hexagonal.ports.LoadAccountPort;
import com.baeldung.hexagonal.ports.UpdateAccountStatePort;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountPersistenceAdapter implements LoadAccountPort, UpdateAccountStatePort {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountPersistenceAdapter(AccountRepository accountRepository,
                                     TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Account loadAccount(UUID accountId, LocalDateTime startingDate) {
        AccountEntity account = accountRepository.findById(accountId);
        if (account == null) {
            throw new RuntimeException("Account does not exist.");
        }
        List<TransactionEntity> transactions = transactionRepository.findByOwnerSince(accountId, startingDate);
        BigInteger withdrawalBalance = transactionRepository.getWithdrawalBalanceUntil(accountId, startingDate);
        BigInteger depositBalance = transactionRepository.getDepositBalanceUntil(accountId, startingDate);
        TransactionLog transactionLog = mapToTransactionLog(transactions);
        return Account.of(accountId, depositBalance.subtract(withdrawalBalance), transactionLog);
    }

    @Override
    public void updateTransactions(Account account) {
        List<Transaction> transactions = account.getTransactionLog().getTransactions();
        for (Transaction t : transactions) {
            transactionRepository.save(new TransactionEntity(t.id(), t.ownerId(), t.sourceAccountId(),
                    t.destinationAccountId(), t.timestamp(), t.amount()));
        }
    }

    TransactionLog mapToTransactionLog(List<TransactionEntity> transactions) {
        List<Transaction> mappedTransactions = new ArrayList<>();
        for (TransactionEntity transactionEntity : transactions) {
            mappedTransactions.add(Transaction.from(transactionEntity.getOwnerId(),
                    transactionEntity.getSourceAccountId(), transactionEntity.getDestinationAccountId(),
                    transactionEntity.getTimestamp(), transactionEntity.getAmount()));
        }
        return TransactionLog.from(mappedTransactions);
    }
}
