package com.taskmanager.dto;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.UUID;

public abstract class AbstractEntityDTO implements Serializable {

    @NotNull
    protected String id = UUID.randomUUID().toString();

    public AbstractEntityDTO() {
    }

    @NotNull
    public String getId() {
        return this.id;
    }

    public void setId(@NotNull final String id) {
        this.id = id;
    }
}
