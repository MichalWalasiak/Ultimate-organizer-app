package io.github.w7mike.logic;

import io.github.w7mike.JobConfigurationProperties;
import io.github.w7mike.model.JobGroupsRepository;
import io.github.w7mike.model.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ProjectsServiceTest {

    @Test
    @DisplayName("Should throw IllegalStateException when properties allows one groups and other incomplete groups exist")
    void createGroup_NoMultipleGroupsProperties_And_IncompleteGroupsExists_throwsIllegalStateException() {
        //given
        JobGroupsRepository mockGroupRepository = groupRepositoryReturning(true);
        //and
        JobConfigurationProperties mockProperties = configurationReturning(false);
        //System Under Test
        var toTest = new ProjectsService(null, mockGroupRepository, mockProperties);

        //when
        var exception = catchThrowable(()-> toTest.createGroup(LocalDateTime.now(), 0));

        //then

        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("one incomplete group");

    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when configuration ok and no projects exists with given id")
    void createGroup_ConfigurationOk_And_noProjects_throwsIllegalArgumentException() {
        //given
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //and
        JobConfigurationProperties mockProperties = configurationReturning(true);
        //System Under Test
        var toTest = new ProjectsService(mockRepository, null, mockProperties);

        //when
        var exception = catchThrowable(()-> toTest.createGroup(LocalDateTime.now(), 0));

        //then

        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Id do not exists");

    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when configuration allows 1 group and no groups and projects exists with given id")
    void createGroup_NoMultipleGroupsProperties_And_noIncompleteGroups_And_NoProjects_throwsIllegalArgumentException() {
        //given
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //and
        JobGroupsRepository mockGroupRepository1 = groupRepositoryReturning(true);
        //and
        JobConfigurationProperties mockProperties = configurationReturning(true);
        //System Under Test
        var toTest = new ProjectsService(mockRepository, mockGroupRepository1, mockProperties);

        //when
        var exception = catchThrowable(()-> toTest.createGroup(LocalDateTime.now(), 0));

        //then

        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Id do not exists");

    }


    @Test
    @DisplayName("should create brand new group from project")
    void createGroup_configurationOk_projectsExists_createNewGroup(){
        //given
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //and
        JobConfigurationProperties mockProperties = configurationReturning(true);

    }


    private JobGroupsRepository groupRepositoryReturning(final boolean result) {
        var mockGroupRepository = mock(JobGroupsRepository.class);
        when(mockGroupRepository.existsByCompleteIsFalseAndProjects_Id(anyInt())).thenReturn(result);
        return mockGroupRepository;
    }

    private JobConfigurationProperties configurationReturning(final boolean result) {
        var mockTemplate = mock(JobConfigurationProperties.Template.class);
        when(mockTemplate.isAllowMultipleJobs()).thenReturn(result);
        //and
        var mockProperties = mock(JobConfigurationProperties.class);
        when(mockProperties.getTemplate()).thenReturn(mockTemplate);
        return mockProperties;
    }
}