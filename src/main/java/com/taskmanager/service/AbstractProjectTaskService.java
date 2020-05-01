package com.taskmanager.service;

import org.jetbrains.annotations.Nullable;
import com.taskmanager.model.AbstractProjectTaskEntity;

import java.util.List;

public abstract class AbstractProjectTaskService<T extends AbstractProjectTaskEntity> {

    public AbstractProjectTaskService() {
    }

    @Nullable
    abstract public List<T> findAll() throws Exception;

    @Nullable
    abstract public List<T> findAllByUserId(@Nullable final String userId) throws Exception;

    @Nullable
    abstract public T findOneById(@Nullable final String userId, @Nullable final String id) throws Exception;

    @Nullable
    abstract public List<T> findOneByName(@Nullable final String userId, @Nullable final String name) throws Exception;

    abstract public void persist(@Nullable final T entity) throws Exception;

    abstract public void remove(@Nullable final String userId, @Nullable final String id) throws Exception;

    abstract public void removeAllByUserId(@Nullable final String userId) throws Exception;

    abstract public void removeAll() throws Exception;

    @Nullable
    abstract public List<T> findAllBySearch(@Nullable final String userId, @Nullable final String search) throws Exception;
}
