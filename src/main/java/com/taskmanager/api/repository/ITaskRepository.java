package com.taskmanager.api.repository;

import com.taskmanager.exception.UserNotFoundException;
import com.taskmanager.model.Task;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITaskRepository extends JpaRepository<Task, String> {

    @Query(value = "SELECT t FROM Task t WHERE t.user.id = :userId")
    @NotNull List<Task> findAllByUserId(@Param("userId") @NotNull String userId) throws Exception;

    @Query(value = "SELECT t FROM Task t WHERE t.user.id = :userId AND t.id = :id")
    @Nullable Task findOneById(@Param("userId") @NotNull String userId, @Param("id") @NotNull String id) throws Exception;

    @Query(value = "SELECT t FROM Task t WHERE t.user.id = :userId AND t.name = :name")
    @Nullable List<Task> findOneByName(@Param("userId") @NotNull String userId, @Param("name") @NotNull String name) throws Exception;

    @Query(value = "SELECT t FROM Task t WHERE t.user.id = :userId AND t.project IS NOT NULL")
    @NotNull List<Task> findTasksWithProjectId(@Param("userId") @Nullable final String userId) throws UserNotFoundException;

    @Query(value = "SELECT t FROM Task t WHERE t.user.id = :userId AND t.project.id = :projectId")
    @NotNull List<Task> findTasksByProjectId(@Param("userId") @NotNull String userId, @Param("projectId") @NotNull String projectId) throws Exception;

    @Query(value = "SELECT t FROM Task t WHERE t.user.id = :userId AND t.project.id IS NULL")
    @NotNull List<Task> findTasksWithoutProject(@Param("userId") @NotNull String userId) throws Exception;

    @Query(value = "SELECT t FROM Task t WHERE t.user.id = :userId ORDER BY t.createDate ASC")
    @NotNull List<Task> findAllSortedByCreateDate(@Param("userId") @NotNull String userId) throws Exception;

    @Query(value = "SELECT t FROM Task t WHERE t.user.id = :userId ORDER BY t.startDate ASC")
    @NotNull List<Task> findAllSortedByStartDateAsc(@Param("userId") @NotNull String userId) throws Exception;

    @Query(value = "SELECT t FROM Task t WHERE t.user.id = :userId ORDER BY t.startDate DESC")
    @NotNull List<Task> findAllSortedByStartDateDesc(@Param("userId") @NotNull String userId) throws Exception;

    @Query(value = "SELECT t FROM Task t WHERE t.user.id = :userId ORDER BY t.finishDate ASC")
    @NotNull List<Task> findAllSortedByFinishDateAsc(@Param("userId") @NotNull String userId) throws Exception;

    @Query(value = "SELECT t FROM Task t WHERE t.user.id = :userId ORDER BY t.finishDate DESC")
    @NotNull List<Task> findAllSortedByFinishDateDesc(@Param("userId") @NotNull String userId) throws Exception;

    @Query(value = "SELECT *, CASE t.status_type WHEN 'PLANNED' THEN 1 WHEN 'INPROCESS' THEN 2 WHEN 'READY' THEN 3 "
            + "END AS status_order FROM app_task t WHERE t.user_id = :userId ORDER BY status_order ASC", nativeQuery = true)
    @NotNull List<Task> findAllSortedByStatusAsc(@Param("userId") @NotNull String userId) throws Exception;

    @Query(value = "SELECT *, CASE t.status_type WHEN 'PLANNED' THEN 1 WHEN 'INPROCESS' THEN 2 WHEN 'READY' THEN 3 "
            + "END AS status_order FROM app_task t WHERE t.user_id = :userId ORDER BY status_order DESC", nativeQuery = true)
    @NotNull List<Task> findAllSortedByStatusDesc(@Param("userId") @NotNull String userId) throws Exception;

    @Query(value = "SELECT t FROM Task t WHERE t.user.id = :userId AND (t.name LIKE :search OR t.description LIKE :search)")
    @NotNull List<Task> findAllBySearch(@Param("userId") @NotNull String userId, @Param("search") @NotNull String search) throws Exception;

    @Query(value = "SELECT t FROM Task t ORDER BY t.startDate ASC")
    @NotNull List<Task> findAllSortedByStartDateAsc() throws Exception;

    @Query(value = "SELECT t FROM Task t ORDER BY t.startDate DESC")
    @NotNull List<Task> findAllSortedByStartDateDesc() throws Exception;

    @Query(value = "SELECT t FROM Task t ORDER BY t.finishDate ASC")
    @NotNull List<Task> findAllSortedByFinishDateAsc() throws Exception;

    @Query(value = "SELECT t FROM Task t ORDER BY t.finishDate DESC")
    @NotNull List<Task> findAllSortedByFinishDateDesc() throws Exception;

    @Query(value = "SELECT *, CASE t.status_type WHEN 'PLANNED' THEN 1 WHEN 'INPROCESS' THEN 2 WHEN 'READY' THEN 3 "
            + "END AS status_order FROM app_task t ORDER BY status_order ASC", nativeQuery = true)
    @NotNull List<Task> findAllSortedByStatusAsc() throws Exception;

    @Query(value = "SELECT *, CASE t.status_type WHEN 'PLANNED' THEN 1 WHEN 'INPROCESS' THEN 2 WHEN 'READY' THEN 3 "
            + "END AS status_order FROM app_task t ORDER BY status_order DESC", nativeQuery = true)
    @NotNull List<Task> findAllSortedByStatusDesc() throws Exception;

    @Query(value = "SELECT t FROM Task t WHERE t.name LIKE :search OR t.description LIKE :search")
    @NotNull List<Task> findAllBySearch(@Param("search") @NotNull String search) throws Exception;
}
