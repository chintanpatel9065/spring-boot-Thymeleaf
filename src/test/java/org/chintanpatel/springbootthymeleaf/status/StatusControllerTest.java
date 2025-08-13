package org.chintanpatel.springbootthymeleaf.status;

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

@WebMvcTest(StatusController.class)
class StatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatusService statusService;

    @Test
    @DisplayName("GET /statuses should render status list view")
    void listStatus_shouldReturnList() throws Exception {
        when(statusService.getAllStatusList()).thenReturn(List.of(new Status()));

        mockMvc.perform(get("/statuses"))
                .andExpect(status().isOk())
                .andExpect(view().name("status/status-list"))
                .andExpect(model().attributeExists("statusList"));

        verify(statusService).getAllStatusList();
    }

    @Test
    @DisplayName("GET /statuses/showStatus should render form with status")
    void showStatusForm_shouldPopulateModel() throws Exception {
        mockMvc.perform(get("/statuses/showStatus"))
                .andExpect(status().isOk())
                .andExpect(view().name("status/status-form"))
                .andExpect(model().attributeExists("status"));
    }

    @Test
    @DisplayName("POST /statuses/insertOrUpdateStatus with valid data should redirect")
    void insertOrUpdateStatus_success() throws Exception {
        mockMvc.perform(post("/statuses/insertOrUpdateStatus")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("statusType", "OPEN")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/statuses"));

        verify(statusService).addStatus(any(Status.class));
    }
}
