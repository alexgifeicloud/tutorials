package com.baeldung.hexagonal.application.service;

import com.baeldung.hexagonal.application.domain.Account;
import com.baeldung.hexagonal.ports.UpdateAccountStatePort;
import com.baeldung.hexagonal.ports.commands.SendMoneyCommand;
import com.baeldung.hexagonal.ports.SendMoneyPort;
import com.baeldung.hexagonal.ports.LoadAccountPort;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class SendMoneyService implements SendMoneyPort {

    private final LoadAccountPort loadAccountPort;
    private final UpdateAccountStatePort updateAccountStatePort;

    public SendMoneyService(LoadAccountPort loadAccountPort, UpdateAccountStatePort updateAccountStatePort) {
        this.loadAccountPort = loadAccountPort;
        this.updateAccountStatePort = updateAccountStatePort;
    }

    @Override
    public boolean sendMoney(SendMoneyCommand sendMoneyCommand) {
        LocalDateTime startingDate = LocalDateTime.now().minusDays(10);

        Account sourceAccount = loadAccountPort.loadAccount(sendMoneyCommand.sourceAccountId(), startingDate);
        Account destinationAccount = loadAccountPort.loadAccount(sendMoneyCommand.destinationAccountId(), startingDate);

        BigInteger amount = sendMoneyCommand.amount();
        sourceAccount.withdraw(amount, destinationAccount.id());
        destinationAccount.deposit(amount, sourceAccount.id());

        updateAccountStatePort.updateTransactions(sourceAccount);
        updateAccountStatePort.updateTransactions(destinationAccount);
        return false;
    }
}
