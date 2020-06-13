package io.github.w7mike.logic;

import io.github.w7mike.model.Job;
import io.github.w7mike.model.JobGroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TempService {

    @Autowired
    List<String> temp (JobGroupsRepository repository){
        return repository.findAll().stream()
                .flatMap(jobGroups -> jobGroups.getJobs().stream())
                .map(Job::getSpecification)
                .collect(Collectors.toList());
    }
}
