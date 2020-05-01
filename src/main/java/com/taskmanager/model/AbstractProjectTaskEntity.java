package com.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.taskmanager.enumerate.StatusType;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractProjectTaskEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    protected User user;

    @Basic(optional = false)
    @NotNull
    protected String name = "Placeholder";

    @Basic(optional = false)
    @NotNull
    protected String description = "Placeholder";

    @Column(name = "create_date", updatable = false)
    @NotNull
    protected Date createDate = new Date(System.currentTimeMillis());

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "start_date")
    @Nullable
    protected Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "finish_date")
    @Nullable
    protected Date finishDate;

    @Enumerated(value = EnumType.STRING)
    @Basic(optional = false)
    @Column(name = "status_type")
    @NotNull
    protected StatusType statusType = StatusType.PLANNED;

    AbstractProjectTaskEntity(@NotNull final String name, @NotNull final String description,
                              @Nullable final Date startDate, @Nullable final Date finishDate) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public AbstractProjectTaskEntity() {
    }

    @NotNull
    public User getUser() {
        return this.user;
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    @NotNull
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

    public void setUser(@NotNull final User user) {
        this.user = user;
    }

    public void setName(@NotNull final String name) {
        this.name = name;
    }

    public void setDescription(@NotNull final String description) {
        this.description = description;
    }

    public void setCreateDate(@NotNull final Date createDate) {
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
