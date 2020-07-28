package io.github.w7mike.reports;

import io.github.w7mike.model.event.JobEvent;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "job_events")
public class PersistedJobEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    Integer jobId;
    String name;
    LocalDateTime occurrence;

    public PersistedJobEvent() {
    }

    public PersistedJobEvent(JobEvent source) {
        jobId = source.getJobId();
        name = source.getClass().getSimpleName();
        occurrence = LocalDateTime.ofInstant(source.getOccurrence(), ZoneId.systemDefault());
    }
}
