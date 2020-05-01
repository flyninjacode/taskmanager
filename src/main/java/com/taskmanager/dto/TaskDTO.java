package com.taskmanager.dto;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Date;

@XmlAccessorType(XmlAccessType.PROPERTY)
public final class TaskDTO extends AbstractProjectTaskEntityDTO implements Serializable {

    @Nullable
    protected String projectId;

    public TaskDTO() {
    }

    public TaskDTO(@NotNull final String name, @NotNull final String description,
                   @Nullable final Date startDate, @Nullable final Date finishDate) {
        super(name, description, startDate, finishDate);
    }

    @Nullable
    public String getProjectId() {
        return this.projectId;
    }

    public void setProjectId(@Nullable final String projectId) {
        this.projectId = projectId;
    }
}
