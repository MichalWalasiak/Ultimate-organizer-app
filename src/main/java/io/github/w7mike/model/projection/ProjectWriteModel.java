package io.github.w7mike.model.projection;

import io.github.w7mike.model.ProjectStep;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

public class ProjectWriteModel {
    @NotBlank(message = "project's specification must be not null")
    private String specification;
    @Valid
    private List<ProjectStep> steps;

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(final String specification) {
        this.specification = specification;
    }

    public List<ProjectStep> getSteps() {
        return steps;
    }

    public void setSteps(final List<ProjectStep> steps) {
        this.steps = steps;
    }
}
