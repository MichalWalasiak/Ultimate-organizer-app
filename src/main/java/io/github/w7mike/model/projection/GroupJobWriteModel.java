package io.github.w7mike.model.projection;

import io.github.w7mike.model.Job;
import io.github.w7mike.model.JobGroups;

import java.time.LocalDateTime;

public class GroupJobWriteModel {

    private String specification;
    private LocalDateTime deadline;

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(final String specification) {
        this.specification = specification;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(final LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Job doJob(final JobGroups jobGroups){
        return new Job(specification, deadline, jobGroups);
    }
}
