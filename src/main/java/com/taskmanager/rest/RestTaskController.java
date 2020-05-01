package com.taskmanager.rest;

import com.taskmanager.api.service.ITaskService;
import com.taskmanager.exception.EmptyInputException;
import com.taskmanager.exception.ProjectNotFoundException;
import com.taskmanager.exception.UserNotFoundException;
import com.taskmanager.model.Task;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.taskmanager.dto.TaskDTO;

import java.util.List;

@RestController
@RequestMapping(value = "/task", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestTaskController {

    @NotNull
    private final ITaskService taskService;

    @Autowired
    public RestTaskController(@NotNull final ITaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/list")
    @ResponseBody
    public List<TaskDTO> findAll() throws Exception {
        return taskService.getListTaskDTO(taskService.findAll());
    }

    @GetMapping(value = "/list/start-date-asc", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskDTO> findAllByStartDateAsc() throws Exception {
        return taskService.getListTaskDTO(taskService.findAllSortedByStartDateAsc());
    }

    @GetMapping(value = "/list/start-date-desc", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskDTO> findAllByStartDateDesc() throws Exception {
        return taskService.getListTaskDTO(taskService.findAllSortedByStartDateDesc());
    }

    @GetMapping(value = "/list/finish-date-asc", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskDTO> findAllByFinishDateAsc() throws Exception {
        return taskService.getListTaskDTO(taskService.findAllSortedByFinishDateAsc());
    }

    @GetMapping(value = "/list/finish-date-desc", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskDTO> findAllByFinishDateDesc() throws Exception {
        return taskService.getListTaskDTO(taskService.findAllSortedByFinishDateDesc());
    }

    @GetMapping(value = "/list/status-asc", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskDTO> findAllByStatusAsc() throws Exception {
        return taskService.getListTaskDTO(taskService.findAllSortedByStatusAsc());
    }

    @GetMapping(value = "/list/status-desc", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskDTO> findAllByStatusDesc() throws Exception {
        return taskService.getListTaskDTO(taskService.findAllSortedByStatusDesc());
    }

    @GetMapping("/search")
    @ResponseBody
    public List<TaskDTO> findAllBySearch(
            @RequestParam(value = "search") @NotNull final String search
    ) throws Exception {
        return taskService.getListTaskDTO(taskService.findAllBySearch(search));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(
            @RequestBody @NotNull final TaskDTO task
    ) throws Exception, UserNotFoundException, ProjectNotFoundException {
        if (task.getUserId().isEmpty()) ResponseEntity.badRequest().body("User id is empty.");
        taskService.persist(taskService.getTaskEntity(task));
    }

    @DeleteMapping("/delete/{user_id}/{id}")
    public ResponseEntity<Object> delete(
            @PathVariable("user_id") @NotNull final String user_id,
            @PathVariable("id") @NotNull final String id
    ) throws Exception {
        if (id.isEmpty()) return ResponseEntity.badRequest().body("Project id is empty.");
        if (user_id.isEmpty()) return ResponseEntity.badRequest().body("User id is empty.");
        taskService.remove(user_id, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/update/{user_id}/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskDTO> update(
            @RequestBody @Nullable final TaskDTO task,
            @PathVariable("user_id") @NotNull final String user_id,
            @PathVariable("id") @NotNull final String id
    ) throws Exception {
        if (id.isEmpty()) return ResponseEntity.badRequest().build();
        if (user_id.isEmpty()) return ResponseEntity.badRequest().build();
        taskService.merge(taskService.getTaskEntity(task));
        @Nullable final Task updatedTask = taskService.findOneById(user_id, id);
        if (updatedTask == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(taskService.getTaskDTO(updatedTask));
        }
    }

    @GetMapping("/view/{user_id}/{id}")
    public ResponseEntity<TaskDTO> view(
            @PathVariable(name = "user_id") @NotNull final String user_id,
            @PathVariable(name = "id") @NotNull final String id
    ) throws Exception, UserNotFoundException, EmptyInputException {
        if (id.isEmpty()) return ResponseEntity.badRequest().build();
        if (user_id.isEmpty()) return ResponseEntity.badRequest().build();
        @Nullable final Task task = taskService.findOneById(user_id, id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(taskService.getTaskDTO(task));
        }
    }
}
