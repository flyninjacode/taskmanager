package com.taskmanager.dto;

import com.taskmanager.enumerate.RoleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.PROPERTY)
public final class UserDTO extends AbstractEntityDTO implements Serializable {

    @NotNull
    private String login;

    @NotNull
    private String passwordHash;

    @NotNull
    private RoleType roleType;

    @Nullable
    private String email;

    @Nullable
    private String firstName;

    @Nullable
    private String lastName;

    @Nullable
    private String middleName;

    @Nullable
    private String phone;

    @NotNull
    private Boolean locked = false;

    public UserDTO() {
    }

    public UserDTO(@NotNull final String login,
                   @NotNull final String password,
                   @NotNull final RoleType roleType) {
        super();
        this.login = login;
        this.passwordHash = password;
        this.roleType = roleType;
    }

    @NotNull
    public String getLogin() {
        return this.login;
    }

    @NotNull
    public String getPasswordHash() {
        return this.passwordHash;
    }

    @NotNull
    public RoleType getRoleType() {
        return this.roleType;
    }

    @Nullable
    public String getEmail() {
        return this.email;
    }

    @Nullable
    public String getFirstName() {
        return this.firstName;
    }

    @Nullable
    public String getLastName() {
        return this.lastName;
    }

    @Nullable
    public String getMiddleName() {
        return this.middleName;
    }

    @Nullable
    public String getPhone() {
        return this.phone;
    }

    @NotNull
    public Boolean getLocked() {
        return this.locked;
    }

    public void setLogin(@NotNull final String login) {
        this.login = login;
    }

    public void setPasswordHash(@NotNull final String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setRoleType(@NotNull final RoleType roleType) {
        this.roleType = roleType;
    }

    public void setEmail(@Nullable final String email) {
        this.email = email;
    }

    public void setFirstName(@Nullable final String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(@Nullable final String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(@Nullable final String middleName) {
        this.middleName = middleName;
    }

    public void setPhone(@Nullable final String phone) {
        this.phone = phone;
    }

    public void setLocked(@NotNull final Boolean locked) {
        this.locked = locked;
    }
}
