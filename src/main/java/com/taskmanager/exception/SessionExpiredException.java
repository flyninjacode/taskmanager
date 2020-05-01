package com.taskmanager.exception;

import org.jetbrains.annotations.NotNull;

public class SessionExpiredException extends Exception {

    @NotNull
    private static final String ERROR_MESSAGE = "[The session is expired.]";

    public SessionExpiredException() {
        super(ERROR_MESSAGE);
    }
}
