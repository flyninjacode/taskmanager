package com.taskmanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.taskmanager.enumerate.StatusType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Date;

public abstract class AbstractProjectTaskEntityDTO extends AbstractEntityDTO implements Serializable {

    @NotNull
    protected String userId;

    @NotNull
    protected String name = "Placeholder";

    @Nullable
    protected String description = "Placeholder";

    @NotNull
    protected Date createDate = new Date(System.currentTimeMillis());

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Nullable
    protected Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Nullable
    protected Date finishDate;

    @NotNull
    protected StatusType statusType = StatusType.PLANNED;

    public AbstractProjectTaskEntityDTO() {
    }

    AbstractProjectTaskEntityDTO(@NotNull final String name, @NotNull final String description,
                                 @Nullable final Date startDate, @Nullable final Date finishDate) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public @NotNull String getUserId() {
        return this.userId;
    }

    public @NotNull String getName() {
        return this.name;
    }

    @Nullable
    public String getDescription() {
        return this.description;
    }

    @NotNull
    public Date getCreateDate() {
        return this.createDate;
    }

    @Nullable
    public Date getStartDate() {
        return this.startDate;
    }

    @Nullable
    public Date getFinishDate() {
        return this.finishDate;
    }

    @NotNull
    public StatusType getStatusType() {
        return this.statusType;
    }

    public void setUserId(@NotNull final String userId) {
        this.userId = userId;
    }

    public void setName(@NotNull final String name) {
        this.name = name;
    }

    public void setDescription(@Nullable final String description) {
        this.description = description;
    }

    public void setCreateDate( final Date createDate) {
        this.createDate = createDate;
    }

    public void setStartDate(@Nullable final Date startDate) {
        this.startDate = startDate;
    }

    public void setFinishDate(@Nullable final Date finishDate) {
        this.finishDate = finishDate;
    }

    public void setStatusType(@NotNull final StatusType statusType) {
        this.statusType = statusType;
    }
}
