package com.taskmanager.exception;

import org.jetbrains.annotations.NotNull;

public class CommandAdminOnlyException extends Exception {

    @NotNull
    private static final String ERROR_MESSAGE = "[The access is forbidden. You need admin role for this command.]";

    public CommandAdminOnlyException() {
        super(ERROR_MESSAGE);
    }
}
