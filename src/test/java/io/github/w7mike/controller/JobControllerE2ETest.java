package io.github.w7mike.controller;

import io.github.w7mike.model.Job;
import io.github.w7mike.model.JobRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JobControllerE2ETest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    JobRepository jobRepository;

    @Test
    void httpGet_returnAllJobs(){
        // given
        jobRepository.save(new Job("foo", LocalDateTime.now()));
        jobRepository.save(new Job("bar", LocalDateTime.now()));

        // when
        Job[] outcome = restTemplate.getForObject("http://localhost:" + port + "/jobs", Job[].class);

        // then
        assertThat(outcome).hasSize(2);
    }
}
