package org.chintanpatel.springbootthymeleaf.task;

import org.chintanpatel.springbootthymeleaf.project.Project;
import org.chintanpatel.springbootthymeleaf.project.ProjectService;
import org.chintanpatel.springbootthymeleaf.status.Status;
import org.chintanpatel.springbootthymeleaf.status.StatusService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private StatusService statusService;

    @MockBean
    private ProjectService projectService;

    @Test
    @DisplayName("GET /tasks should render list view with taskList in model")
    void listTasks_shouldReturnListView() throws Exception {
        when(taskService.getAllTaskList()).thenReturn(List.of(new Task()));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(view().name("task/task-list"))
                .andExpect(model().attributeExists("taskList"));

        verify(taskService, times(1)).getAllTaskList();
    }

    @Test
    @DisplayName("GET /tasks/showTask should render form with task, statusList, projectList")
    void showTaskForm_shouldPopulateModel() throws Exception {
        when(statusService.getAllStatusList()).thenReturn(List.of(new Status()));
        when(projectService.getAllProjectList()).thenReturn(List.of(new Project()));

        mockMvc.perform(get("/tasks/showTask"))
                .andExpect(status().isOk())
                .andExpect(view().name("task/task-form"))
                .andExpect(model().attributeExists("task"))
                .andExpect(model().attributeExists("statusList"))
                .andExpect(model().attributeExists("projectList"));

        verify(statusService).getAllStatusList();
        verify(projectService).getAllProjectList();
    }

    @Test
    @DisplayName("POST /tasks/insertOrUpdateTask with valid data should redirect and call addTask")
    void insertOrUpdateTask_success() throws Exception {
        // No need to stub service for success path beyond verifying interaction
        mockMvc.perform(post("/tasks/insertOrUpdateTask")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("taskName", "Build UI")
                        .param("description", "Create landing page")
                        .param("startDate", LocalDate.now().toString())
                        .param("endDate", LocalDate.now().plusDays(7).toString())
                        .param("status.statusId", "1")
                        .param("project.projectId", "2")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tasks"));

        verify(taskService, times(1)).addTask(Mockito.any(Task.class));
    }

    @Test
    @DisplayName("POST /tasks/insertOrUpdateTask with validation errors should return form and include lists")
    void insertOrUpdateTask_validationErrors() throws Exception {
        when(statusService.getAllStatusList()).thenReturn(List.of(new Status()));
        when(projectService.getAllProjectList()).thenReturn(List.of(new Project()));

        // Missing required fields to trigger @Valid errors
        mockMvc.perform(post("/tasks/insertOrUpdateTask")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("taskName", "")
                        .param("description", "")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("task/task-form"))
                .andExpect(model().attributeExists("statusList"))
                .andExpect(model().attributeExists("projectList"))
                .andExpect(model().attributeHasFieldErrors("task", "taskName", "description", "startDate", "endDate", "status", "project"));

        verify(statusService).getAllStatusList();
        verify(projectService).getAllProjectList();
        verify(taskService, never()).addTask(any());
    }

    @Test
    @DisplayName("GET /tasks/manageTask/{id} should return form with task and lists")
    void manageTask_shouldPopulateModel() throws Exception {
        Task t = new Task();
        t.setTaskId(5L);
        when(taskService.getTaskById(5L)).thenReturn(t);
        when(statusService.getAllStatusList()).thenReturn(List.of(new Status()));
        when(projectService.getAllProjectList()).thenReturn(List.of(new Project()));

        mockMvc.perform(get("/tasks/manageTask/{taskId}", 5L))
                .andExpect(status().isOk())
                .andExpect(view().name("task/task-form"))
                .andExpect(model().attributeExists("task"))
                .andExpect(model().attributeExists("statusList"))
                .andExpect(model().attributeExists("projectList"));

        verify(taskService).getTaskById(5L);
    }

    @Test
    @DisplayName("GET /tasks/deleteTask/{id} should redirect and call delete")
    void deleteTask_shouldRedirect() throws Exception {
        mockMvc.perform(get("/tasks/deleteTask/{taskId}", 7L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tasks"));

        verify(taskService).deleteTaskById(7L);
    }

    @Test
    @DisplayName("GET /tasks/search/taskName should render list with searchType and taskName")
    void searchByTaskName_shouldReturnListView() throws Exception {
        when(taskService.searchByTaskName("Home"))
                .thenReturn(List.of(new Task()));

        mockMvc.perform(get("/tasks/search/taskName").param("taskName", "Home"))
                .andExpect(status().isOk())
                .andExpect(view().name("task/task-list"))
                .andExpect(model().attributeExists("taskList"))
                .andExpect(model().attribute("taskName", "Home"))
                .andExpect(model().attribute("searchType", "taskName"));
    }

    @Test
    @DisplayName("GET /tasks/search/statusType should render list with searchType and statusType")
    void searchByStatusType_shouldReturnListView() throws Exception {
        when(taskService.searchByStatusType("OPEN"))
                .thenReturn(List.of(new Task()));

        mockMvc.perform(get("/tasks/search/statusType").param("statusType", "OPEN"))
                .andExpect(status().isOk())
                .andExpect(view().name("task/task-list"))
                .andExpect(model().attributeExists("taskList"))
                .andExpect(model().attribute("statusType", "OPEN"))
                .andExpect(model().attribute("searchType", "statusType"));
    }

    @Test
    @DisplayName("GET /tasks/search/projectName should render list with searchType and projectName")
    void searchByProjectName_shouldReturnListView() throws Exception {
        when(taskService.searchByProjectName("Demo"))
                .thenReturn(List.of(new Task()));

        mockMvc.perform(get("/tasks/search/projectName").param("projectName", "Demo"))
                .andExpect(status().isOk())
                .andExpect(view().name("task/task-list"))
                .andExpect(model().attributeExists("taskList"))
                .andExpect(model().attribute("projectName", "Demo"))
                .andExpect(model().attribute("searchType", "projectName"));
    }
}
