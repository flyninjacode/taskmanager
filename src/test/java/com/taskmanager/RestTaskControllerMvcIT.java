package com.taskmanager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;
import com.taskmanager.dto.TaskDTO;
import com.taskmanager.dto.UserDTO;
import com.taskmanager.enumerate.RoleType;
import com.taskmanager.service.PropertyService;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = PropertyService.class)
@TestPropertySource
public class RestTaskControllerMvcIT {

    @LocalServerPort
    private int port;

    @Autowired
    private PropertyService property;

    @Nullable
    private UserDTO user;

    @Nullable
    private TaskDTO task;

    @NotNull
    private final RestTemplate restTemplate = new RestTemplate();

    @NotNull
    private final HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    public void setUp() throws Exception {
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        @NotNull final String login = "testTaskUser";
        @NotNull final String pass = "testTaskUser";
        user = new UserDTO(login, pass, RoleType.valueOf("ADMIN"));
        @NotNull final HttpEntity<UserDTO> entityUser = new HttpEntity<>(user, headers);
        @NotNull String createUrl = property.getServer() + property.getUserMapping() + property.getRequestCreate();
        restTemplate.postForEntity(createUrl, entityUser, UserDTO.class);
        @NotNull final String name = "task";
        @NotNull final String description = "description";
        task = new TaskDTO();
        task.setUserId(user.getId());
        task.setName(name);
        task.setDescription(description);
        @NotNull final HttpEntity<TaskDTO> entityProject = new HttpEntity<>(task, headers);
        createUrl = property.getServer() + property.getTaskMapping() + property.getRequestCreate();
        restTemplate.postForEntity(createUrl, entityProject, TaskDTO.class);
    }

    @AfterEach
    public void tearDown() {
        @NotNull String deleteUrl = property.getServer() + property.getUserMapping() + property.getRequestDelete();
        restTemplate.delete(deleteUrl + user.getId());
    }

    @Test
    public void testFindAll() {
        @NotNull final String findAllUrl = property.getServer() + property.getTaskMapping() + property.getRequestList();
        @NotNull final ResponseEntity<TaskDTO[]> response = restTemplate.getForEntity(findAllUrl, TaskDTO[].class);
        @Nullable final TaskDTO[] tasks = response.getBody();
        assertNotNull(tasks);
        assertEquals(2, tasks.length);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testFindAllByStatusAsc() {
        @NotNull final String findAllUrl = property.getServer() + property.getTaskMapping() + property.getRequestListStatusAsc();
        @NotNull final ResponseEntity<TaskDTO[]> response = restTemplate.getForEntity(findAllUrl, TaskDTO[].class);
        @Nullable final TaskDTO[] tasks = response.getBody();
        @NotNull int prevStatus = 0;
        for (@Nullable final TaskDTO TaskDTO : tasks) {
            @NotNull int currStatus = TaskDTO.getStatusType().ordinal();
            assertTrue(prevStatus <= currStatus);
            prevStatus = currStatus;
        }
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testFindAllByStatusDesc() {
        @NotNull final String findAllUrl = property.getServer() + property.getTaskMapping() + property.getRequestListStatusDesc();
        @NotNull final ResponseEntity<TaskDTO[]> response = restTemplate.getForEntity(findAllUrl, TaskDTO[].class);
        @Nullable final TaskDTO[] tasks = response.getBody();
        @NotNull int prevStatus = 2;
        for (@Nullable final TaskDTO TaskDTO : tasks) {
            @NotNull int currStatus = TaskDTO.getStatusType().ordinal();
            assertTrue(prevStatus >= currStatus);
            prevStatus = currStatus;
        }
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreate() throws Exception {
        @NotNull final String name = "create";
        @NotNull final String description = "create";
        task = new TaskDTO();
        task.setUserId(user.getId());
        task.setName(name);
        task.setDescription(description);
        @NotNull final HttpEntity<TaskDTO> entityProject = new HttpEntity<>(task, headers);
        @NotNull final String createUrl = property.getServer() + property.getTaskMapping() + property.getRequestCreate();
        @NotNull final ResponseEntity<TaskDTO> response = restTemplate.postForEntity(createUrl, entityProject, TaskDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdate() throws Exception {
        @NotNull final String name = "update";
        @NotNull final String description = "update";
        task.setName(name);
        task.setDescription(description);
        @NotNull final HttpEntity<TaskDTO> entityProject = new HttpEntity<>(task, headers);
        @NotNull final String updateUrl = property.getServer() + property.getTaskMapping() + property.getRequestUpdate();
        @NotNull final String url = updateUrl + user.getId() + "/" + task.getId();
        @NotNull final ResponseEntity<TaskDTO> response = restTemplate.postForEntity(url, entityProject, TaskDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDelete() throws Exception {
        @NotNull String deleteUrl = property.getServer() + property.getTaskMapping() + property.getRequestDelete();
        @NotNull String url = deleteUrl + user.getId() + "/" + task.getId();
        restTemplate.delete(url);
    }

    @Test
    public void testView() throws Exception {
        @NotNull final String viewUrl = property.getServer() + property.getTaskMapping() + property.getRequestView();
        @NotNull String url = viewUrl + user.getId() + "/" + task.getId();
        @NotNull final ResponseEntity<TaskDTO> response = restTemplate.getForEntity(url, TaskDTO.class);
        @Nullable final TaskDTO temp = response.getBody();
        assertNotNull(temp);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}