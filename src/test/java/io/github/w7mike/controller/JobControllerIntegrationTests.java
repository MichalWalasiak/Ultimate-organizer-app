package io.github.w7mike.controller;

import io.github.w7mike.model.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class JobControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JobRepository jobRepository;
}
