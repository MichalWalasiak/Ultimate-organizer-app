package io.github.w7mike.model.projection;

import io.github.w7mike.model.Job;
import io.github.w7mike.model.JobGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GroupReadModelTest {

    @Test
    @DisplayName("should create null deadline for jobs where no deadline is given")
    void constructor_noDeadline_createNullDeadline() {
        // given
        var source = new JobGroup();
        source.setSpecification("foo");
        source.setJobs(Set.of(new Job("bar", null)));

        // when
        var outcome = new GroupReadModel(source);

        // then

    }

}