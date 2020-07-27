package io.github.w7mike.model.event;

import io.github.w7mike.model.BaseJob;

import java.time.Clock;

public class JobDone extends JobEvent {

    JobDone(final BaseJob source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}
