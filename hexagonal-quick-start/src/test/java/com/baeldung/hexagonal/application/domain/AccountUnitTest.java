package com.baeldung.hexagonal.application.domain;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

import static java.math.BigInteger.valueOf;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class AccountTest {

    private static final UUID OWNER_ACCOUNT_ID = UUID.randomUUID();
    private static final UUID SOURCE_ACCOUNT_ID = OWNER_ACCOUNT_ID;

    @Test
    void calculateBalance() {
        UUID accountId = UUID.randomUUID();
        Transaction firstTransaction = Transaction.from(OWNER_ACCOUNT_ID, SOURCE_ACCOUNT_ID,
                accountId, LocalDateTime.now(), valueOf(999L));
        Transaction secondTransaction = Transaction.from(OWNER_ACCOUNT_ID, SOURCE_ACCOUNT_ID,
                accountId, LocalDateTime.now(), valueOf(1L));
        Account account = Account.of(accountId, valueOf(555L), TransactionLog.from(Lists.newArrayList(
                firstTransaction, secondTransaction
        )));
        assertThat(account.currentBalance()).isEqualTo(BigInteger.valueOf(1555L));
    }

    @Test
    void withdrawalSucceeds() {
        UUID accountId = UUID.randomUUID();
        Transaction firstTransaction = Transaction.from(OWNER_ACCOUNT_ID, SOURCE_ACCOUNT_ID,
                accountId, LocalDateTime.now(), valueOf(999L));
        Transaction secondTransaction = Transaction.from(OWNER_ACCOUNT_ID, SOURCE_ACCOUNT_ID,
                accountId, LocalDateTime.now(), valueOf(1L));
        Account account = Account.of(accountId, valueOf(555L), TransactionLog.from(Lists.newArrayList(
                firstTransaction, secondTransaction
        )));

        account.withdraw(valueOf(555L), UUID.randomUUID());
        assertThat(account.currentBalance()).isEqualTo(valueOf(1000L));
        assertThat(account.getTransactionLog().getTransactions()).hasSize(3);
    }

    @Test
    void depositSuccess() {
        UUID accountId = UUID.randomUUID();
        Transaction firstTransaction = Transaction.from(OWNER_ACCOUNT_ID, SOURCE_ACCOUNT_ID,
                accountId, LocalDateTime.now(), valueOf(999L));
        Transaction secondTransaction = Transaction.from(OWNER_ACCOUNT_ID, SOURCE_ACCOUNT_ID,
                accountId, LocalDateTime.now(), valueOf(1L));
        Account account = Account.of(accountId, valueOf(555L), TransactionLog.from(Lists.newArrayList(
                firstTransaction, secondTransaction
        )));

        account.deposit(valueOf(445L), UUID.randomUUID());
        assertThat(account.currentBalance()).isEqualTo(valueOf(2000L));
        assertThat(account.getTransactionLog().getTransactions()).hasSize(3);
    }
}
