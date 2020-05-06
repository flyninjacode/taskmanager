package com.taskmanager.model;

import com.taskmanager.enumerate.RoleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "app_user")
public final class User extends AbstractEntity {

    @Basic(optional = false)
    @Column(unique = true)
    @NotNull
    private String login;

    @Basic(optional = false)
    @Column(name = "password_hash")
    @NotNull
    private String passwordHash;

    @Basic(optional = false)
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role_type")
    @NotNull
    private RoleType roleType;

    @Nullable
    private String email;

    @Column(name = "first_name")
    @Nullable
    private String firstName;

    @Column(name = "last_name")
    @Nullable
    private String lastName;

    @Column(name = "middle_name")
    @Nullable
    private String middleName;

    @Nullable
    private String phone;

    @Basic(optional = false)
    private Boolean locked = false;

    @Nullable
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Role> roles;

    @Nullable
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Project> projects;

    @Nullable
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Task> tasks;

    public User(@NotNull final String login,
                @NotNull final String passwordHash,
                @NotNull final RoleType roleType) {
        super();
        this.login = login;
        this.passwordHash = passwordHash;
        this.roleType = roleType;
    }

    public User() {
    }
    public boolean match(String login, String passwordHash) {
        return this.login.equals(login) && this.passwordHash.equals(passwordHash);
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

    @NotNull
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Nullable
    public List<Project> getProjects() {
        return this.projects;
    }

    @Nullable
    public List<Task> getTasks() {
        return this.tasks;
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

    public void setProjects(@Nullable final List<Project> projects) {
        this.projects = projects;
    }

    public void setTasks(@Nullable final List<Task> tasks) {
        this.tasks = tasks;
    }
}
