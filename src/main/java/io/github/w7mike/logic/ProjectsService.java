package io.github.w7mike.logic;

import io.github.w7mike.JobConfigurationProperties;
import io.github.w7mike.model.JobGroupsRepository;
import io.github.w7mike.model.JobRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectsService {

    private JobRepository repository;
    private JobGroupsRepository groupsRepository;
    private JobConfigurationProperties properties;

    public ProjectsService(final JobRepository repository, final JobGroupsRepository groupsRepository, final JobConfigurationProperties properties) {
        this.repository = repository;
        this.groupsRepository = groupsRepository;
        this.properties = properties;
    }
}
