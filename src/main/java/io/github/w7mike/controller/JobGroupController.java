package io.github.w7mike.controller;

import io.github.w7mike.logic.JobGroupService;
import io.github.w7mike.model.JobGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobGroupController {

    private static final Logger logger = LoggerFactory.getLogger(JobGroupController.class);

    private JobGroupService jobGroupService;
    private JobGroupRepository jobGroupRepository;

    public JobGroupController(final JobGroupService jobGroupService, final JobGroupRepository jobGroupRepository) {
        this.jobGroupService = jobGroupService;
        this.jobGroupRepository = jobGroupRepository;
    }
}
