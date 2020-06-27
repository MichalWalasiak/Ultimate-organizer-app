package io.github.w7mike.model;

import java.util.List;
import java.util.Optional;

public interface JobGroupsRepository {

    List<JobGroups> findAll();

    Optional<JobGroups> findById(Integer id);

    JobGroups save (JobGroups entity);

    void deleteById(Integer id);

    boolean existsByCompleteIsFalseAndProject_Id(Integer projectId);
}
