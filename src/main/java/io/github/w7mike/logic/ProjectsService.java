package io.github.w7mike.logic;

import io.github.w7mike.JobConfigurationProperties;
import io.github.w7mike.model.*;
import io.github.w7mike.model.projection.GroupJobReadModel;
import io.github.w7mike.model.projection.GroupReadModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class ProjectsService {

    private ProjectRepository repository;
    private JobGroupsRepository groupsRepository;
    private JobConfigurationProperties properties;

    public ProjectsService(final ProjectRepository repository, final JobGroupsRepository groupsRepository, final JobConfigurationProperties properties) {
        this.repository = repository;
        this.groupsRepository = groupsRepository;
        this.properties = properties;
    }

    public List<Projects> readAll(){
        return repository.findAll();
    }

    public Projects createProject(final Projects toCreate){
        return repository.save(toCreate);
    }

    public GroupReadModel createGroup(LocalDateTime deadline, Integer projectId){

        if (!properties.getTemplate().isAllowMultipleJobs() && groupsRepository.existsByCompleteIsFalseAndProjects_Id(projectId)){
            throw new IllegalStateException("only one incomplete group in project is allowed");
        }

        JobGroups result = repository.findById(projectId)
                .map(projects -> {
                    var target = new JobGroups();
                    target.setSpecification(projects.getSpecification());
                    target.setJobs(projects.getSteps().stream()
                    .map(projectSteps -> new Job
                            (projectSteps.getSpecification(),
                                    deadline.plusDays(projectSteps.getDaysToDeadline()))
                    ).collect(Collectors.toSet())
                );
                target.setProjects(projects);
                return groupsRepository.save(target);
                }).orElseThrow(()-> new IllegalArgumentException("project with given Id do not exists"));

        return new GroupReadModel(result);
    }
}
