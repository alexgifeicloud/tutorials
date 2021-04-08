package com.baeldung.hexagonal.adapters.persistence.model;

import java.util.UUID;

public class AccountEntity {

    private UUID id;

    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
