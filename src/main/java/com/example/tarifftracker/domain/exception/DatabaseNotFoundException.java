package com.example.tarifftracker.domain.exception;

import jakarta.persistence.PersistenceException;

public class DatabaseNotFoundException extends PersistenceException {

    public DatabaseNotFoundException(String message) {
        super(message);
    }

}
