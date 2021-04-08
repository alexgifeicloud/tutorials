package com.baeldung.hexagonal.ports.commands;

import java.math.BigInteger;
import java.util.UUID;

public class SendMoneyCommand {

    private final UUID sourceAccountId;
    private final UUID destinationAccountId;
    private final BigInteger amount;

    public static SendMoneyCommand of(UUID sourceAccountId, UUID destinationAccountId, BigInteger amount) {
        return new SendMoneyCommand(sourceAccountId, destinationAccountId, amount);
    }

    private SendMoneyCommand(UUID sourceAccountId, UUID destinationAccountId, BigInteger amount) {
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.amount = amount;
    }

    public UUID sourceAccountId() {
        return sourceAccountId;
    }

    public UUID destinationAccountId() {
        return destinationAccountId;
    }

    public BigInteger amount() {
        return amount;
    }
}
