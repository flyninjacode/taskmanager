package com.taskmanager.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.taskmanager.api.repository.IUserRepository;
import com.taskmanager.model.Role;
import com.taskmanager.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceBean implements UserDetailsService {

    @Autowired
    @NotNull
    private IUserRepository userRepository;

    @Nullable
    @Override
    public UserDetails loadUserByUsername(@Nullable final String username) throws UsernameNotFoundException {
        @Nullable final User user = findUserByLogin(username);
        if (user == null) throw new UsernameNotFoundException("The user is not found by login: " + username);
        org.springframework.security.core.userdetails.User.UserBuilder builder = null;
        builder = org.springframework.security.core.userdetails.User.withUsername(username);
        builder.password(new BCryptPasswordEncoder().encode(user.getPasswordHash()));
        List<Role> userRoles = user.getRoles();
        List<String> roles = new ArrayList<>();
        for (Role role : userRoles) roles.add(role.toString());
        builder.roles(roles.toArray(new String[]{}));
        return builder.build();
    }

    @Nullable
    private User findUserByLogin(@Nullable final String login) {
        if (login == null || login.isEmpty()) return null;
        @Nullable User user = null;
        try {
            user = userRepository.findOneByLogin(login);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
