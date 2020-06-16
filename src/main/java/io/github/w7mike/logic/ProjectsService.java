package io.github.w7mike.logic;

import io.github.w7mike.JobConfigurationProperties;
import io.github.w7mike.model.JobGroupsRepository;
import io.github.w7mike.model.JobRepository;
import io.github.w7mike.model.ProjectRepository;
import io.github.w7mike.model.Projects;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectsService {

    private ProjectRepository repository;
    private JobGroupsRepository groupsRepository;
    private JobConfigurationProperties properties;

    public ProjectsService(final ProjectRepository repository, final JobGroupsRepository groupsRepository, final JobConfigurationProperties properties) {
        this.repository = repository;
        this.groupsRepository = groupsRepository;
        this.properties = properties;
    }


}
