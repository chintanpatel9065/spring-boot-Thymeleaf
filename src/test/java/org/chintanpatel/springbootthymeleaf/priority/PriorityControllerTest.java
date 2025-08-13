package org.chintanpatel.springbootthymeleaf.priority;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PriorityController.class)
class PriorityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriorityService priorityService;

    @Test
    @DisplayName("GET /priorities should render priority list view")
    void listPriority_shouldReturnList() throws Exception {
        when(priorityService.getAllPriorityList()).thenReturn(List.of(new Priority()));

        mockMvc.perform(get("/priorities"))
                .andExpect(status().isOk())
                .andExpect(view().name("priority/priority-list"))
                .andExpect(model().attributeExists("priorityList"));

        verify(priorityService).getAllPriorityList();
    }

    @Test
    @DisplayName("GET /priorities/showPriority should render form with priority")
    void showPriorityForm_shouldPopulateModel() throws Exception {
        mockMvc.perform(get("/priorities/showPriority"))
                .andExpect(status().isOk())
                .andExpect(view().name("priority/priority-form"))
                .andExpect(model().attributeExists("priority"));
    }

    @Test
    @DisplayName("POST /priorities/insertOrUpdatePriority with valid data should redirect")
    void insertOrUpdatePriority_success() throws Exception {
        mockMvc.perform(post("/priorities/insertOrUpdatePriority")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("priorityType", "HIGH")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/priorities"));

        verify(priorityService).addPriority(any(Priority.class));
    }
}
