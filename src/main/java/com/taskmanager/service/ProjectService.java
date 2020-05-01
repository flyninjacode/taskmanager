package com.taskmanager.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taskmanager.api.service.IProjectService;
import com.taskmanager.api.service.IUserService;
import com.taskmanager.api.repository.IProjectRepository;
import com.taskmanager.dto.ProjectDTO;
import com.taskmanager.exception.EmptyInputException;
import com.taskmanager.exception.ProjectNotFoundException;
import com.taskmanager.exception.UserNotFoundException;
import com.taskmanager.model.Project;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProjectService extends AbstractProjectTaskService<Project> implements IProjectService {

    @Autowired
    @NotNull private IProjectRepository projectRepository;

    @Autowired
    @NotNull private IUserService userService;

    public ProjectService() {
    }

    @Override
    @NotNull
    public Project getProjectEntity(@Nullable final ProjectDTO projectDTO) throws Exception {
        if (projectDTO == null) throw new Exception("[The project does not exist.]");
        @NotNull final Project project = new Project();
        project.setId(projectDTO.getId());
        project.setUser(userService.findOneById(projectDTO.getUserId()));
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setCreateDate(projectDTO.getCreateDate());
        project.setStartDate(projectDTO.getStartDate());
        project.setFinishDate(projectDTO.getFinishDate());
        project.setStatusType(projectDTO.getStatusType());
        return project;
    }

    @Override
    @NotNull
    public ProjectDTO getProjectDTO(@Nullable final Project project) throws Exception {
        if (project == null) throw new Exception("[The project does not exist.]");
        @NotNull final ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setUserId(project.getUser().getId());
        projectDTO.setName(project.getName());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setCreateDate(project.getCreateDate());
        projectDTO.setStartDate(project.getStartDate());
        projectDTO.setFinishDate(project.getFinishDate());
        projectDTO.setStatusType(project.getStatusType());
        return projectDTO;
    }

    @Override
    @NotNull
    public List<ProjectDTO> getListProjectDTO(@Nullable final List<Project> projects) throws Exception {
        if (projects == null) throw new Exception("[There're no projects yet.]");
        @NotNull final List<ProjectDTO> listProjectDTO = new ArrayList<>();
        for (@NotNull final Project project : projects)
            listProjectDTO.add(getProjectDTO(project));
        return listProjectDTO;
    }

    @NotNull
    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @NotNull
    @Override
    public List<Project> findAllByUserId(@Nullable final String userId)
            throws Exception, UserNotFoundException {
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        return projectRepository.findAllByUserId(userId);
    }

    @Nullable
    @Override
    public Project findOneById(@Nullable final String userId, @Nullable final String id)
            throws Exception, EmptyInputException, UserNotFoundException {
        if (id == null || id.isEmpty()) throw new EmptyInputException("project");
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        return projectRepository.findOneById(userId, id);
    }

    @NotNull
    @Override
    public List<Project> findOneByName(@Nullable final String userId, @Nullable final String name)
            throws Exception, EmptyInputException, UserNotFoundException {
        if (name == null || name.isEmpty()) throw new EmptyInputException("project");
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        return projectRepository.findOneByName(userId, name);
    }

    @Override
    public void persist(@Nullable final Project project) throws ProjectNotFoundException {
        if (project == null) throw new ProjectNotFoundException();
        projectRepository.save(project);
    }

    @Override
    public void merge(@Nullable final Project project) throws ProjectNotFoundException {
        if (project == null) throw new ProjectNotFoundException();
        projectRepository.save(project);
    }

    @Override
    public void remove(@Nullable final String userId, @Nullable final String id)
            throws Exception, EmptyInputException, UserNotFoundException {
        if (id == null || id.isEmpty()) throw new EmptyInputException("project");
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        @Nullable final Project project = findOneById(userId, id);
        if (project == null) throw new ProjectNotFoundException();
        projectRepository.delete(project);
    }

    @Override
    public void removeAllByUserId(@Nullable final String userId)
            throws Exception, UserNotFoundException {
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        for (@NotNull final Project project : findAllByUserId(userId))
            projectRepository.delete(project);
    }

    @Override
    public void removeAll() {
        projectRepository.deleteAll();
    }

    @Override
    @NotNull
    public List<Project> findAllSortedByCreateDate(@Nullable final String userId)
            throws Exception, UserNotFoundException {
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        return projectRepository.findAllSortedByCreateDate(userId);
    }

    @Override
    @NotNull
    public List<Project> findAllSortedByStartDateAsc(@Nullable final String userId)
            throws Exception, UserNotFoundException {
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        return projectRepository.findAllSortedByStartDateAsc(userId);
    }

    @Override
    @NotNull
    public List<Project> findAllSortedByStartDateDesc(@Nullable final String userId)
            throws Exception, UserNotFoundException {
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        return projectRepository.findAllSortedByStartDateDesc(userId);
    }

    @Override
    @NotNull
    public List<Project> findAllSortedByStartDateAsc()
            throws Exception, UserNotFoundException {
        return projectRepository.findAllSortedByStartDateAsc();
    }

    @Override
    @NotNull
    public List<Project> findAllSortedByStartDateDesc()
            throws Exception, UserNotFoundException {
        return projectRepository.findAllSortedByStartDateDesc();
    }

    @Override
    @NotNull
    public List<Project> findAllSortedByFinishDateAsc(@Nullable final String userId)
            throws Exception, UserNotFoundException {
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        return projectRepository.findAllSortedByFinishDateAsc(userId);
    }

    @Override
    @NotNull
    public List<Project> findAllSortedByFinishDateDesc(@Nullable final String userId)
            throws Exception, UserNotFoundException {
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        return projectRepository.findAllSortedByFinishDateDesc(userId);
    }

    @Override
    @NotNull
    public List<Project> findAllSortedByFinishDateAsc()
            throws Exception, UserNotFoundException {
        return projectRepository.findAllSortedByFinishDateAsc();
    }

    @Override
    @NotNull
    public List<Project> findAllSortedByFinishDateDesc()
            throws Exception, UserNotFoundException {
        return projectRepository.findAllSortedByFinishDateDesc();
    }

    @Override
    @NotNull
    public List<Project> findAllSortedByStatusAsc(@Nullable final String userId)
            throws Exception, UserNotFoundException {
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        return projectRepository.findAllSortedByStatusAsc(userId);
    }

    @Override
    @NotNull
    public List<Project> findAllSortedByStatusDesc(@Nullable final String userId)
            throws Exception, UserNotFoundException {
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        return projectRepository.findAllSortedByStatusDesc(userId);
    }

    @Override
    @NotNull
    public List<Project> findAllSortedByStatusAsc()
            throws Exception, UserNotFoundException {
        return projectRepository.findAllSortedByStatusAsc();
    }

    @Override
    @NotNull
    public List<Project> findAllSortedByStatusDesc()
            throws Exception, UserNotFoundException {
        return projectRepository.findAllSortedByStatusDesc();
    }

    @NotNull
    @Override
    public List<Project> findAllBySearch(@Nullable final String userId, @Nullable final String search)
            throws Exception {
        if (search == null || search.isEmpty()) throw new Exception("[The search query is empty.]");
        if (userId == null || userId.isEmpty()) throw new UserNotFoundException();
        return projectRepository.findAllBySearch(userId, '%' + search + '%');
    }

    @Override
    @NotNull
    public List<Project> findAllBySearch(@Nullable final String search)
            throws Exception {
        if (search == null || search.isEmpty()) throw new Exception("[The search query is empty.]");
        return projectRepository.findAllBySearch('%' + search + '%');
    }
}
