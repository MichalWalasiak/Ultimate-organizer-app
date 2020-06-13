package io.github.w7mike.adapter;

import io.github.w7mike.model.Job;
import io.github.w7mike.model.JobRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
interface SqlJobRepository extends JobRepository, JpaRepository<Job, Integer> {

    @Override
    @Query(nativeQuery = true, value = "select count(*) > 0 from jobs where id=:id")
    boolean existsById(@Param("id") Integer id);

    @Override
    boolean existsByCompleteIsFalseAndJobGroups_Id(Integer id);
}
