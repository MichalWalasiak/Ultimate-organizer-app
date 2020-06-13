package io.github.w7mike.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "project_steps")
public class ProjectSteps {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private Integer id;

    @NotBlank(message = "project steps specification must be not null")
    private String specification;
    private Integer daysToDeadline;

    @ManyToOne
    @JoinColumn(name = "projects_id")
    private Projects projects;

    public ProjectSteps() {
    }

    public Integer getId() {
        return id;
    }

    void setId(final Integer id) {
        this.id = id;
    }

    public String getSpecification() {
        return specification;
    }

    void setSpecification(final String specification) {
        this.specification = specification;
    }

    public Integer getDaysToDeadline() {
        return daysToDeadline;
    }

    void setDaysToDeadline(final Integer daysToDeadline) {
        this.daysToDeadline = daysToDeadline;
    }

    public Projects getProjects() {
        return projects;
    }

    void setProjects(final Projects projects) {
        this.projects = projects;
    }
}
