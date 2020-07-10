package io.github.w7mike.model.projection;

import io.github.w7mike.model.JobGroup;
import io.github.w7mike.model.Project;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GroupWriteModel {

    @NotBlank(message = "job group's specification must be not null")
    private String specification;

    private List<GroupJobWriteModel> jobs = new ArrayList<>();

    public GroupWriteModel() {
        jobs.add(new GroupJobWriteModel());
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(final String specification) {
        this.specification = specification;
    }

    public List<GroupJobWriteModel> getJobs() {
        return jobs;
    }

    public void setJobs(final List<GroupJobWriteModel> jobs) {
        this.jobs = jobs;
    }

    public JobGroup toGroup(final Project project){
        var result = new JobGroup();
        result.setSpecification(specification);
        result.setJobs(jobs.stream()
                            .map(outcome -> outcome.doJob(result))
                            .collect(Collectors.toSet())
        );
        result.setProject(project);
        return result;
    }
}
