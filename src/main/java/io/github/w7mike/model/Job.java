package io.github.w7mike.model;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
public class Job extends BaseJob {
    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "job_groups_id")
    private JobGroup jobGroup;

    public Job() {
    }

    public Job(final String specification, LocalDateTime deadline) {
        this(specification, deadline, null);

    }

    public Job(final String specification, LocalDateTime deadline, JobGroup jobGroup) {
        super(specification);
        this.deadline = deadline;
        if (jobGroup != null){
            this.jobGroup = jobGroup;
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
        jobGroup = source.jobGroup;
    }
}


