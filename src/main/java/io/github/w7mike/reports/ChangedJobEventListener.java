package io.github.w7mike.reports;

import io.github.w7mike.model.event.JobDone;
import io.github.w7mike.model.event.JobEvent;
import io.github.w7mike.model.event.JobUndone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ChangedJobEventListener {

    private final Logger logger = LoggerFactory.getLogger(ChangedJobEventListener.class);

    private final PersistedJobEventRepository repository;

    public ChangedJobEventListener(final PersistedJobEventRepository repository) {
        this.repository = repository;
    }

    @Async
    @EventListener
    public void on(JobDone event) {
        onChanged(event);
    }

    @Async
    @EventListener
    public void on(JobUndone event) {
        onChanged(event);
    }

    private void onChanged(final JobEvent event) {
        logger.info("Got " + event);
        repository.save(new PersistedJobEvent(event));
    }
}
