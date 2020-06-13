package io.github.w7mike.model.projection;

import io.github.w7mike.model.Job;

public class GroupJobReadModel {

    private String specification;
    private boolean complete;

    public GroupJobReadModel(Job source){
        specification = source.getSpecification();
        complete = source.isComplete();
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(final String specification) {
        this.specification = specification;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(final boolean complete) {
        this.complete = complete;
    }
}
