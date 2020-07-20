package io.github.w7mike.model.event;

import io.github.w7mike.model.Job;

import java.time.Clock;

public class JobDone extends JobEvent {

    JobDone(final Job source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}
