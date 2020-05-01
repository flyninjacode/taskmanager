package com.taskmanager.api.service;

import com.taskmanager.exception.EmptyInputException;
import com.taskmanager.exception.TaskNotFoundException;
import com.taskmanager.exception.UserNotFoundException;
import com.taskmanager.model.Task;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.taskmanager.dto.TaskDTO;

import java.util.List;

public interface ITaskService {
    @NotNull Task getTaskEntity(@Nullable TaskDTO taskDTO) throws Exception;

    @NotNull TaskDTO getTaskDTO(@Nullable Task task) throws Exception;

    @NotNull List<TaskDTO> getListTaskDTO(@Nullable List<Task> tasks) throws Exception;

    @NotNull List<Task> findAll() throws Exception;

    @NotNull List<Task> findAllByUserId(@Nullable String userId)
            throws Exception, UserNotFoundException;

    @NotNull List<Task> findTasksWithProjectId(@Nullable String userId) throws UserNotFoundException;

    @Nullable Task findOneById(@Nullable String userId, @Nullable String id)
            throws Exception, UserNotFoundException, EmptyInputException;

    @NotNull List<Task> findOneByName(@Nullable String userId, @Nullable String name)
            throws Exception, UserNotFoundException, EmptyInputException;

    void persist(@Nullable Task task) throws TaskNotFoundException;

    void merge(@Nullable Task task) throws TaskNotFoundException;

    void remove(@Nullable String userId, @Nullable String id)
                    throws Exception, UserNotFoundException, EmptyInputException;

    void removeAllByUserId(@Nullable String userId)
                            throws Exception, UserNotFoundException;

    void removeAll();

    void removeTasksWithProjectId(@Nullable String userId)
                                    throws Exception, UserNotFoundException;

    void removeProjectTasks(@Nullable String userId, @Nullable String projectId)
                                            throws Exception, UserNotFoundException, EmptyInputException;

    @NotNull List<Task> findTasksByProjectId(@Nullable String userId, @Nullable String projectId)
            throws Exception, UserNotFoundException, EmptyInputException;

    @NotNull List<Task> findTasksWithoutProject(@Nullable String userId)
            throws Exception, UserNotFoundException;

    @NotNull List<Task> findAllSortedByCreateDate(@Nullable String userId)
            throws Exception, UserNotFoundException;

    @NotNull List<Task> findAllSortedByStartDateAsc(@Nullable String userId)
            throws Exception, UserNotFoundException;

    @NotNull List<Task> findAllSortedByStartDateDesc(@Nullable String userId)
            throws Exception, UserNotFoundException;

    @NotNull List<Task> findAllSortedByStartDateAsc()
            throws Exception, UserNotFoundException;

    @NotNull List<Task> findAllSortedByStartDateDesc()
            throws Exception, UserNotFoundException;

    @NotNull List<Task> findAllSortedByFinishDateAsc(@Nullable String userId)
            throws Exception, UserNotFoundException;

    @NotNull List<Task> findAllSortedByFinishDateDesc(@Nullable String userId)
            throws Exception, UserNotFoundException;

    @NotNull List<Task> findAllSortedByFinishDateAsc()
            throws Exception, UserNotFoundException;

    @NotNull List<Task> findAllSortedByFinishDateDesc()
            throws Exception, UserNotFoundException;

    @NotNull List<Task> findAllSortedByStatusAsc(@Nullable String userId)
            throws Exception, UserNotFoundException;

    List<Task> findAllSortedByStatusDesc(@Nullable String userId)
            throws Exception, UserNotFoundException;

    @NotNull List<Task> findAllSortedByStatusAsc()
            throws Exception, UserNotFoundException;

    @NotNull List<Task> findAllSortedByStatusDesc()
            throws Exception, UserNotFoundException;

    @NotNull List<Task> findAllBySearch(@Nullable String userId, @Nullable String search)
            throws Exception;

    @NotNull List<Task> findAllBySearch(@Nullable String search) throws Exception;
}
