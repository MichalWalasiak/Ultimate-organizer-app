package io.github.w7mike.model.event;

import io.github.w7mike.model.Job;

import java.time.Clock;

public class JobUndone extends JobEvent {
    public JobUndone(final Job source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}
