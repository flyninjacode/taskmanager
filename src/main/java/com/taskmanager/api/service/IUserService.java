package com.taskmanager.api.service;

import com.taskmanager.exception.DuplicateUserException;
import com.taskmanager.exception.UserNotFoundException;
import com.taskmanager.model.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.taskmanager.dto.UserDTO;

import java.util.List;

public interface IUserService {
    @NotNull
    User getUserEntity(@Nullable UserDTO userDTO) throws Exception;

    @NotNull
    UserDTO getUserDTO(@Nullable User user) throws UserNotFoundException;

    @NotNull List<UserDTO> getListUserDTO(@Nullable List<User> users) throws UserNotFoundException;

    @Nullable
    User registerUser(User user) throws DuplicateUserException, UserNotFoundException;

    @NotNull List<User> findAll() throws Exception;

    @NotNull List<User> findAllBySearch(@Nullable String search) throws Exception;

    @Nullable
    User findOneById(@Nullable String id) throws Exception, UserNotFoundException;

    @Nullable
    User findOneByLogin(@Nullable String login) throws Exception, UserNotFoundException;

    void persist(@Nullable User user) throws UserNotFoundException;

    void merge(@Nullable User user) throws UserNotFoundException;

    void remove(@Nullable String id) throws Exception, UserNotFoundException;

    void removeAll();
}
