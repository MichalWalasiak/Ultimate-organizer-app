package io.github.w7mike.logic;

import io.github.w7mike.JobConfigurationProperties;
import io.github.w7mike.model.*;
import io.github.w7mike.model.projection.GroupReadModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class ProjectsService {

    private ProjectRepository projectRepository;
    private JobGroupsRepository groupsRepository;
    private JobConfigurationProperties properties;

    public ProjectsService(final ProjectRepository projectRepository, final JobGroupsRepository groupsRepository, final JobConfigurationProperties properties) {
        this.projectRepository = projectRepository;
        this.groupsRepository = groupsRepository;
        this.properties = properties;
    }

    public List<Projects> readAll(){
        return projectRepository.findAll();
    }

    public Projects createProject(final Projects toCreate){
        return projectRepository.save(toCreate);
    }

    public GroupReadModel createGroup(LocalDateTime deadline, Integer projectId){

        if (!properties.getTemplate().isAllowMultipleJobs() && groupsRepository.existsByCompleteIsFalseAndProjects_Id(projectId)){
            throw new IllegalStateException("only one incomplete group in project is allowed");
        }

        JobGroups result = projectRepository.findById(projectId)
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
