package io.github.w7mike.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "job_groups")
public class JobGroups extends BaseJob {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobGroups")
    private Set<Job> jobs;

    @ManyToOne
    @JoinColumn(name = "projects_id")
    private Projects projects;

    public JobGroups() {
    }

    public Set<Job> getJobs() {
        return jobs;
    }

     public void setJobs(final Set<Job> jobSet) {
        this.jobs = jobSet;
    }

}


