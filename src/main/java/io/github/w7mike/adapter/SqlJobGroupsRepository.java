package io.github.w7mike.adapter;

import io.github.w7mike.model.JobGroups;
import io.github.w7mike.model.JobGroupsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SqlJobGroupsRepository extends JobGroupsRepository, JpaRepository<JobGroups, Integer> {

    @Override
    @Query("select distinct g from JobGroups g join fetch g.jobs")
    List<JobGroups> findAll();

    @Override
    boolean existsByCompleteIsFalseAndProjects_Id(Integer projectId);
}
