package com.taskmanager.model;

import com.taskmanager.enumerate.RoleType;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "app_role")
public class Role extends AbstractEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private User user;

    @Column(name="role_type")
    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleType roleType = RoleType.USER;

    public Role() {
    }

    public Role(@NotNull final User user, @NotNull final RoleType roleType) {
        this.user = user;
        this.roleType = roleType;
    }

    @NotNull
    public User getUser() {
        return user;
    }

    public void setUser(@NotNull final User user) {
        this.user = user;
    }

    @NotNull
    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(@NotNull final RoleType roleType) {
        this.roleType = roleType;
    }

    @Override
    public String toString() {
        return roleType.name();
    }
}
