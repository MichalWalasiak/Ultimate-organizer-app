package io.github.w7mike.model.projection;

import io.github.w7mike.model.Job;
import io.github.w7mike.model.JobGroup;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class GroupJobWriteModel {

    @NotBlank(message = "task specification must be not null")
    private String specification;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
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

    public Job doJob(final JobGroup jobGroup){
        return new Job(specification, deadline, jobGroup);
    }
}
