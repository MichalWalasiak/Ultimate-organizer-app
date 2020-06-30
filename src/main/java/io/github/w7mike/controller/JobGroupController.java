package io.github.w7mike.controller;

import io.github.w7mike.logic.JobGroupService;
import io.github.w7mike.model.Job;
import io.github.w7mike.model.JobGroupRepository;
import io.github.w7mike.model.JobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class JobGroupController {

    private static final Logger logger = LoggerFactory.getLogger(JobGroupController.class);

    private JobGroupService jobGroupService;
    private JobRepository jobRepository;

    public JobGroupController(final JobGroupService jobGroupService, final JobRepository jobRepository) {
        this.jobGroupService = jobGroupService;
        this.jobRepository = jobRepository;
    }

    @PostMapping
    ResponseEntity<Job> createJob(@RequestBody @Valid Job newJob){
        Job result = jobRepository.save(newJob);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @GetMapping(params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Job>> readAllJobs(){
        logger.warn("You are going to read all available Jobs");
        return ResponseEntity.ok(jobRepository.findAll());
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<Job> toggleJob(@PathVariable int id){
        if(!jobRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }

        Job job = jobRepository.findById(id)
                .get();
        job.setComplete(!job.isComplete());


        return ResponseEntity.ok(job);
    }
}
