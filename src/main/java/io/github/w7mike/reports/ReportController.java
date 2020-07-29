package io.github.w7mike.reports;

import io.github.w7mike.model.Job;
import io.github.w7mike.model.JobRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/reports")
public class ReportController {
    private final JobRepository repository;
    private final PersistedJobEventRepository persistedJobEventRepository;

    public ReportController(final JobRepository repository, final PersistedJobEventRepository persistedJobEventRepository) {
        this.repository = repository;
        this.persistedJobEventRepository = persistedJobEventRepository;
    }

    @GetMapping("/count/{id}")
    ResponseEntity<JobWithChangesCount> readJobWithCount(@PathVariable Integer id) {
        return repository.findById(id)
                .map(job -> new JobWithChangesCount(job, persistedJobEventRepository.findByJobId(id)))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private static class JobWithChangesCount {

        public String specification;
        public boolean complete;
        public int changesCounter;

        public JobWithChangesCount(final Job job, final List<PersistedJobEvent> events) {
            specification = job.getSpecification();
            complete = job.isComplete();
            changesCounter = events.size();
        }
    }
}
