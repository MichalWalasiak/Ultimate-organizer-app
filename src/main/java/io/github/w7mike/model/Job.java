package io.github.w7mike.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
public class Job extends BaseJob {
    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "job_groups_id")
    private JobGroups jobGroups;

    public Job() {
    }

    public Job(final String specification, LocalDateTime deadline) {
        this(specification, deadline, null);

    }

    public Job(final String specification, LocalDateTime deadline, JobGroups jobGroups) {
        super(specification);
        this.deadline = deadline;
        if (jobGroups != null){
            this.jobGroups = jobGroups;
        }

    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(final LocalDateTime deadline) {
        this.deadline = deadline;
    }

    @Override
    public void updateFrom(final Job source){
        super.updateFrom(source);
        deadline = source.deadline;
        jobGroups = source.jobGroups;
    }


}


