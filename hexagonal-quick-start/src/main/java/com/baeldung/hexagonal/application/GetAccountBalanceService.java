package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.ports.GetAccountBalancePort;
import com.baeldung.hexagonal.ports.LoadAccountPort;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

public class GetAccountBalanceService implements GetAccountBalancePort {

    private final LoadAccountPort loadAccountPort;

    public GetAccountBalanceService(LoadAccountPort loadAccountPort) {
        this.loadAccountPort = loadAccountPort;
    }

    @Override
    public BigInteger getAccountBalance(UUID accountId) {
        return loadAccountPort.loadAccount(accountId, LocalDateTime.now()).currentBalance();
    }
}
