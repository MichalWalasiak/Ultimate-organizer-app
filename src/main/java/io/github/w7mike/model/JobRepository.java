package io.github.w7mike.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface JobRepository {

    List<Job> findAll();

    Page<Job> findAll(Pageable page);

    Optional<Job> findById(Integer id);

    boolean existsById(Integer id);

    boolean existsByCompleteIsFalseAndJobGroup_Id(Integer id);

    List<Job>findByComplete(boolean complete);

    Job save (Job entity);

    void deleteById(Integer id);

    List<Job> findAllByGroup_Id(Integer groupId);
}
