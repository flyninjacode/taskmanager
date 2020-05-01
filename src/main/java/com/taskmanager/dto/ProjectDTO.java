package com.taskmanager.dto;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Date;

@XmlAccessorType(XmlAccessType.PROPERTY)
public final class ProjectDTO extends AbstractProjectTaskEntityDTO implements Serializable {

    public ProjectDTO() {
    }

    public ProjectDTO(@NotNull final String name, @NotNull final String description,
                      @Nullable final Date startDate, @Nullable final Date finishDate) {
        super(name, description, startDate, finishDate);
    }
}
