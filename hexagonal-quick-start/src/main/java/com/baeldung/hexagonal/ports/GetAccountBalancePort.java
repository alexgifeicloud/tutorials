package com.baeldung.hexagonal.ports;

import java.math.BigInteger;
import java.util.UUID;

public interface GetAccountBalancePort {

    BigInteger getAccountBalance(UUID accountId);

}
