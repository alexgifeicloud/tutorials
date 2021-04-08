package com.baeldung.hexagonal.adapters.web;

import com.baeldung.hexagonal.ports.SendMoneyPort;
import com.baeldung.hexagonal.ports.commands.SendMoneyCommand;

import java.math.BigInteger;
import java.util.UUID;

public class SendMoneyController {

    private final SendMoneyPort sendMoneyPort;

    public SendMoneyController(SendMoneyPort sendMoneyPort) {
        this.sendMoneyPort = sendMoneyPort;
    }

    public void sendMoney(UUID sourceAccountId, UUID destinationAccountId, BigInteger amount) {
        SendMoneyCommand sendMoneyCommand = SendMoneyCommand.of(sourceAccountId, destinationAccountId, amount);
        sendMoneyPort.sendMoney(sendMoneyCommand);
    }
}
