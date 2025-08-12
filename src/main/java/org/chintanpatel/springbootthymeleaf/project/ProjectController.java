package org.chintanpatel.springbootthymeleaf.project;

import jakarta.validation.Valid;
import org.chintanpatel.springbootthymeleaf.priority.Priority;
import org.chintanpatel.springbootthymeleaf.priority.PriorityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProjectController {

    private final ProjectService projectService;
    private final PriorityService priorityService;

    public ProjectController(ProjectService projectService, PriorityService priorityService) {
        this.projectService = projectService;
        this.priorityService = priorityService;
    }

    @GetMapping("/projects")
    public String listProjects(Model model) {
        List<Project> projectList = projectService.getAllProjectList();
        model.addAttribute("projectList", projectList);
        return "project/project-list";
    }

    @GetMapping("/projects/showProject")
    public String showProjectForm(Model model) {
        Project project = new Project();
        List<Priority> priorityList = priorityService.getAllPriorityList();
        model.addAttribute("project", project);
        model.addAttribute("priorityList", priorityList);
        return "project/project-form";
    }

    @PostMapping("/projects/insertOrUpdateProject")
    public String insertOrUpdateProject(@Valid @ModelAttribute("project")Project project, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            List<Priority> priorityList = priorityService.getAllPriorityList();
            model.addAttribute("priorityList", priorityList);
            return "project/project-form";
        }
        projectService.addProject(project);
        return "redirect:/projects";
    }

    @GetMapping("/projects/manageProject/{projectId}")
    public String manageProject(@PathVariable Long projectId, Model model) {
        if (projectId != null) {
            Project project = projectService.getProjectById(projectId);
            List<Priority> priorityList = priorityService.getAllPriorityList();
            model.addAttribute("project", project);
            model.addAttribute("priorityList", priorityList);
        }
        return "project/project-form";
    }

    @GetMapping("/projects/deleteProject/{projectId}")
    public String deleteProject(@PathVariable Long projectId) {
        if (projectId != null) {
            projectService.deleteProjectById(projectId);
        }
        return "redirect:/projects";
    }

    @GetMapping("/projects/searchProject")
    public String searchByProjectName(@RequestParam("projectName") String projectName, Model model) {
        List<Project> projectList = projectService.searchByProjectName(projectName);
        model.addAttribute("projectList", projectList);
        model.addAttribute("projectName", projectName);
        model.addAttribute("searchType", "projectName");
        return "project/project-list";
    }

    @GetMapping("/projects/searchPriority")
    public String searchByPriorityType(@RequestParam("priorityType") String priorityType, Model model) {
        List<Project> projectList = projectService.searchByPriorityType(priorityType);
        model.addAttribute("projectList", projectList);
        model.addAttribute("priorityType", priorityType);
        model.addAttribute("searchType", "priorityType");
        return "project/project-list";
    }
}
