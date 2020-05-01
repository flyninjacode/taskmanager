package com.taskmanager.exception;

import org.jetbrains.annotations.NotNull;

public class ProjectNotFoundException extends Exception {

    @NotNull
    private static final String ERROR_MESSAGE = "[The project is not found.]";

    public ProjectNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
