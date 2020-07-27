package io.github.w7mike.reports;

import io.github.w7mike.model.event.JobDone;
import io.github.w7mike.model.event.JobUndone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ChangedJobEventListener {

    private final Logger logger = LoggerFactory.getLogger(ChangedJobEventListener.class);

    @EventListener
    public void on(JobDone event) {
        logger.info("Got " + event);
    }

    @EventListener
    public void on(JobUndone event) {
        logger.info("Got " + event);
    }
}
