package com.taskmanager.rest;

import com.taskmanager.api.controller.IRestLoginController;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/auth")
public class RestLoginController implements IRestLoginController {
    @Resource
    AuthenticationManager authenticationManager;

    @Override
    @GetMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public String login(
            @RequestParam(value = "login") @NotNull final String login,
            @RequestParam(value = "password") @NotNull final String password
    ) {
        try {
            final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login, password);
            final Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok(authentication.isAuthenticated()).toString();
        } catch (Exception e) {
            return ResponseEntity.ok(e).toString();
        }
    }

    @Override
    @GetMapping(value = "/session", produces = MediaType.APPLICATION_JSON_VALUE)
    public Authentication session() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public User user(@AuthenticationPrincipal(errorOnInvalidType = true) final User user) {
        return user;
    }

    @Override
    @GetMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public String logout() {
        try {
            SecurityContextHolder.getContext().setAuthentication(null);
            return ResponseEntity.ok("The logout ended up successfully.").toString();
        } catch (Exception e) {
            return ResponseEntity.ok(e).toString();
        }
    }
}