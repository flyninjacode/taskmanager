package com.taskmanager.enumerate;

import org.jetbrains.annotations.NotNull;

public enum RoleType {
    ADMIN("ADMIN"),
    USER("USER");

    @NotNull private final String role;

    RoleType(@NotNull final String role) {
        this.role = role;
    }

    public String displayName(){
        return role;
    }
}
