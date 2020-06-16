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

    public GroupReadModel createGroup(final GroupWriteModel source){
        JobGroups result = repository.save(source.toGroup());
        return new GroupReadModel(result);
    }

    public Set<GroupReadModel> readAll(){
        return repository.findAll()
                .stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toSet());
    }

    public void toggleGroup(Integer groupId){
        if (jobRepository.existsByCompleteIsFalseAndJobGroups_Id(groupId)){
            throw new IllegalStateException("Group contains uncompleted jobs, please complete all jobs first");
        }

        JobGroups result = repository.findById(groupId)
                .orElseThrow(()-> new IllegalArgumentException("Group with given id does not exists"));

    }





}
