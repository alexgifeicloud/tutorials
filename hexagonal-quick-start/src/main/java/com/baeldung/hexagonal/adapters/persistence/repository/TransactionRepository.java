package com.baeldung.hexagonal.adapters.persistence.repository;

import com.baeldung.hexagonal.adapters.persistence.model.TransactionEntity;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class TransactionRepository {

    private static final List<TransactionEntity> DATA = new ArrayList<>();

    public List<TransactionEntity> findByOwnerSince(UUID ownerAccountId, LocalDateTime since) {

        return DATA.stream()
                .sorted(comparing(TransactionEntity::getTimestamp))
                .filter(t -> t.getOwnerId().equals(ownerAccountId))
                .filter(t -> since.isEqual(t.getTimestamp()) || since.isBefore(t.getTimestamp()))
                .collect(toList());
    }

    public BigInteger getDepositBalanceUntil(UUID accountId, LocalDateTime until) {
        return DATA.stream()
                .sorted(comparing(TransactionEntity::getTimestamp))
                .filter(t -> t.getDestinationAccountId().equals(accountId))
                .filter(t -> t.getOwnerId().equals(accountId))
                .filter(t -> t.getTimestamp().isBefore(until))
                .map(TransactionEntity::getAmount)
                .reduce(BigInteger::add).orElse(BigInteger.ZERO);
    }

    public BigInteger getWithdrawalBalanceUntil(UUID accountId, LocalDateTime until) {
        return DATA.stream()
                .sorted(comparing(TransactionEntity::getTimestamp))
                .filter(t -> t.getSourceAccountId().equals(accountId))
                .filter(t -> t.getOwnerId().equals(accountId))
                .filter(t -> t.getTimestamp().isBefore(until))
                .map(TransactionEntity::getAmount)
                .reduce(BigInteger::add).orElse(BigInteger.ZERO);
    }

    public void save(TransactionEntity transactionEntity) {
        DATA.add(transactionEntity);
    }
}
