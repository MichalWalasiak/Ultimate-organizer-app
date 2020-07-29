package io.github.w7mike.reports;

import io.github.w7mike.model.JobRepository;
import org.springframework.stereotype.Controller;

@Controller("/reports")
public class ReportController {
    private final JobRepository repository;
    private final PersistedJobEventRepository persistedJobEventRepository;

    public ReportController(final JobRepository repository, final PersistedJobEventRepository persistedJobEventRepository) {
        this.repository = repository;
        this.persistedJobEventRepository = persistedJobEventRepository;
    }
}
