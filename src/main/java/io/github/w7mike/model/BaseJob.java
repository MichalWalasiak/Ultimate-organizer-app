package io.github.w7mike.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@MappedSuperclass
abstract class BaseJob {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private Integer id;

    @NotBlank(message = "task specification must be not null")
    private String specification;
    private boolean complete;

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    @PrePersist
    void createdOn(){
        createdOn = LocalDateTime.now();
    }

    @PreUpdate
    void updatedOn(){
        updatedOn = LocalDateTime.now();
    }

    public BaseJob(){
    }

    public BaseJob(String specification) {
        this.specification = specification;
    }

    public Integer getId() {
        return id;
    }

    void setId(Integer id) {
        this.id = id;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public void updateFrom(final Job source){
        specification = source.getSpecification();
        complete = source.isComplete();
    }
}
