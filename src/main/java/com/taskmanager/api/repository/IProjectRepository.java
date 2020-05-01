package com.taskmanager.api.repository;

import com.taskmanager.model.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProjectRepository extends JpaRepository<Project, String> {

    @Query(value = "SELECT p FROM Project p WHERE p.user.id = :userId")
    @NotNull List<Project> findAllByUserId(@Param("userId") @NotNull String userId) throws Exception;

    @Query(value = "SELECT p FROM Project p WHERE p.user.id = :userId AND p.id = :id")
    @Nullable
    Project findOneById(@Param("userId") @NotNull String userId, @Param("id") @NotNull String id) throws Exception;

    @Query(value = "SELECT p FROM Project p WHERE p.user.id = :userId AND p.name = :name")
    @NotNull List<Project> findOneByName(@Param("userId") @NotNull String userId, @Param("name") @NotNull String name) throws Exception;

    @Query(value = "SELECT p FROM Project p WHERE p.user.id = :userId ORDER BY p.createDate ASC")
    @NotNull List<Project> findAllSortedByCreateDate(@Param("userId") @NotNull String userId) throws Exception;

    @Query(value = "SELECT p FROM Project p WHERE p.user.id = :userId ORDER BY p.startDate ASC")
    @NotNull List<Project> findAllSortedByStartDateAsc(@Param("userId") @NotNull String userId) throws Exception;

    @Query(value = "SELECT p FROM Project p WHERE p.user.id = :userId ORDER BY p.startDate DESC")
    @NotNull List<Project> findAllSortedByStartDateDesc(@Param("userId") @NotNull String userId) throws Exception;

    @Query(value = "SELECT p FROM Project p WHERE p.user.id = :userId ORDER BY p.finishDate ASC")
    @NotNull List<Project> findAllSortedByFinishDateAsc(@Param("userId") @NotNull String userId) throws Exception;

    @Query(value = "SELECT p FROM Project p WHERE p.user.id = :userId ORDER BY p.finishDate DESC")
    @NotNull List<Project> findAllSortedByFinishDateDesc(@Param("userId") @NotNull String userId) throws Exception;

    @Query(value = "SELECT *, CASE p.status_type WHEN 'PLANNED' THEN 1 WHEN 'INPROCESS' THEN 2 WHEN 'READY' THEN 3 "
            + "END AS status_order FROM app_project p WHERE p.user_id = :userId ORDER BY status_order ASC", nativeQuery = true)
    @NotNull List<Project> findAllSortedByStatusAsc(@Param("userId") @NotNull String userId) throws Exception;

    @Query(value = "SELECT *, CASE p.status_type WHEN 'PLANNED' THEN 1 WHEN 'INPROCESS' THEN 2 WHEN 'READY' THEN 3 "
            + "END AS status_order FROM app_project p WHERE p.user_id = :userId ORDER BY status_order DESC", nativeQuery = true)
    @NotNull List<Project> findAllSortedByStatusDesc(@Param("userId") @NotNull String userId) throws Exception;

    @Query(value = "SELECT p FROM Project p WHERE p.user.id = :userId AND (p.name LIKE :search OR p.description LIKE :search)")
    @NotNull List<Project> findAllBySearch(@Param("userId") @NotNull String userId, @Param("search") @NotNull String search) throws Exception;

    @Query(value = "SELECT p FROM Project p ORDER BY p.startDate ASC")
    @NotNull List<Project> findAllSortedByStartDateAsc() throws Exception;

    @Query(value = "SELECT p FROM Project p ORDER BY p.startDate DESC")
    @NotNull List<Project> findAllSortedByStartDateDesc() throws Exception;

    @Query(value = "SELECT p FROM Project p ORDER BY p.finishDate ASC")
    @NotNull List<Project> findAllSortedByFinishDateAsc() throws Exception;

    @Query(value = "SELECT p FROM Project p ORDER BY p.finishDate DESC")
    @NotNull List<Project> findAllSortedByFinishDateDesc() throws Exception;

    @Query(value = "SELECT *, CASE p.status_type WHEN 'PLANNED' THEN 1 WHEN 'INPROCESS' THEN 2 WHEN 'READY' THEN 3 "
            + "END AS status_order FROM app_project p ORDER BY status_order ASC", nativeQuery = true)
    @NotNull List<Project> findAllSortedByStatusAsc() throws Exception;

    @Query(value = "SELECT *, CASE p.status_type WHEN 'PLANNED' THEN 1 WHEN 'INPROCESS' THEN 2 WHEN 'READY' THEN 3 "
            + "END AS status_order FROM app_project p ORDER BY status_order DESC", nativeQuery = true)
    @NotNull List<Project> findAllSortedByStatusDesc() throws Exception;

    @Query(value = "SELECT p FROM Project p WHERE p.name LIKE :search OR p.description LIKE :search")
    @NotNull List<Project> findAllBySearch(@Param("search") @NotNull String search) throws Exception;
}
