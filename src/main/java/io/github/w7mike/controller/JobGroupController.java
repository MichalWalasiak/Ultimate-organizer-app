package io.github.w7mike.controller;

import io.github.w7mike.logic.JobGroupService;
import io.github.w7mike.model.Job;
import io.github.w7mike.model.JobRepository;
import io.github.w7mike.model.projection.GroupReadModel;
import io.github.w7mike.model.projection.GroupWriteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/groups")
public class JobGroupController {

    private static final Logger logger = LoggerFactory.getLogger(JobGroupController.class);

    private final JobGroupService jobGroupService;
    private final JobRepository jobRepository;

    JobGroupController(final JobGroupService jobGroupService, final JobRepository jobRepository) {
        this.jobGroupService = jobGroupService;
        this.jobRepository = jobRepository;
    }
    @ResponseBody
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel newGroup){
        GroupReadModel outcome = jobGroupService.createGroup(newGroup);
        return ResponseEntity.created(URI.create("/" + outcome.getId())).body(outcome);
    }
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<GroupReadModel>> readAllGroups(){
        return ResponseEntity.ok(jobGroupService.readAll().stream().collect(Collectors.toList()));
    }
    @ResponseBody
    @GetMapping("/{id}")
    ResponseEntity<List<Job>> readAllJobsFromGroup(@PathVariable int id){
        return ResponseEntity.ok(jobRepository.findAllByJobGroup_Id(id));
    }

    @ResponseBody
    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleGroup(@PathVariable int id){
        jobGroupService.toggleGroup(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalState(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
