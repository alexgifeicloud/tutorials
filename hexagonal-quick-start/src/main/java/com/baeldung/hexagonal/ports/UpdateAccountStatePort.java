package com.baeldung.hexagonal.ports;

import com.baeldung.hexagonal.application.domain.Account;

public interface UpdateAccountStatePort {

    void updateTransactions(Account account);

}
