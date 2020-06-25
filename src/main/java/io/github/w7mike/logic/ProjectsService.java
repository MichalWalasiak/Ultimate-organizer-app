package io.github.w7mike.logic;

import io.github.w7mike.JobConfigurationProperties;
import io.github.w7mike.model.*;
import io.github.w7mike.model.projection.GroupJobWriteModel;
import io.github.w7mike.model.projection.GroupReadModel;
import io.github.w7mike.model.projection.GroupWriteModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class ProjectsService {

    private ProjectRepository projectRepository;
    private JobGroupsRepository groupsRepository;
    private JobGroupsService service;
    private JobConfigurationProperties properties;

    public ProjectsService(final ProjectRepository projectRepository, final JobGroupsRepository groupsRepository, final JobGroupsService service, final JobConfigurationProperties properties) {
        this.projectRepository = projectRepository;
        this.groupsRepository = groupsRepository;
        this.service = service;
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
        return projectRepository.findById(projectId)
                .map(projects -> {
                    var target = new GroupWriteModel();
                    target.setSpecification(projects.getSpecification());
                    target.setJobs
                            (projects.getSteps().stream()
                            .map(projectSteps -> {
                                        var job = new GroupJobWriteModel();
                                        job.setSpecification(projectSteps.getSpecification());
                                        job.setDeadline(deadline.plusDays(projectSteps.getDaysToDeadline()));
                                        return job;
                            }
                            ).collect(Collectors.toSet())
                    );
                    return service.createGroup(target);
                }).orElseThrow(()-> new IllegalArgumentException("project with given Id do not exists"));
    }
}
