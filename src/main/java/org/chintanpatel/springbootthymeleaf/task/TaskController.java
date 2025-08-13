package org.chintanpatel.springbootthymeleaf.task;

import jakarta.validation.Valid;
import org.chintanpatel.springbootthymeleaf.project.Project;
import org.chintanpatel.springbootthymeleaf.project.ProjectService;
import org.chintanpatel.springbootthymeleaf.status.Status;
import org.chintanpatel.springbootthymeleaf.status.StatusService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TaskController {

    private final TaskService taskService;
    private final StatusService statusService;
    private final ProjectService projectService;

    public TaskController(TaskService taskService, StatusService statusService, ProjectService projectService) {
        this.taskService = taskService;
        this.statusService = statusService;
        this.projectService = projectService;
    }

    @GetMapping("/tasks")
    public String listTasks(Model model) {
        List<Task> taskList = taskService.getAllTaskList();
        model.addAttribute("taskList", taskList);
        return "task/task-list";
    }

    @GetMapping("/tasks/showTask")
    public String showTaskForm(Model model) {
        Task task = new Task();
        List<Status> statusList = statusService.getAllStatusList();
        List<Project> projectList = projectService.getAllProjectList();
        model.addAttribute("task", task);
        model.addAttribute("statusList", statusList);
        model.addAttribute("projectList", projectList);
        return "task/task-form";
    }

    @PostMapping("/tasks/insertOrUpdateTask")
    public String insertOrUpdateTask(@Valid @ModelAttribute("task")Task task, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            List<Status> statusList = statusService.getAllStatusList();
            List<Project> projectList = projectService.getAllProjectList();
            model.addAttribute("statusList", statusList);
            model.addAttribute("projectList", projectList);
            return "task/task-form";
        }
        taskService.addTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/manageTask/{taskId}")
    public String manageTask(@PathVariable Long taskId, Model model) {
        if (taskId != null) {
            Task task = taskService.getTaskById(taskId);
            List<Status> statusList = statusService.getAllStatusList();
            List<Project> projectList = projectService.getAllProjectList();
            model.addAttribute("task", task);
            model.addAttribute("statusList", statusList);
            model.addAttribute("projectList", projectList);
        }
        return "task/task-form";
    }

    @GetMapping("/tasks/deleteTask/{taskId}")
    public String deleteTask(@PathVariable Long taskId) {
        if (taskId != null) {
            taskService.deleteTaskById(taskId);
        }
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/search/taskName")
    public String searchByTaskName(@RequestParam("taskName") String taskName, Model model) {
        List<Task> taskList = taskService.searchByTaskName(taskName);
        model.addAttribute("taskList", taskList);
        model.addAttribute("taskName", taskName);
        model.addAttribute("searchType", "taskName");
        return "task/task-list";
    }

    @GetMapping("/tasks/search/statusType")
    public String searchByStatusType(@RequestParam("statusType")String statusType, Model model) {
        List<Task> taskList = taskService.searchByStatusType(statusType);
        model.addAttribute("taskList", taskList);
        model.addAttribute("statusType", statusType);
        model.addAttribute("searchType", "statusType");
        return "task/task-list";
    }

    @GetMapping("/tasks/search/projectName")
    public String searchByProjectName(@RequestParam("projectName")String projectName, Model model) {
        List<Task> taskList = taskService.searchByProjectName(projectName);
        model.addAttribute("taskList", taskList);
        model.addAttribute("projectName", projectName);
        model.addAttribute("searchType", "projectName");
        return "task/task-list";
    }
}
