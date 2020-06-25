package io.github.w7mike;

import io.github.w7mike.model.Job;
import io.github.w7mike.model.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.*;

@Configuration
class TestConfiguration {

    @Bean
    @Primary
    @Profile("!integration")
    DataSource e2eTestDataSource(){
        var outcome = new DriverManagerDataSource("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
        outcome.setDriverClassName("org.h2.Driver");
        return outcome;
    }

    @Bean
    @Primary
    @Profile("integration")
    JobRepository testRepository(){
        return new JobRepository() {
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
                return Optional.ofNullable(jobs.get(id));
            }

            @Override
            public boolean existsById(final Integer id) {
                return jobs.containsKey(id);
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
                int key = jobs.size() + 1;
                jobs.put(key, entity);
                return jobs.get(key);
            }

            @Override
            public void deleteById(final Integer id) {

            }
        };
    }
}
