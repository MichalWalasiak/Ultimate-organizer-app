package io.github.w7mike.controller;

import io.github.w7mike.model.Job;
import io.github.w7mike.model.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
public class JobControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JobRepository jobRepository;

    @Test
    @DisplayName("should return given Job")
    void httpGet_returnsGivenJob() throws Exception {
        // given
        var id = jobRepository.save(new Job("foo", LocalDateTime.now())).getId();

        // when + then
        mockMvc.perform(get("/jobs/" + id))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("Should return all jobs")
    void httpGet_returnsAllJobs() throws Exception {
        // given
        jobRepository.save(new Job("foo", LocalDateTime.now()));
        jobRepository.save(new Job("bar", LocalDateTime.now()));

        // when + then
        mockMvc.perform(get("/jobs"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}
