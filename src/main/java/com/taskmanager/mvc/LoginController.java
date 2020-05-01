package com.taskmanager.mvc;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;

@SessionAttributes({"currentUser"})
@Controller
public class LoginController {

    @GetMapping(value = "/doLogin")
    public String login() {
        return "log-in";
    }

    @GetMapping(value = "/loginFailed")
    public String loginError(@NotNull final Model model) {
        model.addAttribute("error", "The login or password are incorrect. Please, retry!");
        return "log-in";
    }

    @GetMapping(value = "/logout")
    public String logout(@NotNull final SessionStatus session) {
        SecurityContextHolder.getContext().setAuthentication(null);
        session.setComplete();
        return "redirect:/index";
    }

    @PostMapping(value = "/postLogin")
    public String postLogin(@NotNull final Model model, @NotNull final HttpSession session) {
        // read principal out of security context and set it to session
        @NotNull final UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        @NotNull final User user = (User) authentication.getPrincipal();
        validatePrinciple(user);
        model.addAttribute("currentUser", user.getUsername());
        session.setAttribute("login", user.getUsername());
        return "redirect:/project-list";
    }

    private void validatePrinciple(@NotNull final Object principal) {
        if (!(principal instanceof org.springframework.security.core.userdetails.User)) {
            throw new IllegalArgumentException("Principal can not be null!");
        }
    }
}