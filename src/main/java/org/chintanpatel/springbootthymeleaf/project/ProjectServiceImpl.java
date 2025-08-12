package org.chintanpatel.springbootthymeleaf.project;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("projectService")
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void addProject(Project project) {
        projectRepository.save(project);
    }

    @Override
    public List<Project> getAllProjectList() {
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId).orElse(null);
    }

    @Override
    public void deleteProjectById(Long projectId) {
        projectRepository.deleteById(projectId);
    }

    @Override
    public List<Project> searchByProjectName(String projectName) {
        return projectRepository.findByProjectNameContainingIgnoreCase(projectName);
    }

    @Override
    public List<Project> searchByPriorityType(String priorityType) {
        return projectRepository.findByPriorityTypeContainingIgnoreCase(priorityType);
    }
}
