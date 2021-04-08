package com.baeldung.hexagonal.application.domain;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

import static java.math.BigInteger.valueOf;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class TransactionLogTest {

    @Test
    void calculatesBalance() {
        UUID account1 = UUID.randomUUID();
        UUID account2 = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();

        TransactionLog transactionLog = TransactionLog.from(Lists.newArrayList(
                Transaction.from(ownerId, account1, account2, LocalDateTime.now(), valueOf(999)),
                Transaction.from(ownerId, account1, account2, LocalDateTime.now(), valueOf(1)),
                Transaction.from(ownerId, account2, account1, LocalDateTime.now(), valueOf(500)))
        );

        assertThat(transactionLog.calculateBalance(account1)).isEqualTo(valueOf(-500L));
        assertThat(transactionLog.calculateBalance(account2)).isEqualTo(valueOf(500L));
    }
}
