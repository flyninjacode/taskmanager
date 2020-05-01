package com.taskmanager.exception;

import org.jetbrains.annotations.NotNull;

public class SignatureCorruptException extends Exception {

    @NotNull
    private static final String ERROR_MESSAGE = ": The signature is invalid.]";

    public SignatureCorruptException(@NotNull final String object) {
        super("[" + object + ERROR_MESSAGE);
    }
}
