package io.github.w7mike.model.event;

import io.github.w7mike.model.BaseJob;

import java.time.Clock;
import java.time.Instant;

public abstract class JobEvent {
    public static JobEvent changed(BaseJob source) {
        return source.isComplete() ? new JobDone(source) : new JobUndone(source);
    }

    private Integer jobId;
    private Instant occurrence;

    JobEvent(final Integer jobId, Clock clock) {
        this.jobId = jobId;
        this.occurrence = Instant.now(clock);
    }

    public Integer getJobId() {
        return jobId;
    }

    public Instant getOccurrence() {
        return occurrence;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "jobId=" + jobId +
                ", occurrence=" + occurrence +
                '}';
    }
}
