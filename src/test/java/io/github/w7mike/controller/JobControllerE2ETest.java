package io.github.w7mike.controller;

import io.github.w7mike.model.Job;
import io.github.w7mike.model.JobRepository;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Should return correct size of Job Array")
    void httpGet_returnAllJobs(){
        // given
        var initial = jobRepository.findAll().size();
        jobRepository.save(new Job("foo", LocalDateTime.now()));
        jobRepository.save(new Job("bar", LocalDateTime.now()));

        // when
        Job[] outcome = restTemplate.getForObject("http://localhost:" + port + "/jobs", Job[].class);

        // then
        assertThat(outcome).hasSize(initial + 2);
    }

    @Test
    @DisplayName("Should compere fields in data base after saving Job")
    void httpPost_savesJob() {
        //given
        Job jobToSave = jobRepository.save(new Job("foo", LocalDateTime.now()));

        //when
        Job job = restTemplate.postForObject("http://localhost:" + port + "/jobs", jobToSave, Job.class);

        //then
        assertThat(job).isEqualToComparingOnlyGivenFields(jobToSave, "specification", "deadline");
        assertThat(job).isExactlyInstanceOf(Job.class);
    }
}
