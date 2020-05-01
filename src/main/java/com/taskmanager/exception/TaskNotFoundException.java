package com.taskmanager.exception;

import org.jetbrains.annotations.NotNull;

public class TaskNotFoundException extends Exception {

    @NotNull
    private static final String ERROR_MESSAGE = "[The task is not found.]";

    public TaskNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
