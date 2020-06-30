package io.github.w7mike.model;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "job_groups")
public class JobGroup extends BaseJob {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobGroups")
    private Set<Job> jobs;

    @ManyToOne
    @JoinColumn(name = "projects_id")
    private Project project;

    public JobGroup() {
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public void setJobs(final Set<Job> jobSet) {
        this.jobs = jobSet;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(final Project project) {
        this.project = project;
    }
}


