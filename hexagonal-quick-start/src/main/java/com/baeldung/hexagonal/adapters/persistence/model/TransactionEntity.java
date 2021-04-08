package com.baeldung.hexagonal.adapters.persistence.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionEntity {

    private final Long id;
    private final UUID ownerId;
    private final UUID sourceAccountId;
    private final UUID destinationAccountId;
    private final LocalDateTime timestamp;
    private final BigInteger amount;

    public TransactionEntity(Long id, UUID ownerId, UUID sourceAccountId, UUID destinationAccountId,
                             LocalDateTime timestamp, BigInteger amount) {
        this.id = id;
        this.ownerId = ownerId;
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.timestamp = timestamp;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public UUID getSourceAccountId() {
        return sourceAccountId;
    }

    public UUID getDestinationAccountId() {
        return destinationAccountId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public BigInteger getAmount() {
        return amount;
    }
}
