package com.taskmanager.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taskmanager.api.service.IProjectService;
import com.taskmanager.api.service.ITaskService;
import com.taskmanager.api.service.IUserService;
import com.taskmanager.api.repository.IUserRepository;
import com.taskmanager.dto.UserDTO;
import com.taskmanager.exception.DuplicateUserException;
import com.taskmanager.exception.UserNotFoundException;
import com.taskmanager.model.User;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService extends AbstractService<User> implements IUserService {

    @Autowired
    @NotNull private IUserRepository userRepository;

    @Autowired
    @NotNull private IProjectService projectService;

    @Autowired
    @NotNull private ITaskService taskService;

    public UserService() {
    }

    @Override
    @NotNull
    public User getUserEntity(@Nullable final UserDTO userDTO) throws Exception {
        if (userDTO == null) throw new UserNotFoundException();
        @NotNull final User user = new User();
        user.setId(userDTO.getId());
        user.setLogin(userDTO.getLogin());
        user.setPasswordHash(userDTO.getPasswordHash());
        user.setRoleType(userDTO.getRoleType());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setPhone(userDTO.getPhone());
        user.setLocked(userDTO.getLocked());
        user.setProjects(projectService.findAllByUserId(userDTO.getId()));
        user.setTasks(taskService.findAllByUserId(userDTO.getId()));
        return user;
    }

    @Override
    @NotNull
    public UserDTO getUserDTO(@Nullable final User user) throws UserNotFoundException {
        if (user == null) throw new UserNotFoundException();
        @NotNull final UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setLogin(user.getLogin());
        userDTO.setPasswordHash(user.getPasswordHash());
        userDTO.setRoleType(user.getRoleType());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setMiddleName(user.getMiddleName());
        userDTO.setPhone(user.getPhone());
        userDTO.setLocked(user.getLocked());
        return userDTO;
    }

    @Override
    @NotNull
    public List<UserDTO> getListUserDTO(@Nullable final List<User> users) throws UserNotFoundException {
        if (users == null) throw new UserNotFoundException();
        @NotNull final List<UserDTO> listUserDTO = new ArrayList<>();
        for (@NotNull final User user : users)
            listUserDTO.add(getUserDTO(user));
        return listUserDTO;
    }

    @Override
    @Nullable
    public User registerUser(User user) throws DuplicateUserException, UserNotFoundException {
        if (isLoginExist(user.getLogin())) return null;
        persist(user);
        return user;
    }

    private boolean isLoginExist(String email) {
        return userRepository.findOneByLogin(email) != null;
    }

    @NotNull
    @Override
    public List<User> findAll() throws Exception {
        return userRepository.findAll();
    }

    @Override
    @NotNull
    public List<User> findAllBySearch(@Nullable final String search) throws Exception {
        if (search == null || search.isEmpty()) throw new Exception("[The search query is empty.]");
        return userRepository.findAllBySearch('%' + search + '%');
    }

    @Nullable
    @Override
    public User findOneById(@Nullable final String id) throws Exception, UserNotFoundException {
        if (id == null || id.isEmpty()) throw new UserNotFoundException();
        return userRepository.getOne(id);
    }

    @Override
    @Nullable
    public User findOneByLogin(@Nullable final String login) throws Exception, UserNotFoundException {
        if (login == null || login.isEmpty()) throw new UserNotFoundException();
        return userRepository.findOneByLogin(login);
    }

    @Override
    public void persist(@Nullable final User user) throws UserNotFoundException {
        if (user == null) throw new UserNotFoundException();
        userRepository.save(user);
    }

    @Override
    public void merge(@Nullable User user) throws UserNotFoundException {
        if (user == null) throw new UserNotFoundException();
        userRepository.save(user);
    }

    @Override
    public void remove(@Nullable String id) throws Exception, UserNotFoundException {
        if (id == null || id.isEmpty()) throw new UserNotFoundException();
        @NotNull final User user = userRepository.getOne(id);
        userRepository.delete(user);
    }

    @Override
    public void removeAll() {
        userRepository.deleteAll();
    }
}
