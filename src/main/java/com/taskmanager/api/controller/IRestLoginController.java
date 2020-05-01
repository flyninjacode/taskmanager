package com.taskmanager.api.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(value = "/auth")
public interface IRestLoginController {
    @GetMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    String login(
            @RequestParam(value = "login") @NotNull String login,
            @RequestParam(value = "password") @NotNull String password
    );

    @GetMapping(value = "/session", produces = MediaType.APPLICATION_JSON_VALUE)
    Authentication session();

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    User user(@AuthenticationPrincipal(errorOnInvalidType = true) User user);

    @GetMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    String logout();
}
