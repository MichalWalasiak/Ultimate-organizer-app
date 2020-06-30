package io.github.w7mike.model.projection;

import io.github.w7mike.model.JobGroup;

import java.util.Set;
import java.util.stream.Collectors;

public class GroupWriteModel {

    private String specification;
    private Set<GroupJobWriteModel> jobs;

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(final String specification) {
        this.specification = specification;
    }

    public Set<GroupJobWriteModel> getJobs() {
        return jobs;
    }

    public void setJobs(final Set<GroupJobWriteModel> jobs) {
        this.jobs = jobs;
    }

    public JobGroup toGroup(){
        var result = new JobGroup();
        result.setSpecification(specification);
        result.setJobs(jobs.stream()
                            .map(outcome -> outcome.doJob(result))
                            .collect(Collectors.toSet()));

        return result;
    }


}
