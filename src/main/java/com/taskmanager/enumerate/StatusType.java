package com.taskmanager.enumerate;

import org.jetbrains.annotations.NotNull;

public enum StatusType {
    PLANNED("PLANNED"),
    INPROCESS("INPROCESS"),
    READY("READY");

    @NotNull
    private final String status;

    StatusType(@NotNull final String status) {
        this.status = status;
    }

    public String displayName(){
        return status;
    }
}
