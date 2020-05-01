package com.taskmanager.mvc;

import com.taskmanager.api.service.IProjectService;
import com.taskmanager.api.service.IUserService;
import com.taskmanager.enumerate.RoleType;
import com.taskmanager.enumerate.StatusType;
import com.taskmanager.exception.EmptyInputException;
import com.taskmanager.exception.ProjectNotFoundException;
import com.taskmanager.exception.UserNotFoundException;
import com.taskmanager.model.Project;
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
public class ProjectController {

    @NotNull
    private final IUserService userService;

    @NotNull
    private final IProjectService projectService;

    @Autowired
    public ProjectController(
            @NotNull final IUserService userService,
            @NotNull final IProjectService projectService
    ) {
        this.userService = userService;
        this.projectService = projectService;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/project-list-all")
    public String findAll(@NotNull final Model model) throws Exception {
        @NotNull final List<Project> projects = projectService.findAll();
        model.addAttribute("projects", projects);
        return "project-list-all";
    }

    @GetMapping("/project-list")
    public String findAllByUserId(
            @NotNull final Model model,
            @NotNull final HttpSession session
    ) throws Exception {
        @Nullable final User user = userService.findOneByLogin((String) session.getAttribute("login"));
        if (user == null) throw new UserNotFoundException();
        @NotNull final String userId = user.getId();
        @NotNull final List<Project> projects = projectService.findAllByUserId(userId);
        model.addAttribute("projects", projects);
        return "project-list";
    }

    @GetMapping("/project-create")
    public String createProjectForm(@NotNull final Model model) throws Exception {
        return "project-create";
    }

    @PostMapping("/project-create")
    public String createProject(
            @NotNull final HttpSession session,
            @ModelAttribute("name") @Nullable final String name,
            @ModelAttribute("description") @Nullable final String description,
            @ModelAttribute("statusType") @Nullable final String statusType,
            @ModelAttribute("startDate") @Nullable final String startDate,
            @ModelAttribute("finishDate") @Nullable final String finishDate,
            @NotNull final Model model
    ) throws Exception, UserNotFoundException, ProjectNotFoundException {
        if (name == null) throw new EmptyInputException("project name");
        @NotNull final Project project = new Project(name, description, StatusType.valueOf(statusType));
        @Nullable final User user = userService.findOneByLogin((String) session.getAttribute("login"));
        if (user == null) throw new UserNotFoundException();
        project.setUser(user);
        project.setStartDate(DateFormatterUtil.parseDate(startDate));
        project.setFinishDate(DateFormatterUtil.parseDate(finishDate));
        projectService.persist(project);
        model.addAttribute("message", "The project is successfully created.");
        return "redirect:/project-list";
    }

    @GetMapping("/project-delete/{id}")
    public String deleteProject(
            @NotNull final HttpSession session,
            @PathVariable("id") @NotNull final String id
    ) throws Exception {
        @Nullable final User user = userService.findOneByLogin((String) session.getAttribute("login"));
        if (user == null) throw new UserNotFoundException();
        @NotNull final String userId = user.getId();
        projectService.remove(userId, id);
        return "redirect:/project-list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/project-delete/{userId}/{id}")
    public String deleteProject(
            @NotNull final HttpSession session,
            @PathVariable("userId") @NotNull final String userId,
            @PathVariable("id") @NotNull final String id
    ) throws Exception {
        projectService.remove(userId, id);
        return "redirect:/project-list-all";
    }

    @GetMapping("/project-view/{userId}/{id}")
    public String viewProjectForm(
            @PathVariable("userId") @NotNull final String userId,
            @PathVariable("id") @NotNull final String id,
            @NotNull final Model model
    ) throws Exception {
        if (id.isEmpty()) throw new ProjectNotFoundException();
        if (userId.isEmpty()) throw new UserNotFoundException();
        @Nullable final Project project = projectService.findOneById(userId, id);
        if (project == null) throw new ProjectNotFoundException();
        model.addAttribute("project", project);
        return "project-view";
    }

    @GetMapping("/project-update/{userId}/{id}")
    public String updateProjectForm(
            @NotNull final HttpSession session,
            @PathVariable("userId") @NotNull final String userId,
            @PathVariable("id") @NotNull final String id,
            @NotNull final Model model
    ) throws Exception {
        if (userId.isEmpty()) throw new UserNotFoundException();
        @Nullable final Project project = projectService.findOneById(userId, id);
        model.addAttribute("project", project);
        return "project-update";
    }

    @PostMapping("/project-update")
    public String updateProject(
            @NotNull final HttpSession session,
            @ModelAttribute("userId") @Nullable final String userId,
            @ModelAttribute("id") @Nullable final String id,
            @ModelAttribute("name") @Nullable final String name,
            @ModelAttribute("description") @Nullable final String description,
            @ModelAttribute("statusType") @Nullable final String statusType,
            @ModelAttribute("startDate") @Nullable final String startDate,
            @ModelAttribute("finishDate") @Nullable final String finishDate
    ) throws Exception, UserNotFoundException, ProjectNotFoundException {
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        @Nullable final User user = userService.findOneByLogin((String) session.getAttribute("login"));
        if (user == null) throw new UserNotFoundException();
        @Nullable Project project = null;
        if (!user.getId().equals(userId) 
                || user.getRoleType().equals(RoleType.ADMIN))
            project = projectService.findOneById(userId, id);
        else if (user.getId().equals(userId))
            project = projectService.findOneById(userId, id);
        if (project == null) throw new ProjectNotFoundException();
        if (name == null) throw new EmptyInputException("project name");
        project.setName(name);
        project.setDescription(description);
        project.setStatusType(StatusType.valueOf(statusType));
        project.setStartDate(DateFormatterUtil.parseDate(startDate));
        project.setFinishDate(DateFormatterUtil.parseDate(finishDate));
        projectService.merge(project);
        return "redirect:/project-list";
    }

    @GetMapping("/project-view/{id}")
    public String viewProjectForm(
            @NotNull final HttpSession session,
            @PathVariable("id") @NotNull final String id,
            @NotNull final Model model
    ) throws Exception {
        @Nullable final User user = userService.findOneByLogin((String) session.getAttribute("login"));
        if (user == null) throw new UserNotFoundException();
        @NotNull final String userId = user.getId();
        if (id.isEmpty()) throw new ProjectNotFoundException();
        @Nullable final Project project = projectService.findOneById(userId, id);
        if (project == null) throw new ProjectNotFoundException();
        model.addAttribute("project", project);
        return "project-view";
    }

    @Nullable
    @GetMapping("/project-search")
    public String findAllBySearch(
            @NotNull final HttpSession session,
            @RequestParam("search") @NotNull final String search,
            @NotNull final Model model
    ) throws Exception {
        @Nullable final User user = userService.findOneByLogin((String) session.getAttribute("login"));
        if (user == null) throw new UserNotFoundException();
        @NotNull final String userId = user.getId();
        @NotNull final List<Project> projectSearch = projectService.findAllBySearch(userId, search);
        if (projectSearch.isEmpty()) return null;
        model.addAttribute("projectSearch", projectSearch);
        return "project-search";
    }

    @Secured("ROLE_ADMIN")
    @Nullable
    @GetMapping("/project-search-all")
    public String findAllBySearch(
            @RequestParam("search") @NotNull final String search,
            @NotNull final Model model
    ) throws Exception {
        @NotNull final List<Project> projectSearch = projectService.findAllBySearch(search);
        if (projectSearch.isEmpty()) return null;
        model.addAttribute("projectSearch", projectSearch);
        return "project-search";
    }

    @GetMapping("/project-list-start-date-asc")
    public String findAllByStartDateAsc(
            @NotNull final HttpSession session,
            @NotNull final Model model
    ) throws Exception {
        @Nullable final User user = userService.findOneByLogin((String) session.getAttribute("login"));
        if (user == null) throw new UserNotFoundException();
        @NotNull final String userId = user.getId();
        @NotNull final List<Project> projects = projectService.findAllSortedByStartDateAsc(userId);
        model.addAttribute("projects", projects);
        return "project-list";
    }

    @GetMapping("/project-list-start-date-desc")
    public String findAllByStartDateDesc(
            @NotNull final HttpSession session,
            @NotNull final Model model
    ) throws Exception {
        @Nullable final User user = userService.findOneByLogin((String) session.getAttribute("login"));
        if (user == null) throw new UserNotFoundException();
        @NotNull final String userId = user.getId();
        @NotNull final List<Project> projects = projectService.findAllSortedByStartDateDesc(userId);
        model.addAttribute("projects", projects);
        return "project-list";
    }

    @GetMapping("/project-list-finish-date-asc")
    public String findAllByFinishDateAsc(
            @NotNull final HttpSession session,
            @NotNull final Model model
    ) throws Exception {
        @Nullable final User user = userService.findOneByLogin((String) session.getAttribute("login"));
        if (user == null) throw new UserNotFoundException();
        @NotNull final String userId = user.getId();
        @NotNull final List<Project> projects = projectService.findAllSortedByFinishDateAsc(userId);
        model.addAttribute("projects", projects);
        return "project-list";
    }

    @GetMapping("/project-list-finish-date-desc")
    public String findAllByFinishDateDesc(
            @NotNull final HttpSession session,
            @NotNull final Model model
    ) throws Exception {
        @Nullable final User user = userService.findOneByLogin((String) session.getAttribute("login"));
        if (user == null) throw new UserNotFoundException();
        @NotNull final String userId = user.getId();
        @NotNull final List<Project> projects = projectService.findAllSortedByFinishDateDesc(userId);
        model.addAttribute("projects", projects);
        return "project-list";
    }

    @GetMapping("/project-list-status-asc")
    public String findAllByStatusAsc(
            @NotNull final HttpSession session,
            @NotNull final Model model
    ) throws Exception {
        @Nullable final User user = userService.findOneByLogin((String) session.getAttribute("login"));
        if (user == null) throw new UserNotFoundException();
        @NotNull final String userId = user.getId();
        @NotNull final List<Project> projects = projectService.findAllSortedByStatusAsc(userId);
        model.addAttribute("projects", projects);
        return "project-list";
    }

    @GetMapping("/project-list-status-desc")
    public String findAllByStatusDesc(
            @NotNull final HttpSession session,
            @NotNull final Model model
    ) throws Exception {
        @Nullable final User user = userService.findOneByLogin((String) session.getAttribute("login"));
        if (user == null) throw new UserNotFoundException();
        @NotNull final String userId = user.getId();
        @NotNull final List<Project> projects = projectService.findAllSortedByStatusDesc(userId);
        model.addAttribute("projects", projects);
        return "project-list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/project-list-start-date-asc-all")
    public String findAllByStartDateAsc(@NotNull final Model model) throws Exception {
        @NotNull final List<Project> projects = projectService.findAllSortedByStartDateAsc();
        model.addAttribute("projects", projects);
        return "project-list-all";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/project-list-start-date-desc-all")
    public String findAllByStartDateDesc(@NotNull final Model model) throws Exception {
        @NotNull final List<Project> projects = projectService.findAllSortedByStartDateDesc();
        model.addAttribute("projects", projects);
        return "project-list-all";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/project-list-finish-date-asc-all")
    public String findAllByFinishDateAsc(@NotNull final Model model) throws Exception {
        @NotNull final List<Project> projects = projectService.findAllSortedByFinishDateAsc();
        model.addAttribute("projects", projects);
        return "project-list-all";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/project-list-finish-date-desc-all")
    public String findAllByFinishDateDesc(@NotNull final Model model) throws Exception {
        @NotNull final List<Project> projects = projectService.findAllSortedByFinishDateDesc();
        model.addAttribute("projects", projects);
        return "project-list-all";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/project-list-status-asc-all")
    public String findAllByStatusAsc(@NotNull final Model model) throws Exception {
        @NotNull final List<Project> projects = projectService.findAllSortedByStatusAsc();
        model.addAttribute("projects", projects);
        return "project-list-all";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/project-list-status-desc-all")
    public String findAllByStatusDesc(@NotNull final Model model) throws Exception {
        @NotNull final List<Project> projects = projectService.findAllSortedByStatusDesc();
        model.addAttribute("projects", projects);
        return "project-list-all";
    }
}