package io.github.w7mike.controller;

import io.github.w7mike.model.Job;
import io.github.w7mike.model.JobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/jobs")
class JobController {

    private final Logger logger = LoggerFactory.getLogger(JobController.class);
    private final ApplicationEventPublisher eventPublisher;

    private final JobRepository repository;

    JobController(final JobRepository repository, final ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping
    ResponseEntity<Job> createJob(@RequestBody @Valid Job newJob){
        Job result = repository.save(newJob);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @GetMapping(params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Job>> readAllJobs(){
        logger.warn("You are going to read all available Jobs");
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping
    ResponseEntity<List<Job>> readAllJobs(Pageable page){
        logger.info("custom pagable");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @GetMapping("/{id}")
    ResponseEntity<Job>readSelectedJob(@PathVariable int id){
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/search/complete")
    ResponseEntity<List<Job>> readCompleteJobs(@RequestParam(defaultValue = "true") boolean status) {
            return ResponseEntity.ok(repository.findByComplete(status));
    }

    @PutMapping("/{id}")
    ResponseEntity<Job> updateJob(@PathVariable int id, @RequestBody Job jobToUpdate){
            if(!repository.existsById(id)){
                return ResponseEntity.notFound().build();
            }

            repository.findById(id)
                    .ifPresent(job -> {
                        job.updateFrom(jobToUpdate);
                        repository.save(job);
                    });
            return ResponseEntity.noContent().build();
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<Job> toggleJob(@PathVariable int id){
        if(!repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }

        repository.findById(id)
                .map(Job::toggle)
                .ifPresent(eventPublisher::publishEvent);
        return ResponseEntity.notFound().build();

        /*Job job = repository.findById(id).get();
        job.setComplete(!job.isComplete());

        return ResponseEntity.ok(job);*/
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Job> removeJob(@PathVariable int id){
        if (!repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
