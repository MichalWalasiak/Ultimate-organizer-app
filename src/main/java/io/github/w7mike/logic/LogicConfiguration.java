package io.github.w7mike.logic;

import io.github.w7mike.JobConfigurationProperties;
import io.github.w7mike.model.JobGroupsRepository;
import io.github.w7mike.model.JobRepository;
import io.github.w7mike.model.ProjectRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogicConfiguration {

    @Bean
    public ProjectsService service(
            final ProjectRepository projectRepository,
            final JobGroupsRepository groupsRepository,
            final JobConfigurationProperties properties
    ){
        return new ProjectsService(projectRepository, groupsRepository, properties);
    }

    @Bean
    public JobGroupsService jobGroupsService(
            final JobGroupsRepository groupsRepository,
            final JobRepository jobRepository
    ){
        return new JobGroupsService(groupsRepository, jobRepository);
    }
}
