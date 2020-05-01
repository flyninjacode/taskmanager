package com.taskmanager.api.repository;

import com.taskmanager.model.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT u FROM User u WHERE u.login = :login")
    @Nullable User findOneByLogin(@Param("login") @NotNull String login);

    @Query(value = "SELECT u FROM User u WHERE u.login LIKE :search OR u.email LIKE :search "
            + "OR u.firstName LIKE :search OR u.lastName LIKE :search OR u.middleName LIKE :search")
    @NotNull List<User> findAllBySearch(@Param("search") @NotNull String search) throws Exception;
}
