package com.taskmanager.api.service;

import com.taskmanager.exception.EmptyInputException;
import com.taskmanager.exception.ProjectNotFoundException;
import com.taskmanager.exception.UserNotFoundException;
import com.taskmanager.model.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.taskmanager.dto.ProjectDTO;

import java.util.List;

public interface IProjectService {
    @NotNull Project getProjectEntity(@Nullable ProjectDTO projectDTO) throws Exception;

    @NotNull ProjectDTO getProjectDTO(@Nullable Project project) throws Exception;

    @NotNull List<ProjectDTO> getListProjectDTO(@Nullable List<Project> projects) throws Exception;

    @NotNull List<Project> findAll();

    @NotNull List<Project> findAllByUserId(@Nullable String userId)
            throws Exception, UserNotFoundException;

    @Nullable Project findOneById(@Nullable String userId, @Nullable String id)
            throws Exception, EmptyInputException, UserNotFoundException;

    @NotNull List<Project> findOneByName(@Nullable String userId, @Nullable String name)
            throws Exception, EmptyInputException, UserNotFoundException;

    void persist(@Nullable Project project) throws ProjectNotFoundException;

    void merge(@Nullable Project project) throws ProjectNotFoundException;

    void remove(@Nullable String userId, @Nullable String id)
                    throws Exception, EmptyInputException, UserNotFoundException;

    void removeAllByUserId(@Nullable String userId)
                            throws Exception, UserNotFoundException;

    void removeAll();

    @NotNull List<Project> findAllSortedByCreateDate(@Nullable String userId)
            throws Exception, UserNotFoundException;

    @NotNull List<Project> findAllSortedByStartDateAsc(@Nullable String userId)
            throws Exception, UserNotFoundException;

    @NotNull List<Project> findAllSortedByStartDateDesc(@Nullable String userId)
            throws Exception, UserNotFoundException;

    @NotNull List<Project> findAllSortedByStartDateAsc()
            throws Exception, UserNotFoundException;

    @NotNull List<Project> findAllSortedByStartDateDesc()
            throws Exception, UserNotFoundException;

    @NotNull List<Project> findAllSortedByFinishDateAsc(@Nullable String userId)
            throws Exception, UserNotFoundException;

    @NotNull List<Project> findAllSortedByFinishDateDesc(@Nullable String userId)
            throws Exception, UserNotFoundException;

    @NotNull List<Project> findAllSortedByFinishDateAsc()
            throws Exception, UserNotFoundException;

    @NotNull List<Project> findAllSortedByFinishDateDesc()
            throws Exception, UserNotFoundException;

    @NotNull List<Project> findAllSortedByStatusAsc(@Nullable String userId)
            throws Exception, UserNotFoundException;

    @NotNull List<Project> findAllSortedByStatusDesc(@Nullable String userId)
            throws Exception, UserNotFoundException;

    @NotNull List<Project> findAllSortedByStatusAsc()
            throws Exception, UserNotFoundException;

    @NotNull List<Project> findAllSortedByStatusDesc()
            throws Exception, UserNotFoundException;

    @NotNull List<Project> findAllBySearch(@Nullable String userId, @Nullable String search)
            throws Exception;

    @NotNull List<Project> findAllBySearch(@Nullable String search)
            throws Exception;
}
