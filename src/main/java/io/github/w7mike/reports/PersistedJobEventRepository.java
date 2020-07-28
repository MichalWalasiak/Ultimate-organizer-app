package io.github.w7mike.reports;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersistedJobEventRepository extends JpaRepository<PersistedJobEvent, Integer> {
}
