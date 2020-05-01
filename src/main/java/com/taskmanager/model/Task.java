package com.taskmanager.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.taskmanager.enumerate.StatusType;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "app_task")
public final class Task extends AbstractProjectTaskEntity {

    @ManyToOne
    @JoinColumn(name = "project_id")
    @Nullable
    protected Project project;

    public Task() {
    }

    public Task(@NotNull final String name, @Nullable final String description,
                @Nullable final Date startDate, @Nullable final Date finishDate) {
        super(name, description, startDate, finishDate);
    }

    public Task(@NotNull final String name, @Nullable final String description,
                @NotNull final StatusType statusType) {
        this.name = name;
        this.description = description;
        this.statusType = statusType;
    }

    @Nullable
    public Project getProject() {
        return this.project;
    }

    public void setProject(@Nullable final Project project) {
        this.project = project;
    }
}
