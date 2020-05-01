package com.taskmanager.mvc;

import com.taskmanager.api.service.IUserService;
import com.taskmanager.enumerate.RoleType;
import com.taskmanager.exception.ProjectNotFoundException;
import com.taskmanager.exception.UserNotFoundException;
import com.taskmanager.model.Role;
import com.taskmanager.model.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@SessionAttributes({"currentUser"})
@Controller
public class UserController {

    @NotNull
    private final IUserService userService;

    @Autowired
    public UserController(@NotNull final IUserService userService) {
        this.userService = userService;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/user-list")
    public String findAll(@NotNull final Model model) throws Exception {
        @NotNull final List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user-list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/user-create")
    public String createUser(@NotNull final Model model) throws Exception {
        return "user-create";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/user-create")
    public String createUser(
            @ModelAttribute("login") @NotNull final String login,
            @ModelAttribute("password") @NotNull final String password,
            @ModelAttribute("roleType") @NotNull final String roleType,
            @ModelAttribute("email") @Nullable final String email,
            @ModelAttribute("firstName") @Nullable final String firstName,
            @ModelAttribute("lastName") @Nullable final String lastName,
            @ModelAttribute("phone") @Nullable final String phone
    ) throws UserNotFoundException {
        @NotNull final User user = new User(login, password, RoleType.valueOf(roleType));
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        @NotNull final List<Role> userRoles = new ArrayList<>();
        @NotNull Role role = new Role();
        role.setUser(user);
        userRoles.add(role);
        if (!user.getRoleType().equals(role.getRoleType())) {
            role = new Role(user, user.getRoleType());
            userRoles.add(role);
        }
        user.setRoles(userRoles);
        userService.persist(user);
        return "redirect:/user-list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/user-delete/{id}")
    public String deleteUser(
            @PathVariable("id") @NotNull final String id
    ) throws Exception {
        userService.remove(id);
        return "redirect:/user-list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/user-update/{id}")
    public String updateUserForm(
            @PathVariable("id") @NotNull final String id,
            @NotNull final Model model
    ) throws Exception {
        @Nullable final User user = userService.findOneById(id);
        model.addAttribute("user", user);
        return "user-update";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/user-update")
    public String updateUser(
            @ModelAttribute("id") @NotNull final String id,
            @ModelAttribute("login") @NotNull final String login,
            @ModelAttribute("password") @NotNull final String password,
            @ModelAttribute("roleType") @NotNull final String roleType,
            @ModelAttribute("email") @Nullable final String email,
            @ModelAttribute("firstName") @Nullable final String firstName,
            @ModelAttribute("lastName") @Nullable final String lastName,
            @ModelAttribute("phone") @Nullable final String phone
    ) throws Exception, UserNotFoundException, ProjectNotFoundException {
        @Nullable final User user = userService.findOneById(id);
        if (user == null) throw new UserNotFoundException();
        user.setLogin(login);
        user.setPasswordHash(password);
        user.setRoleType(RoleType.valueOf(roleType));
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        @NotNull final List<Role> userRoles = new ArrayList<>();
        @NotNull Role role = new Role();
        role.setUser(user);
        userRoles.add(role);
        if (!user.getRoleType().equals(role.getRoleType())) {
            role = new Role(user, user.getRoleType());
            userRoles.add(role);
        }
        user.setRoles(userRoles);
        userService.merge(user);
        return "redirect:/user-list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/user-view/{id}")
    public String viewProjectForm(
            @PathVariable("id") @NotNull final String id,
            @NotNull final Model model
    ) throws Exception {
        if (id.isEmpty()) throw new UserNotFoundException();
        @Nullable final User user = userService.findOneById(id);
        if (user == null) throw new UserNotFoundException();
        model.addAttribute("user", user);
        return "user-view";
    }

    @Secured("ROLE_ADMIN")
    @Nullable
    @GetMapping("/user-search")
    public String findAllBySearch(
            @RequestParam("search") @NotNull final String search,
            @NotNull final Model model
    ) throws Exception {
        @NotNull final List<User> userSearch = userService.findAllBySearch(search);
        if (userSearch.isEmpty()) return null;
        model.addAttribute("userSearch", userSearch);
        return "user-search";
    }
}
