package io.github.w7mike.controller;

import io.github.w7mike.model.Job;
import io.github.w7mike.model.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JobController.class)
public class JobControllerLightIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JobRepository jobRepository;

    @Test
    @DisplayName("should return given Job")
    void httpGet_returnsGivenJob() throws Exception {
        // given
        when(jobRepository.findById(anyInt())).thenReturn(Optional.of(new Job("foo", LocalDateTime.now())));

        // when + then
        mockMvc.perform(get("/jobs/1"))
                .andDo(print())
                .andExpect(content().string(containsString("\"specification\":\"foo\"")));


    }
}
