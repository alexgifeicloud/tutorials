package com.baeldung.hexagonal.domain;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {

    private final Long id;
    private final UUID ownerId;
    private final UUID sourceAccountId;
    private final UUID destinationAccountId;
    private final LocalDateTime timestamp;
    private final BigInteger amount;

    public static Transaction from(UUID ownerId, UUID sourceAccountId, UUID destinationAccountId,
                                   LocalDateTime timestamp, BigInteger amount) {
        return new Transaction(System.nanoTime(),
                ownerId, sourceAccountId, destinationAccountId, timestamp, amount);
    }

    private Transaction(Long id, UUID ownerId, UUID sourceAccountId, UUID destinationAccountId,
                        LocalDateTime timestamp, BigInteger amount) {
        this.id = id;
        this.ownerId = ownerId;
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.timestamp = timestamp;
        this.amount = amount;
    }

    public Long id() {
        return id;
    }

    public UUID ownerId() {
        return ownerId;
    }

    public UUID sourceAccountId() {
        return sourceAccountId;
    }

    public UUID destinationAccountId() {
        return destinationAccountId;
    }

    public LocalDateTime timestamp() {
        return timestamp;
    }

    public BigInteger amount() {
        return amount;
    }
}
