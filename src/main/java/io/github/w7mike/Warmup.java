package io.github.w7mike;

import io.github.w7mike.model.Job;
import io.github.w7mike.model.JobGroup;
import io.github.w7mike.model.JobGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Warmup implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(Warmup.class);

    private final JobGroupRepository groupRepository;

    public Warmup(final JobGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        logger.info("Application warm up after context refreshed");
        final String specification = "ApplicationContextEvent";
        if(!groupRepository.existsBySpecification(specification)){
            logger.info("No required group found! Adding it!");
            var group = new JobGroup();
            group.setSpecification(specification);
            group.setJobs(Set.of(
                    new Job("ContextStartedEvent", null, group),
                    new Job("ContextRefreshedEvent", null, group),
                    new Job("ContextStoppedEvent", null, group),
                    new Job("ContextClosedEvent", null, group)

            ));
            groupRepository.save(group);
        }
    }
}
