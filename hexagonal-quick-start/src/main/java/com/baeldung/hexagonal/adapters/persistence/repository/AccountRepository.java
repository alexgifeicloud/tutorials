package com.baeldung.hexagonal.adapters.persistence.repository;

import com.baeldung.hexagonal.adapters.persistence.model.AccountEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountRepository {

    private static final List<AccountEntity> DATA = new ArrayList<>();

    public AccountEntity findById(UUID id) {
        return DATA.stream().filter(accountEntity -> accountEntity.id().equals(id)).findFirst().orElse(null);
    }
}
