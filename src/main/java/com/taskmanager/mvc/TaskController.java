package com.taskmanager.mvc;

import com.taskmanager.api.service.IProjectService;
import com.taskmanager.api.service.ITaskService;
import com.taskmanager.api.service.IUserService;
import com.taskmanager.enumerate.RoleType;
import com.taskmanager.enumerate.StatusType;
import com.taskmanager.exception.EmptyInputException;
import com.taskmanager.exception.ProjectNotFoundException;
import com.taskmanager.exception.TaskNotFoundException;
import com.taskmanager.exception.UserNotFoundException;
import com.taskmanager.model.Project;
import com.taskmanager.model.Task;
import com.taskmanager.model.User;
import com.taskmanager.util.DateFormatterUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@SessionAttributes({"currentUser"})
@Controller
public class TaskController {

    @NotNull
    private final IUserService userService;

    @NotNull
    private final IProjectService projectService;

    @NotNull
    private final ITaskService taskService;

    @Autowired
    public TaskController(
            @NotNull final IUserService userService,
            @NotNull final IProjectService projectService,
            @NotNull final ITaskService taskService
    ) {
        this.userService = userService;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/task-list-all")
    public String findAll(@NotNull final Model model) throws Exception {
        @NotNull final List<Task> tasks = taskService.findAll();
        model.addAttribute("tasks", tasks);
        return "task-list-all";
    }

    @GetMapping("/task-list")
    public String findAllByUserId(
            @NotNull final HttpSession session,
            @NotNull final Model model
    ) throws Exception {
        @Nullable final User user = userService.findOneByLogin((String) session.getAttribute("login"));
        if (user == null) throw new UserNotFoundException();
        @NotNull final String userId = user.getId();
        @NotNull final List<Task> tasks = taskService.findAllByUserId(userId);
        model.addAttribute("tasks", tasks);
        return "task-list";
    }

    @GetMapping("/task-create")
    public String createTask(
            @NotNull final HttpSession session,
            @NotNull final Model model
    ) throws Exception {
        @Nullable final User user = userService.findOneByLogin((String) session.getAttribute("login"));
        if (user == null) throw new UserNotFoundException();
        @NotNull final String userId = user.getId();
        @NotNull final List<Project> projects = projectService.findAllByUserId(userId);
        model.addAttribute("projects", projects);
        return "task-create";
    }

    @PostMapping("/task-create")
    public String createTask(
            @NotNull final HttpSession session,
            @ModelAttribute("projectId") @Nullable final String projectId,
            @ModelAttribute("name") @Nullable final String name,
            @ModelAttribute("description") @Nullable final String description,
            @ModelAttribute("statusType") @NotNull final String statusType,
            @ModelAttribute("startDate") @Nullable final String startDate,
            @ModelAttribute("finishDate") @Nullable final String finishDate,
            @NotNull final Model model
    ) throws Exception, TaskNotFoundException {
        @Nullable final User user = userService.findOneByLogin((String) session.getAttribute("login"));
        if (user == null) throw new UserNotFoundException();
        @NotNull final String userId = user.getId();
        if (name == null) throw new EmptyInputException("Task name");
        @NotNull final Task task = new Task(name, description, StatusType.valueOf(statusType));
        task.setUser(user);
        task.setProject(projectService.findOneById(userId, projectId));
        task.setStartDate(DateFormatterUtil.parseDate(startDate));
        task.setFinishDate(DateFormatterUtil.parseDate(finishDate));
        taskService.persist(task);
        model.addAttribute("message", "The task is successfully created.");
        return "redirect:/task-list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/task-delete/{userId}/{id}")
    public String deleteTask(
            @PathVariable("userId") @NotNull final String userId,
            @PathVariable("id") @NotNull final String id
    ) throws Exception {
        taskService.remove(userId, id);
        return "redirect:/task-list";
    }

    @GetMapping("/task-delete/{id}")
    public String deleteTask(
            @NotNull final HttpSession session,
            @PathVariable("id") @NotNull final String id
    ) throws Exception {
        @Nullable final User user = userService.findOneByLogin((String) session.getAttribute("login"));
        if (user == null) throw new UserNotFoundException();
        @NotNull final String userId = user.getId();
        taskService.remove(userId, id);
        return "redirect:/task-list";
    }

    @GetMapping("/task-update/{userId}/{id}")
    public String updateTaskForm(
            @NotNull final HttpSession session,
            @PathVariable("userId") @NotNull final String userId,
            @PathVariable("id") @NotNull final String id,
            @NotNull final Model model
    ) throws Exception {
        @Nullable final Task task = taskService.findOneById(userId, id);
        model.addAttribute("task", task);
        return "task-update";
    }

    @PostMapping("/task-update")
    public String updateTask(
            @NotNull final HttpSession session,
            @ModelAttribute("userId") @Nullable final String userId,
            @ModelAttribute("projectId") @Nullable final String projectId,
            @ModelAttribute("id") @Nullable final String id,
            @ModelAttribute("name") @Nullable final String name,
            @ModelAttribute("description") @Nullable final String description,
            @ModelAttribute("statusType") @NotNull final String statusType,
            @ModelAttribute("startDate") @Nullable final String startDate,
            @ModelAttribute("finishDate") @Nullable final String finishDate
    ) throws Exception, UserNotFoundException, ProjectNotFoundException {
        @Nullable final User user = userService.findOneByLogin((String) session.getAttribute("login"));
        if (user == null) throw new UserNotFoundException();
        @Nullable Task task = null;
        if (!user.getId().equals(userId)
                || user.getRoleType().equals(RoleType.ADMIN))
            task = taskService.findOneById(userId, id);
        else if (user.getId().equals(userId))
            task = taskService.findOneById(userId, id);
        if (task == null) throw new TaskNotFoundException();
        if (projectId == null || projectId.isEmpty()) throw new ProjectNotFoundException();
        task.setProject(projectService.findOneById(userId, projectId));
        if (name == null) throw new EmptyInputException("task name");
        task.setName(name);
        task.setDescription(description);
        task.setStatusType(StatusType.valueOf(statusType));
        task.setStartDate(DateFormatterUtil.parseDate(startDate));
        task.setFinishDate(DateFormatterUtil.parseDate(finishDate));
        taskService.merge(task);
        return "redirect:/task-list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/task-view/{userId}/{id}")
    public String viewProjectForm(
            @PathVariable("userId") @NotNull final String userId,
            @PathVariable("id") @NotNull final String id,
            @NotNull final Model model
    ) throws Exception {
        if (id.isEmpty()) throw new TaskNotFoundException();
        if (userId.isEmpty()) throw new UserNotFoundException();
        @Nullable final Task task = taskService.findOneById(userId, id);
        if (task == null) throw new TaskNotFoundException();
        model.addAttribute("task", task);
        return "task-view";
    }

    @GetMapping("/task-view/{id}")
    public String viewProjectForm(
            @NotNull final HttpSession session,
            @PathVariable("id") @NotNull final String id,
            @NotNull final Model model
    ) throws Exception {
        @Nullable final User user = userService.findOneByLogin((String) session.getAttribute("login"));
        if (user == null) throw new UserNotFoundException();
        @NotNull final String userId = user.getId();
        if (id.isEmpty()) throw new TaskNotFoundException();
        @Nullable final Task task = taskService.findOneById(userId, id);
        if (task == null) throw new TaskNotFoundException();
        model.addAttribute("task", task);
        return "task-view";
    }

    @Secured("ROLE_ADMIN")
    @Nullable
    @GetMapping("/task-search-all")
    public String findAllBySearch(
            @RequestParam("search") @NotNull final String search,
            @NotNull final Model model
    ) throws Exception {
        @NotNull final List<Task> taskSearch = taskService.findAllBySearch(search);
        if (taskSearch.isEmpty()) return null;
        model.addAttribute("taskSearch", taskSearch);
        return "task-search";
    }

    @Nullable
    @GetMapping("/task-search")
    public String findAllBySearch(
            @NotNull final HttpSession session,
            @RequestParam("search") @NotNull final String search,
            @NotNull final Model model
    ) throws Exception {
        @Nullable final User user = userService.findOneByLogin((String) session.getAttribute("login"));
        if (user == null) throw new UserNotFoundException();
        @NotNull final String userId = user.getId();
        @NotNull final List<Task> taskSearch = taskService.findAllBySearch(userId, search);
        if (taskSearch.isEmpty()) return null;
        model.addAttribute("taskSearch", taskSearch);
        return "task-search";
    }

    @GetMapping("/task-list-start-date-asc")
    public String findAllByStartDateAsc(
            @NotNull final HttpSession session,
            @NotNull final Model model
    ) throws Exception {
        @Nullable final User user = userService.findOneByLogin((String) session.getAttribute("login"));
        if (user == null) throw new UserNotFoundException();
        @NotNull final String userId = user.getId();
        @NotNull final List<Task> tasks = taskService.findAllSortedByStartDateAsc(userId);
        model.addAttribute("tasks", tasks);
        return "task-list";
    }

    @GetMapping("/task-list-start-date-desc")
    public String findAllByStartDateDesc(
            @NotNull final HttpSession session,
            @NotNull final Model model
    ) throws Exception {
        @Nullable final User user = userService.findOneByLogin((String) session.getAttribute("login"));
        if (user == null) throw new UserNotFoundException();
        @NotNull final String userId = user.getId();
        @NotNull final List<Task> tasks = taskService.findAllSortedByStartDateDesc(userId);
        model.addAttribute("tasks", tasks);
        return "task-list";
    }

    @GetMapping("/task-list-finish-date-asc")
    public String findAllByFinishDateAsc(
            @NotNull final HttpSession session,
            @NotNull final Model model
    ) throws Exception {
        @Nullable final User user = userService.findOneByLogin((String) session.getAttribute("login"));
        if (user == null) throw new UserNotFoundException();
        @NotNull final String userId = user.getId();
        @NotNull final List<Task> tasks = taskService.findAllSortedByFinishDateAsc(userId);
        model.addAttribute("tasks", tasks);
        return "task-list";
    }

    @GetMapping("/task-list-finish-date-desc")
    public String findAllByFinishDateDesc(
            @NotNull final HttpSession session,
            @NotNull final Model model
    ) throws Exception {
        @Nullable final User user = userService.findOneByLogin((String) session.getAttribute("login"));
        if (user == null) throw new UserNotFoundException();
        @NotNull final String userId = user.getId();
        @NotNull final List<Task> tasks = taskService.findAllSortedByFinishDateDesc(userId);
        model.addAttribute("tasks", tasks);
        return "task-list";
    }

    @GetMapping("/task-list-status-asc")
    public String findAllByStatusAsc(
            @NotNull final HttpSession session,
            @NotNull final Model model
    ) throws Exception {
        @Nullable final User user = userService.findOneByLogin((String) session.getAttribute("login"));
        if (user == null) throw new UserNotFoundException();
        @NotNull final String userId = user.getId();
        @NotNull final List<Task> tasks = taskService.findAllSortedByStatusAsc(userId);
        model.addAttribute("tasks", tasks);
        return "task-list";
    }

    @GetMapping("/task-list-status-desc")
    public String findAllByStatusDesc(
            @NotNull final HttpSession session,
            @NotNull final Model model
    ) throws Exception {
        @Nullable final User user = userService.findOneByLogin((String) session.getAttribute("login"));
        if (user == null) throw new UserNotFoundException();
        @NotNull final String userId = user.getId();
        @NotNull final List<Task> tasks = taskService.findAllSortedByStatusDesc(userId);
        model.addAttribute("tasks", tasks);
        return "task-list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/task-list-start-date-asc-all")
    public String findAllByStartDateAsc(@NotNull final Model model) throws Exception {
        @NotNull final List<Task> tasks = taskService.findAllSortedByStartDateAsc();
        model.addAttribute("tasks", tasks);
        return "task-list-all";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/task-list-start-date-desc-all")
    public String findAllByStartDateDesc(@NotNull final Model model) throws Exception {
        @NotNull final List<Task> tasks = taskService.findAllSortedByStartDateDesc();
        model.addAttribute("tasks", tasks);
        return "task-list-all";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/task-list-finish-date-asc-all")
    public String findAllByFinishDateAsc(@NotNull final Model model) throws Exception {
        @NotNull final List<Task> tasks = taskService.findAllSortedByFinishDateAsc();
        model.addAttribute("tasks", tasks);
        return "task-list-all";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/task-list-finish-date-desc-all")
    public String findAllByFinishDateDesc(@NotNull final Model model) throws Exception {
        @NotNull final List<Task> tasks = taskService.findAllSortedByFinishDateDesc();
        model.addAttribute("tasks", tasks);
        return "task-list-all";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/task-list-status-asc-all")
    public String findAllByStatusAsc(@NotNull final Model model) throws Exception {
        @NotNull final List<Task> tasks = taskService.findAllSortedByStatusAsc();
        model.addAttribute("tasks", tasks);
        return "task-list-all";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/task-list-status-desc-all")
    public String findAllByStatusDesc(@NotNull final Model model) throws Exception {
        @NotNull final List<Task> tasks = taskService.findAllSortedByStatusDesc();
        model.addAttribute("tasks", tasks);
        return "task-list-all";
    }
}
