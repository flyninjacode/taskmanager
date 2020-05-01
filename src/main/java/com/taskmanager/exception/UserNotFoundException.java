package com.taskmanager.exception;

import org.jetbrains.annotations.NotNull;

public class UserNotFoundException extends Exception {

    @NotNull
    private static final String ERROR_MESSAGE = "[The user doesn't exist.]";

    public UserNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
