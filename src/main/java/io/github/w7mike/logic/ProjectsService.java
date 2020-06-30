package io.github.w7mike.logic;

import io.github.w7mike.JobConfigurationProperties;
import io.github.w7mike.model.*;
import io.github.w7mike.model.projection.GroupJobWriteModel;
import io.github.w7mike.model.projection.GroupReadModel;
import io.github.w7mike.model.projection.GroupWriteModel;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


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

    public Project createProject(final Project toCreate){
        return projectRepository.save(toCreate);
    }

    public GroupReadModel createGroup(LocalDateTime deadline, Integer projectId){

        if (!properties.getTemplate().isAllowMultipleJobs() && groupsRepository.existsByCompleteIsFalseAndProject_Id(projectId)){
            throw new IllegalStateException("only one incomplete group in project is allowed");
        }

        Optional<Project> projects = projectRepository.findById(projectId);
        if (projects.isEmpty()) {
            throw new IllegalArgumentException("project with given Id do not exists");
        }

        final Set<GroupJobWriteModel> jobs = createGroupJobWriteModels(deadline, projects);

        var target = new GroupWriteModel();
        target.setSpecification(projects.get().getSpecification());
        target.setJobs(jobs);

        return service.createGroup(target);
    }

    private Set<GroupJobWriteModel> createGroupJobWriteModels(final LocalDateTime deadline, final Optional<Project> projects) {
        final Set<GroupJobWriteModel> jobs = new HashSet();
        for (ProjectSteps projectSteps : projects.get().getSteps()) {
            var job = new GroupJobWriteModel();
            job.setSpecification(projectSteps.getSpecification());
            job.setDeadline(deadline.plusDays(projectSteps.getDaysToDeadline()));
            jobs.add(job);
        }
        return jobs;
    }
}
