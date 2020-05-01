package com.taskmanager.rest;

import com.taskmanager.api.service.IProjectService;
import com.taskmanager.exception.EmptyInputException;
import com.taskmanager.exception.ProjectNotFoundException;
import com.taskmanager.exception.UserNotFoundException;
import com.taskmanager.model.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.taskmanager.dto.ProjectDTO;

import java.util.List;

@RestController
@RequestMapping(value = "/project", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestProjectController {

    @NotNull
    private final IProjectService projectService;

    @Autowired
    public RestProjectController(
            @NotNull final IProjectService projectService
    ) throws Exception, EmptyInputException, ProjectNotFoundException {
        this.projectService = projectService;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProjectDTO> findAll() throws Exception {
        return projectService.getListProjectDTO(projectService.findAll());
    }

    @GetMapping(value = "/list/start-date-asc", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProjectDTO> findAllByStartDateAsc() throws Exception {
        return projectService.getListProjectDTO(projectService.findAllSortedByStartDateAsc());
    }

    @GetMapping(value = "/list/start-date-desc", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProjectDTO> findAllByStartDateDesc() throws Exception {
        return projectService.getListProjectDTO(projectService.findAllSortedByStartDateDesc());
    }

    @GetMapping(value = "/list/finish-date-asc", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProjectDTO> findAllByFinishDateAsc() throws Exception {
        return projectService.getListProjectDTO(projectService.findAllSortedByFinishDateAsc());
    }

    @GetMapping(value = "/list/finish-date-desc", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProjectDTO> findAllByFinishDateDesc() throws Exception {
        return projectService.getListProjectDTO(projectService.findAllSortedByFinishDateDesc());
    }

    @GetMapping(value = "/list/status-asc", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProjectDTO> findAllByStatusAsc() throws Exception {
        return projectService.getListProjectDTO(projectService.findAllSortedByStatusAsc());
    }

    @GetMapping(value = "/list/status-desc", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProjectDTO> findAllByStatusDesc() throws Exception {
        return projectService.getListProjectDTO(projectService.findAllSortedByStatusDesc());
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProjectDTO> findAllBySearch(
            @RequestParam(value = "search") @NotNull final String search
    ) throws Exception {
        return projectService.getListProjectDTO(projectService.findAllBySearch(search));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(
            @RequestBody @NotNull final ProjectDTO project
    ) throws Exception, UserNotFoundException, ProjectNotFoundException {
        if (project.getUserId().isEmpty()) return ResponseEntity.badRequest().body("User id is empty.");
        projectService.persist(projectService.getProjectEntity(project));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{user_id}/{id}")
    public ResponseEntity<Object> delete(
            @PathVariable("user_id") @NotNull final String user_id,
            @PathVariable("id") @NotNull final String id
    ) throws Exception {
        if (id.isEmpty()) return ResponseEntity.badRequest().body("Project id is empty.");
        if (user_id.isEmpty()) return ResponseEntity.badRequest().body("User id is empty.");
        projectService.remove(user_id, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/update/{user_id}/{id}")
    public ResponseEntity<ProjectDTO> update(
            @RequestBody @Nullable final ProjectDTO project,
            @PathVariable("user_id") @NotNull final String user_id,
            @PathVariable("id") @NotNull final String id
    ) throws Exception {
        if (id.isEmpty()) return ResponseEntity.badRequest().build();
        if (user_id.isEmpty()) return ResponseEntity.badRequest().build();
        projectService.merge(projectService.getProjectEntity(project));
        @Nullable final Project updatedProject = projectService.findOneById(user_id, id);
        if (updatedProject == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(projectService.getProjectDTO(updatedProject));
        }
    }

    @GetMapping("/view/{user_id}/{id}")
    public ResponseEntity<ProjectDTO> view(
            @PathVariable(name = "user_id") @NotNull final String user_id,
            @PathVariable(name = "id") @NotNull final String id
    ) throws Exception, UserNotFoundException, EmptyInputException {
        if (id.isEmpty()) return ResponseEntity.badRequest().build();
        if (user_id.isEmpty()) return ResponseEntity.badRequest().build();
        @Nullable final Project project = projectService.findOneById(user_id, id);
        if (project == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(projectService.getProjectDTO(project));
        }
    }
}
