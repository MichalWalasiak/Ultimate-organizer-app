package io.github.w7mike.logic;


import io.github.w7mike.model.JobGroupsRepository;
import org.springframework.stereotype.Service;

@Service
public class JobGroupsService {

    private JobGroupsRepository repository;

    public JobGroupsService(final JobGroupsRepository repository) {
        this.repository = repository;
    }
}
