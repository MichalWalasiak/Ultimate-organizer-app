package io.github.w7mike.model.projection;

import io.github.w7mike.model.JobGroups;

import java.time.LocalDateTime;
import java.util.Set;

public class GroupReadModel {

    private String specification;

    /**
     *deadline from the latest job in group
     */
    private LocalDateTime deadline;
    private Set<GroupJobReadModel> jobs;

    public GroupReadModel(JobGroups source){
        specification = source.getSpecification();

//        source.getJ.stream()
//                .
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
