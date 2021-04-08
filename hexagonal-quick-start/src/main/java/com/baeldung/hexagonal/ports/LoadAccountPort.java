package com.baeldung.hexagonal.ports;

import com.baeldung.hexagonal.domain.Account;

import java.time.LocalDateTime;
import java.util.UUID;

public interface LoadAccountPort {

    Account loadAccount(UUID accountId, LocalDateTime startingDate);
}
