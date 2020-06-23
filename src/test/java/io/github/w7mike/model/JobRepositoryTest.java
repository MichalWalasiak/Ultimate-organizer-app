package io.github.w7mike.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

public class JobRepositoryTest implements JobRepository {
    private Map<Integer, Job> jobs = new HashMap<>();

    @Override
    public List<Job> findAll() {
        return new ArrayList<>(jobs.values());
    }

    @Override
    public Page<Job> findAll(final Pageable page) {
        return null;
    }

    @Override
    public Optional<Job> findById(final Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(final Integer id) {
        return false;
    }

    @Override
    public boolean existsByCompleteIsFalseAndJobGroups_Id(final Integer id) {
        return false;
    }

    @Override
    public List<Job> findByComplete(final boolean complete) {
        return null;
    }

    @Override
    public Job save(final Job entity) {
        return null;
    }

    @Override
    public void deleteById(final Integer id) {

    }
}
