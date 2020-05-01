package com.taskmanager.exception;

import org.jetbrains.annotations.NotNull;

public class SessionNotFoundException extends Exception {

    @NotNull
    private static final String ERROR_MESSAGE = "[The session does not exist.]";

    public SessionNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
