package com.taskmanager.exception;

import org.jetbrains.annotations.NotNull;

public class DuplicateUserException extends Exception {

    @NotNull
    private static final String ERROR_MESSAGE = "[Such login already exists, please pick another one.]";

    public DuplicateUserException() {
        super(ERROR_MESSAGE);
    }
}
