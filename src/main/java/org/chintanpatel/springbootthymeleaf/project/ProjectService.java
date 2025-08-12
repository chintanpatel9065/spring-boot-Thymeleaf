package org.chintanpatel.springbootthymeleaf.project;

import java.util.List;

public interface ProjectService {

    void addProject(Project project);

    List<Project>getAllProjectList();

    Project getProjectById(Long projectId);

    void deleteProjectById(Long projectId);

    List<Project>searchByProjectName(String projectName);

    List<Project>searchByPriorityType(String priorityType);
}
