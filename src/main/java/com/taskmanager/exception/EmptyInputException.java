package com.taskmanager.exception;

import org.jetbrains.annotations.NotNull;

public class EmptyInputException extends Exception {

    @NotNull
    private static final String ERROR_MESSAGE = " can't be empty.]";

    public EmptyInputException(@NotNull final String input) {
        super("[" + input + ERROR_MESSAGE);
    }
}
