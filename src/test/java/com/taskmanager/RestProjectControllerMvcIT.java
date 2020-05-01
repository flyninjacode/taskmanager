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
import com.taskmanager.dto.ProjectDTO;
import com.taskmanager.dto.UserDTO;
import com.taskmanager.enumerate.RoleType;
import com.taskmanager.service.PropertyService;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = PropertyService.class)
@TestPropertySource
public class RestProjectControllerMvcIT {

    @Autowired
    private PropertyService property;

    @Nullable
    private UserDTO user;

    @Nullable
    private ProjectDTO project;

    @NotNull
    private final RestTemplate restTemplate = new RestTemplate();

    @NotNull
    private final HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    public void setUp() throws Exception {
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        @NotNull final String login = "testProjectUser";
        @NotNull final String pass = "testProjectUser";
        user = new UserDTO(login, pass, RoleType.valueOf("ADMIN"));
        @NotNull final HttpEntity<UserDTO> entityUser = new HttpEntity<>(user, headers);
        @NotNull String createUrl = property.getServer() + property.getUserMapping() + property.getRequestCreate();
        restTemplate.postForEntity(createUrl, entityUser, UserDTO.class);
        @NotNull final String name = "project";
        @NotNull final String description = "description";
        project = new ProjectDTO();
        project.setUserId(user.getId());
        project.setName(name);
        project.setDescription(description);
        @NotNull final HttpEntity<ProjectDTO> entityProject = new HttpEntity<>(project, headers);
        createUrl = property.getServer() + property.getProjectMapping() + property.getRequestCreate();
        restTemplate.postForEntity(createUrl, entityProject, ProjectDTO.class);
    }

    @AfterEach
    public void tearDown() {
        @NotNull String deleteUrl = property.getServer() + property.getUserMapping() + property.getRequestDelete();
        restTemplate.delete(deleteUrl + user.getId());
    }

    @Test
    public void testFindAll() {
        @NotNull final String findAllUrl = property.getServer() + property.getProjectMapping() + property.getRequestList();
        @NotNull final ResponseEntity<ProjectDTO[]> response = restTemplate.getForEntity(findAllUrl, ProjectDTO[].class);
        @Nullable final ProjectDTO[] projects = response.getBody();
        assertNotNull(projects);
        assertEquals(3, projects.length);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testFindAllByStatusAsc() {
        @NotNull final String findAllUrl = property.getServer() + property.getProjectMapping() + property.getRequestListStatusAsc();
        @NotNull final ResponseEntity<ProjectDTO[]> response = restTemplate.getForEntity(findAllUrl, ProjectDTO[].class);
        @Nullable final ProjectDTO[] projects = response.getBody();
        @NotNull int prevStatus = 0;
        for (@Nullable final ProjectDTO projectDTO : projects) {
            @NotNull int currStatus = projectDTO.getStatusType().ordinal();
            assertTrue(prevStatus <= currStatus);
            prevStatus = currStatus;
        }
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testFindAllByStatusDesc() {
        @NotNull final String findAllUrl = property.getServer() + property.getProjectMapping() + property.getRequestListStatusDesc();
        @NotNull final ResponseEntity<ProjectDTO[]> response = restTemplate.getForEntity(findAllUrl, ProjectDTO[].class);
        @Nullable final ProjectDTO[] projects = response.getBody();
        @NotNull int prevStatus = 2;
        for (@Nullable final ProjectDTO projectDTO : projects) {
            @NotNull int currStatus = projectDTO.getStatusType().ordinal();
            assertTrue(prevStatus >= currStatus);
            prevStatus = currStatus;
        }
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreate() throws Exception {
        @NotNull final String name = "create";
        @NotNull final String description = "create";
        project = new ProjectDTO();
        project.setUserId(user.getId());
        project.setName(name);
        project.setDescription(description);
        @NotNull final HttpEntity<ProjectDTO> entityProject = new HttpEntity<>(project, headers);
        @NotNull final String createUrl = property.getServer() + property.getProjectMapping() + property.getRequestCreate();
        @NotNull final ResponseEntity<ProjectDTO> response = restTemplate.postForEntity(createUrl, entityProject, ProjectDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdate() throws Exception {
        @NotNull final String name = "update";
        @NotNull final String description = "update";
        project.setName(name);
        project.setDescription(description);
        @NotNull final HttpEntity<ProjectDTO> entityProject = new HttpEntity<>(project, headers);
        @NotNull final String updateUrl = property.getServer() + property.getProjectMapping() + property.getRequestUpdate();
        @NotNull final String url = updateUrl + user.getId() + "/" + project.getId();
        @NotNull final ResponseEntity<ProjectDTO> response = restTemplate.postForEntity(url, entityProject, ProjectDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDelete() throws Exception {
        @NotNull String deleteUrl = property.getServer() + property.getProjectMapping() + property.getRequestDelete();
        @NotNull String url = deleteUrl + user.getId() + "/" + project.getId();
        restTemplate.delete(url);
    }

    @Test
    public void testView() throws Exception {
        @NotNull final String viewUrl = property.getServer() + property.getProjectMapping() + property.getRequestView();
        @NotNull String url = viewUrl + user.getId() + "/" + project.getId();
        @NotNull final ResponseEntity<ProjectDTO> response = restTemplate.getForEntity(url, ProjectDTO.class);
        @Nullable final ProjectDTO temp = response.getBody();
        assertNotNull(temp);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}