package io.github.w7mike.logic;

import io.github.w7mike.JobConfigurationProperties;
import io.github.w7mike.model.*;
import io.github.w7mike.model.projection.GroupJobWriteModel;
import io.github.w7mike.model.projection.GroupReadModel;
import io.github.w7mike.model.projection.GroupWriteModel;
import io.github.w7mike.model.projection.ProjectWriteModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class ProjectsService {

    private ProjectRepository projectRepository;
    private JobGroupRepository groupsRepository;
    private JobGroupService service;
    private JobConfigurationProperties properties;

    public ProjectsService(final ProjectRepository projectRepository, final JobGroupRepository groupsRepository, final JobGroupService service, final JobConfigurationProperties properties) {
        this.projectRepository = projectRepository;
        this.groupsRepository = groupsRepository;
        this.service = service;
        this.properties = properties;
    }

    public List<Project> readAll(){
        return projectRepository.findAll();
    }

    public Project save(final ProjectWriteModel toCreate){
        return projectRepository.save(toCreate.toProject());
    }

    public GroupReadModel createGroup(LocalDateTime deadline, Integer projectId) {

        if (!properties.getTemplate().isAllowMultipleJobs() && groupsRepository.existsByCompleteIsFalseAndProject_Id(projectId)) {
            throw new IllegalStateException("only one incomplete group in project is allowed");
        }
        return projectRepository.findById(projectId)
                .map(project -> {
                    var target = new GroupWriteModel();
                    target.setSpecification(project.getSpecification());
                    target.setJobs(
                            project.getSteps().stream()
                                    .map(projectSteps -> {
                                        var job = new GroupJobWriteModel();
                                        job.setSpecification(projectSteps.getSpecification());
                                        job.setDeadline(deadline.plusDays(projectSteps.getDaysToDeadline()));
                                        return job;
                                    }).collect(Collectors.toSet())
                    );
                    return service.createGroup(target, project);
                }).orElseThrow(() -> new IllegalArgumentException("project with given Id do not exists"));
    }
}



