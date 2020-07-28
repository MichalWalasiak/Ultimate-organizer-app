package io.github.w7mike.reports;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersistedJobEventRepository extends JpaRepository<PersistedJobEvent, Integer> {
    List<PersistedJobEvent> findByJobId(Integer jobId);
}
