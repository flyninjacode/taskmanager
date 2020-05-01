package com.taskmanager.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.taskmanager.model.AbstractEntity;

import java.util.List;

public abstract class AbstractService<T extends AbstractEntity> {

    public AbstractService() {
    }

    @Nullable
    abstract List<T> findAll() throws Exception;

    @Nullable
    abstract T findOneById(@Nullable final String id) throws Exception;

    abstract void persist(@NotNull final T entity) throws Exception;

    abstract void merge(@NotNull final T entity) throws Exception;

    abstract void remove(@Nullable final String id) throws Exception;

    abstract void removeAll() throws Exception;
}
