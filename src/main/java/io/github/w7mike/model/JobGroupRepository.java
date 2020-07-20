package io.github.w7mike.model;

import java.util.List;
import java.util.Optional;

public interface JobGroupRepository {

    List<JobGroup> findAll();

    Optional<JobGroup> findById(Integer id);

    JobGroup save (JobGroup entity);

    void deleteById(Integer id);

    boolean existsByCompleteIsFalseAndProject_Id(Integer projectId);

    boolean existsBySpecification(String specification);
}
