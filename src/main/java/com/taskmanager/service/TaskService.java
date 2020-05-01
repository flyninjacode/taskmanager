package com.taskmanager.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taskmanager.api.service.IProjectService;
import com.taskmanager.api.service.ITaskService;
import com.taskmanager.api.service.IUserService;
import com.taskmanager.api.repository.ITaskRepository;
import com.taskmanager.dto.TaskDTO;
import com.taskmanager.exception.EmptyInputException;
import com.taskmanager.exception.TaskNotFoundException;
import com.taskmanager.exception.UserNotFoundException;
import com.taskmanager.model.Task;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TaskService extends AbstractProjectTaskService<Task> implements ITaskService {

    @Autowired
    @NotNull private IUserService userService;

    @Autowired
    @NotNull private IProjectService projectService;

    @Autowired
    @NotNull private ITaskRepository taskRepository;

    public TaskService() {
    }

    @Override
    @NotNull
    public Task getTaskEntity(@Nullable final TaskDTO taskDTO) throws Exception {
        if (taskDTO == null) throw new Exception("[The task does not exist.]");
        @NotNull final Task task = new Task();
        task.setId(taskDTO.getId());
        task.setUser(userService.findOneById(taskDTO.getUserId()));
        if (taskDTO.getProjectId() != null)
            task.setProject(projectService.findOneById(taskDTO.getUserId(), taskDTO.getProjectId()));
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setCreateDate(taskDTO.getCreateDate());
        task.setStartDate(taskDTO.getStartDate());
        task.setFinishDate(taskDTO.getFinishDate());
        task.setStatusType(taskDTO.getStatusType());
        return task;
    }

    @Override
    @NotNull
    public TaskDTO getTaskDTO(@Nullable final Task task) throws Exception {
        if (task == null) throw new Exception("[The task does not exist.]");
        @NotNull final TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setUserId(task.getUser().getId());
        if (task.getProject() != null)
            taskDTO.setProjectId(task.getProject().getId());
        taskDTO.setName(task.getName());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setCreateDate(task.getCreateDate());
        taskDTO.setStartDate(task.getStartDate());
        taskDTO.setFinishDate(task.getFinishDate());
        taskDTO.setStatusType(task.getStatusType());
        return taskDTO;
    }

    @Override
    @NotNull
    public List<TaskDTO> getListTaskDTO(@Nullable final List<Task> tasks) throws Exception {
        if (tasks == null) throw new Exception("[There're no tasks yet.]");
        @NotNull final List<TaskDTO> listTaskDTO = new ArrayList<>();
        for (@NotNull final Task task : tasks)
            listTaskDTO.add(getTaskDTO(task));
        return listTaskDTO;
    }

    @NotNull
    @Override
    public List<Task> findAll() throws Exception {
        return taskRepository.findAll();
    }

    @NotNull
    @Override
    public List<Task> findAllByUserId(@Nullable final String userId)
            throws Exception, UserNotFoundException {
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        return taskRepository.findAllByUserId(userId);
    }

    @Override
    @NotNull
    public List<Task> findTasksWithProjectId(@Nullable final String userId) throws UserNotFoundException {
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        return taskRepository.findTasksWithProjectId(userId);
    }

    @Nullable
    @Override
    public Task findOneById(@Nullable final String userId, @Nullable final String id)
            throws Exception, UserNotFoundException, EmptyInputException {
        if (id == null || id.isEmpty()) throw new EmptyInputException("task");
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        return taskRepository.findOneById(userId, id);
    }

    @NotNull
    @Override
    public List<Task> findOneByName(@Nullable final String userId, @Nullable final String name)
            throws Exception, UserNotFoundException, EmptyInputException {
        if (name == null || name.isEmpty()) throw new EmptyInputException("task");
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        return taskRepository.findOneByName(userId, name);
    }

    @Override
    public void persist(@Nullable final Task task) throws TaskNotFoundException {
        if (task == null) throw new TaskNotFoundException();
        taskRepository.save(task);
    }

    @Override
    public void merge(@Nullable final Task task) throws TaskNotFoundException {
        if (task == null) throw new TaskNotFoundException();
        taskRepository.save(task);
    }

    @Override
    public void remove(@Nullable final String userId, @Nullable final String id)
            throws Exception, UserNotFoundException, EmptyInputException {
        if (id == null || id.isEmpty()) throw new EmptyInputException("task");
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        @Nullable final Task task = findOneById(userId, id);
        if (task == null) throw new TaskNotFoundException();
        taskRepository.delete(task);
    }

    @Override
    public void removeAllByUserId(@Nullable final String userId)
            throws Exception, UserNotFoundException {
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        for (@NotNull final Task task : findAllByUserId(userId))
            taskRepository.delete(task);
    }

    @Override
    public void removeAll() {
        taskRepository.deleteAll();
    }

    @Override
    @NotNull
    public List<Task> findAllSortedByCreateDate(@Nullable final String userId)
            throws Exception, UserNotFoundException {
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        return taskRepository.findAllSortedByCreateDate(userId);
    }

    @Override
    @NotNull
    public List<Task> findAllSortedByStartDateAsc(@Nullable final String userId)
            throws Exception, UserNotFoundException {
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        return taskRepository.findAllSortedByStartDateAsc(userId);
    }

    @Override
    @NotNull
    public List<Task> findAllSortedByStartDateDesc(@Nullable final String userId)
            throws Exception, UserNotFoundException {
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        return taskRepository.findAllSortedByStartDateDesc(userId);
    }

    @Override
    @NotNull
    public List<Task> findAllSortedByStartDateAsc()
            throws Exception, UserNotFoundException {
        return taskRepository.findAllSortedByStartDateAsc();
    }

    @Override
    @NotNull
    public List<Task> findAllSortedByStartDateDesc()
            throws Exception, UserNotFoundException {
        return taskRepository.findAllSortedByStartDateDesc();
    }

    @Override
    @NotNull
    public List<Task> findAllSortedByFinishDateAsc(@Nullable final String userId)
            throws Exception, UserNotFoundException {
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        return taskRepository.findAllSortedByFinishDateAsc(userId);
    }

    @Override
    @NotNull
    public List<Task> findAllSortedByFinishDateDesc(@Nullable final String userId)
            throws Exception, UserNotFoundException {
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        return taskRepository.findAllSortedByFinishDateDesc(userId);
    }

    @Override
    @NotNull
    public List<Task> findAllSortedByFinishDateAsc()
            throws Exception, UserNotFoundException {
        return taskRepository.findAllSortedByFinishDateAsc();
    }

    @Override
    @NotNull
    public List<Task> findAllSortedByFinishDateDesc()
            throws Exception, UserNotFoundException {
        return taskRepository.findAllSortedByFinishDateDesc();
    }

    @Override
    @NotNull
    public List<Task> findAllSortedByStatusAsc(@Nullable final String userId)
            throws Exception, UserNotFoundException {
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        return taskRepository.findAllSortedByStatusAsc(userId);
    }

    @Override
    public List<Task> findAllSortedByStatusDesc(@Nullable final String userId)
            throws Exception, UserNotFoundException {
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        return taskRepository.findAllSortedByStatusDesc(userId);
    }

    @Override
    @NotNull
    public List<Task> findAllSortedByStatusAsc()
            throws Exception, UserNotFoundException {
        return taskRepository.findAllSortedByStatusAsc();
    }

    @Override
    @NotNull
    public List<Task> findAllSortedByStatusDesc()
            throws Exception, UserNotFoundException {
        return taskRepository.findAllSortedByStatusDesc();
    }

    @NotNull
    @Override
    public List<Task> findAllBySearch(@Nullable final String userId, @Nullable final String search) throws Exception {
        if (search == null || search.isEmpty()) throw new Exception("[The search query is empty.]");
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        return taskRepository.findAllBySearch(userId, '%' + search + '%');
    }

    @Override
    @NotNull
    public List<Task> findAllBySearch(@Nullable final String search) throws Exception {
        if (search == null || search.isEmpty()) throw new Exception("[The search query is empty.]");
        return taskRepository.findAllBySearch('%' + search + '%');
    }

    @Override
    public void removeTasksWithProjectId(@Nullable final String userId)
            throws Exception, UserNotFoundException {
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        for (@NotNull final Task task : findTasksWithProjectId(userId))
            taskRepository.delete(task);
    }

    @Override
    public void removeProjectTasks(@Nullable final String userId, @Nullable final String projectId)
            throws Exception, UserNotFoundException, EmptyInputException {
        if (projectId == null || projectId.isEmpty()) throw new EmptyInputException("project");
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        for (@NotNull final Task task : findTasksByProjectId(userId, projectId))
            taskRepository.delete(task);
    }

    @Override
    @NotNull
    public List<Task> findTasksByProjectId(@Nullable final String userId, @Nullable final String projectId)
            throws Exception, UserNotFoundException, EmptyInputException {
        if (projectId == null || projectId.isEmpty()) throw new EmptyInputException("project");
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        return taskRepository.findTasksByProjectId(userId, projectId);
    }

    @Override
    @NotNull
    public List<Task> findTasksWithoutProject(@Nullable final String userId)
            throws Exception, UserNotFoundException {
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        return taskRepository.findTasksWithoutProject(userId);
    }
}
