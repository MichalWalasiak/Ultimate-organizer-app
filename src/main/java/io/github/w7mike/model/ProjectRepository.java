package io.github.w7mike.model;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {

    List<Projects> findAll();

    Optional<Projects> findById(Integer id);

    Projects save (Projects entity);

    void deleteById(Integer id);
}
