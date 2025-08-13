package org.chintanpatel.springbootthymeleaf.project;

import org.chintanpatel.springbootthymeleaf.priority.Priority;
import org.chintanpatel.springbootthymeleaf.priority.PriorityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private PriorityService priorityService;

    @Test
    @DisplayName("GET /projects should render project list view")
    void listProjects_shouldReturnList() throws Exception {
        when(projectService.getAllProjectList()).thenReturn(List.of(new Project()));

        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(view().name("project/project-list"))
                .andExpect(model().attributeExists("projectList"));

        verify(projectService).getAllProjectList();
    }

    @Test
    @DisplayName("GET /projects/showProject should render form with project and priorityList")
    void showProjectForm_shouldPopulateModel() throws Exception {
        when(priorityService.getAllPriorityList()).thenReturn(List.of(new Priority()));

        mockMvc.perform(get("/projects/showProject"))
                .andExpect(status().isOk())
                .andExpect(view().name("project/project-form"))
                .andExpect(model().attributeExists("project"))
                .andExpect(model().attributeExists("priorityList"));

        verify(priorityService).getAllPriorityList();
    }

    @Test
    @DisplayName("POST /projects/insertOrUpdateProject with valid data should redirect")
    void insertOrUpdateProject_success() throws Exception {
        mockMvc.perform(post("/projects/insertOrUpdateProject")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("projectName", "My Project")
                        .param("description", "Desc")
                        .param("startDate", LocalDate.now().toString())
                        .param("endDate", LocalDate.now().plusDays(10).toString())
                        .param("priority.priorityId", "3")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));

        verify(projectService).addProject(any(Project.class));
    }
}
