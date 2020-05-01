package com.taskmanager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;
import com.taskmanager.dto.UserDTO;
import com.taskmanager.enumerate.RoleType;
import com.taskmanager.service.PropertyService;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = PropertyService.class)
@TestPropertySource
public class RestUserControllerMvcIT {

    @Autowired
    private PropertyService property;

    @Nullable
    private UserDTO user;

    @NotNull
    private final RestTemplate restTemplate = new RestTemplate();

    @NotNull
    private final HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    public void setUp() throws Exception {
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        @NotNull final String login = "testUser";
        @NotNull final String pass = "testUser";
        user = new UserDTO(login, pass, RoleType.valueOf("ADMIN"));
        @NotNull final HttpEntity<UserDTO> entityUser = new HttpEntity<>(user, headers);
        @NotNull String createUrl = property.getServer() + property.getUserMapping() + property.getRequestCreate();
        restTemplate.postForEntity(createUrl, entityUser, UserDTO.class);
    }

    @AfterEach
    public void tearDown() {
        @NotNull String deleteUrl = property.getServer() + property.getUserMapping() + property.getRequestDelete();
        restTemplate.delete(deleteUrl + user.getId());
    }

    @Test
    public void testFindAll() {
        @NotNull final String findAllUrl = property.getServer() + property.getUserMapping() + property.getRequestList();
        @NotNull final ResponseEntity<UserDTO[]> response = restTemplate.getForEntity(findAllUrl, UserDTO[].class);
        @Nullable final UserDTO[] users = response.getBody();
        assertNotNull(users);
        assertEquals(2, users.length);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreate() throws Exception {
        @NotNull final String login = "create";
        @NotNull final String pass = "create";
        @NotNull UserDTO temp = new UserDTO();
        temp.setLogin(login);
        temp.setPasswordHash(pass);
        temp.setRoleType(RoleType.valueOf("USER"));
        @NotNull final HttpEntity<UserDTO> entityProject = new HttpEntity<>(temp, headers);
        @NotNull final String createUrl = property.getServer() + property.getUserMapping() + property.getRequestCreate();
        @NotNull final ResponseEntity<UserDTO> response = restTemplate.postForEntity(createUrl, entityProject, UserDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        @NotNull String deleteUrl = property.getServer() + property.getUserMapping() + property.getRequestDelete();
        restTemplate.delete(deleteUrl + temp.getId());
    }

    @Test
    public void testUpdate() throws Exception {
        @NotNull final String login = "update";
        @NotNull final String pass = "update";
        user.setLogin(login);
        user.setPasswordHash(pass);
        @NotNull final HttpEntity<UserDTO> entityProject = new HttpEntity<>(user, headers);
        @NotNull final String updateUrl = property.getServer() + property.getUserMapping() + property.getRequestUpdate();
        @NotNull final String url = updateUrl + user.getId();
        @NotNull final ResponseEntity<UserDTO> response = restTemplate.postForEntity(url, entityProject, UserDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDelete() throws Exception {
        @NotNull final String login = "delete";
        @NotNull final String pass = "delete";
        @NotNull UserDTO temp = new UserDTO();
        temp.setLogin(login);
        temp.setPasswordHash(pass);
        temp.setRoleType(RoleType.valueOf("USER"));
        @NotNull final HttpEntity<UserDTO> entityProject = new HttpEntity<>(temp, headers);
        @NotNull final String createUrl = property.getServer() + property.getUserMapping() + property.getRequestCreate();
        @NotNull final ResponseEntity<UserDTO> response = restTemplate.postForEntity(createUrl, entityProject, UserDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        @NotNull String deleteUrl = property.getServer() + property.getUserMapping() + property.getRequestDelete();
        @NotNull String url = deleteUrl + temp.getId();
        restTemplate.delete(url);
    }

    @Test
    public void testView() throws Exception {
        @NotNull final String viewUrl = property.getServer() + property.getUserMapping() + property.getRequestView();
        @NotNull String url = viewUrl + user.getId();
        @NotNull final ResponseEntity<UserDTO> response = restTemplate.getForEntity(url, UserDTO.class);
        @Nullable final UserDTO temp = response.getBody();
        assertNotNull(temp);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}