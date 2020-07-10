package io.github.w7mike.model.projection;

import io.github.w7mike.model.JobGroup;
import io.github.w7mike.model.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupWriteModel {

    private String specification;
    private List<GroupJobWriteModel> jobs = new ArrayList<>();

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
