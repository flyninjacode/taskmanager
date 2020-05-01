package com.taskmanager.mvc;

import com.taskmanager.api.service.IUserService;
import com.taskmanager.enumerate.RoleType;
import com.taskmanager.exception.DuplicateUserException;
import com.taskmanager.exception.UserNotFoundException;
import com.taskmanager.model.Role;
import com.taskmanager.model.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RegisterController {

    @NotNull
    private final IUserService userService;

    @Autowired
    public RegisterController(@NotNull final IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String registerUser(@NotNull final Model model) throws Exception {
        return "sign-up";
    }

    @PostMapping("/signup")
    public String registerUser(
            @NotNull final Model model,
            @ModelAttribute("login") @NotNull final String login,
            @ModelAttribute("password") @NotNull final String password,
            @ModelAttribute("roleType") @NotNull final String roleType,
            @ModelAttribute("email") @Nullable final String email,
            @ModelAttribute("firstName") @Nullable final String firstName,
            @ModelAttribute("lastName") @Nullable final String lastName,
            @ModelAttribute("phone") @Nullable final String phone
    ) throws UserNotFoundException, DuplicateUserException {
        @NotNull final User user =
                new User(login, password, RoleType.valueOf(roleType));
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
        @Nullable final User registered = userService.registerUser(user);
        if (registered != null)
            model.addAttribute("message", "You are successfully registered as " + model.asMap().get("login"));
        return "redirect:/login";
    }
}
