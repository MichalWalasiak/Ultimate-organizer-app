package io.github.w7mike.logic;

import io.github.w7mike.model.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JobGroupsServiceTest {

    @Test
    @DisplayName("Should Throw IllegalStateException when incomplete jobs exists")
    void toggleGroup_uncompletedJobs_throwsIllegalStateException(){
        //given
        var mockJobRepository = mock(JobRepository.class);
        when(mockJobRepository.existsByCompleteIsFalseAndJobGroups_Id(anyInt())).thenReturn(true);

        var toTest = new JobGroupsService(null, mockJobRepository);
        //when
        var exception = catchThrowable(()-> toTest.toggleGroup(1));

        //then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("contains uncompleted jobs");

    }
}