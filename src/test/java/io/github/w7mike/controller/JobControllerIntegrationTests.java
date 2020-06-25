package io.github.w7mike.controller;

import io.github.w7mike.model.Job;
import io.github.w7mike.model.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
public class JobControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JobRepository jobRepository;

    @Test
    @DisplayName("should return given Job")
    void httpGet_returnsGivenJob() {
        // given
        jobRepository.save(new Job("foo", LocalDateTime.now()));


    }
}
