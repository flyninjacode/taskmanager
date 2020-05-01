package com.taskmanager.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.annotation.Transient;
import com.taskmanager.enumerate.StatusType;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "app_project")
public final class Project extends AbstractProjectTaskEntity {

    @NotNull
    @Transient
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    public Project() {
    }

    public Project(@NotNull final String name, @Nullable final String description,
                   @Nullable final Date startDate, @Nullable final Date finishDate) {
        super(name, description, startDate, finishDate);
    }

    public Project(@NotNull final String name, @Nullable final String description,
                   @NotNull final StatusType statusType) {
        this.name = name;
        this.description = description;
        this.statusType = statusType;
    }

    @NotNull
    public List<Task> getTasks() {
        return this.tasks;
    }

    public void setTasks(@NotNull final List<Task> tasks) {
        this.tasks = tasks;
    }
}
