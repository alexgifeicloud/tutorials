package com.baeldung.hexagonal.application.service;

import com.baeldung.hexagonal.application.domain.Account;
import com.baeldung.hexagonal.ports.LoadAccountPort;
import com.baeldung.hexagonal.ports.UpdateAccountStatePort;
import com.baeldung.hexagonal.ports.commands.SendMoneyCommand;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.math.BigInteger.valueOf;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class SendMoneyServiceTest {

    private static final UUID SOURCE_ACCOUNT_ID = UUID.randomUUID();
    private static final UUID DESTINATION_ACCOUNT_ID = UUID.randomUUID();

    private final LoadAccountPort loadAccountPort = Mockito.mock(LoadAccountPort.class);
    private final UpdateAccountStatePort updateAccountStatePort = Mockito.mock(UpdateAccountStatePort.class);
    private final SendMoneyService sendMoneyService = new SendMoneyService(loadAccountPort,
            updateAccountStatePort);

    @Test
    void transactionSucceeds() {
        Account sourceAccount = givenAccount(SOURCE_ACCOUNT_ID);
        Account destinationAccount = givenAccount(DESTINATION_ACCOUNT_ID);

        givenWithdrawalWillSucceed(sourceAccount);
        givenDepositWillSucceed(destinationAccount);

        SendMoneyCommand sendMoneyCommand = SendMoneyCommand.of(SOURCE_ACCOUNT_ID, DESTINATION_ACCOUNT_ID, valueOf(500L));
        sendMoneyService.sendMoney(sendMoneyCommand);

        thenAccountsHaveBeenUpdated();
    }

    private Account givenAccount(UUID accountId) {
        Account account = Mockito.mock(Account.class);
        given(account.id()).willReturn(accountId);
        given(loadAccountPort.loadAccount(eq(account.id()), any(LocalDateTime.class)))
                .willReturn(account);
        return account;
    }

    private void givenWithdrawalWillSucceed(Account account) {
        given(account.withdraw(any(BigInteger.class), any(UUID.class))).willReturn(true);
    }

    private void givenDepositWillSucceed(Account account) {
        given(account.deposit(any(BigInteger.class), any(UUID.class))).willReturn(true);
    }

    private void thenAccountsHaveBeenUpdated() {
        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        then(updateAccountStatePort).should(times(2))
                .updateTransactions(accountCaptor.capture());
        List<UUID> updatedAccounts = accountCaptor.getAllValues()
                .stream()
                .map(Account::id)
                .collect(toList());
        assertThat(updatedAccounts).isEqualTo(Lists.newArrayList(SOURCE_ACCOUNT_ID, DESTINATION_ACCOUNT_ID));
    }
}
