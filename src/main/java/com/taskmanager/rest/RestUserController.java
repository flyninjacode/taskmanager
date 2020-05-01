package com.taskmanager.rest;

import com.taskmanager.api.service.IUserService;
import com.taskmanager.exception.EmptyInputException;
import com.taskmanager.exception.ProjectNotFoundException;
import com.taskmanager.exception.UserNotFoundException;
import com.taskmanager.model.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.taskmanager.dto.UserDTO;

import java.util.List;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestUserController {

    @NotNull
    private final IUserService userService;

    @Autowired
    public RestUserController(@NotNull final IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    @ResponseBody
    public List<UserDTO> findAll() throws Exception {
        return userService.getListUserDTO(userService.findAll());
    }

    @GetMapping("/search")
    @ResponseBody
    public List<UserDTO> findAllBySearch(
            @RequestParam(value = "search") @NotNull final String search
    ) throws Exception {
        return userService.getListUserDTO(userService.findAllBySearch(search));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(
            @RequestBody @NotNull final UserDTO user
    ) throws Exception, UserNotFoundException, ProjectNotFoundException {
        if (user.getLogin().isEmpty()) ResponseEntity.badRequest().body("The login is empty.");
        userService.persist(userService.getUserEntity(user));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(
            @PathVariable("id") @NotNull final String id
    ) throws Exception {
        if (id.isEmpty()) return ResponseEntity.badRequest().body("User id is empty.");
        userService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<UserDTO> update(
            @RequestBody @Nullable final UserDTO user,
            @PathVariable("id") @NotNull final String id
    ) throws Exception {
        if (id.isEmpty()) return ResponseEntity.badRequest().build();
        userService.merge(userService.getUserEntity(user));
        @Nullable final User updatedUser = userService.findOneById(id);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(userService.getUserDTO(updatedUser));
        }
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<UserDTO> view(
            @PathVariable(name = "id") @NotNull final String id
    ) throws Exception, UserNotFoundException, EmptyInputException {
        if (id.isEmpty()) return ResponseEntity.badRequest().build();
        @Nullable final User user = userService.findOneById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(userService.getUserDTO(user));
        }
    }
}
