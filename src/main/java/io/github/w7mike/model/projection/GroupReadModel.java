package io.github.w7mike.model.projection;

import io.github.w7mike.model.Job;
import io.github.w7mike.model.JobGroups;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupReadModel {

    private String specification;

    /**
     *deadline from the latest job in group
     */
    private LocalDateTime deadline;
    private Set<GroupJobReadModel> jobs;

    public GroupReadModel(JobGroups source){
        specification = source.getSpecification();
            source.getJobs().stream()
                    .sorted(Comparator.comparing(Job::getDeadline))
                    .map(Job::getDeadline)
                    .max(LocalDateTime::compareTo)
                    .ifPresent(date -> deadline = date);

            jobs = source.getJobs()
                    .stream()
                    .map(GroupJobReadModel::new)
                    .collect(Collectors.toSet());

    }

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
}
