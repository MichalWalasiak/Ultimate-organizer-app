package io.github.w7mike.logic;


import io.github.w7mike.model.JobGroups;
import io.github.w7mike.model.JobGroupsRepository;
import io.github.w7mike.model.JobRepository;
import io.github.w7mike.model.projection.GroupReadModel;
import io.github.w7mike.model.projection.GroupWriteModel;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JobGroupsService {

    private JobGroupsRepository repository;
    private JobRepository jobRepository;

    public JobGroupsService(final JobGroupsRepository repository, final JobRepository jobRepository) {
        this.repository = repository;
        this.jobRepository = jobRepository;
    }

    public GroupReadModel createGroup(GroupWriteModel source){
        JobGroups result = repository.save(source.toGroup());
        return new GroupReadModel(result);
    }

    public Set<GroupReadModel> readAll(){
        return repository.findAll()
                .stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toSet());
    }





}
