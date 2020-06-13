package io.github.w7mike.adapter;

import io.github.w7mike.model.ProjectRepository;
import io.github.w7mike.model.Projects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SqlProjectRepository extends ProjectRepository, JpaRepository<Projects, Integer> {

    @Override
    @Query("select distinct p from Projects p join fetch p.steps")
    List<Projects> findAll();
}
