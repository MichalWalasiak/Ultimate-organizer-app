package io.github.w7mike.logic;


import io.github.w7mike.model.JobGroup;
import io.github.w7mike.model.JobGroupRepository;
import io.github.w7mike.model.JobRepository;
import io.github.w7mike.model.Project;
import io.github.w7mike.model.projection.GroupReadModel;
import io.github.w7mike.model.projection.GroupWriteModel;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JobGroupService {

    private JobGroupRepository groupsRepository;
    private JobRepository jobRepository;

    public JobGroupService(final JobGroupRepository groupsRepository, final JobRepository jobRepository) {
        this.groupsRepository = groupsRepository;
        this.jobRepository = jobRepository;
    }

    public GroupReadModel createGroup(final GroupWriteModel source){
        return createGroup(source, null);
    }

    public GroupReadModel createGroup(final GroupWriteModel source, final Project project) {
        JobGroup result = groupsRepository.save(source.toGroup(project));
        return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll(){
        return groupsRepository.findAll()
                .stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    public void toggleGroup(Integer groupId){
        if (jobRepository.existsByCompleteIsFalseAndJobGroup_Id(groupId)){
            throw new IllegalStateException("Group contains uncompleted jobs, please complete all jobs first");
        }

        JobGroup result = groupsRepository.findById(groupId)
                .orElseThrow(()-> new IllegalArgumentException("Group with given id does not exists"));
        result.setComplete(!result.isComplete());
        groupsRepository.save(result);
    }
}
