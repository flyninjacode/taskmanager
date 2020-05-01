package com.taskmanager.exception;

import org.jetbrains.annotations.NotNull;

public class AuthenticationException extends Exception {

    @NotNull
    private static final String ERROR_MESSAGE = "[The access is forbidden.]";

    public AuthenticationException() {
        super(ERROR_MESSAGE);
    }
}
