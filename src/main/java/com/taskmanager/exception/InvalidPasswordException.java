package com.taskmanager.exception;

import org.jetbrains.annotations.NotNull;

public class InvalidPasswordException extends Exception {

    @NotNull
    private static final String ERROR_MESSAGE = "[The password doesn't match.]";

    public InvalidPasswordException() {
        super(ERROR_MESSAGE);
    }
}
