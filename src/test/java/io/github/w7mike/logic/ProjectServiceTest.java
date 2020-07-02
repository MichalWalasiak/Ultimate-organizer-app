package io.github.w7mike.logic;

import io.github.w7mike.JobConfigurationProperties;
import io.github.w7mike.model.*;
import io.github.w7mike.model.projection.GroupReadModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    @Test
    @DisplayName("Should throw IllegalStateException when properties allows one groups and other incomplete groups exist")
    void createGroup_NoMultipleGroupsProperties_And_IncompleteGroupsExists_throwsIllegalStateException() {
        //given
        JobGroupRepository mockGroupRepository = groupRepositoryReturning(true);
        JobConfigurationProperties mockProperties = configurationReturning(false);

        var toTest = new ProjectsService(null, mockGroupRepository, null, mockProperties);

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
        JobConfigurationProperties mockProperties = configurationReturning(true);

        var toTest = new ProjectsService(mockRepository, null, null, mockProperties);

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
        JobGroupRepository mockGroupRepository1 = groupRepositoryReturning(true);
        JobConfigurationProperties mockProperties = configurationReturning(true);

        var toTest = new ProjectsService(mockRepository, mockGroupRepository1, null, mockProperties);

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
        var today = LocalDate.now().atStartOfDay();
        var project = projectWith("bar", Set.of(-1, -2));
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.of(project));

        InMemoryGroupRepository inMemoryGroupRepository = inMemoryGroupRepository();
        var serviceInMemoryGroupRepository = proxyGroupRepository(inMemoryGroupRepository);

        int sizeBeforeCall = inMemoryGroupRepository.count();
        JobConfigurationProperties mockProperties = configurationReturning(true);

        var toTest = new ProjectsService(mockRepository, inMemoryGroupRepository, serviceInMemoryGroupRepository, mockProperties);

        //when
        GroupReadModel outcome = toTest.createGroup(today, 1);

        //then
        assertThat(outcome.getSpecification()).isEqualTo("bar");
        assertThat(outcome.getDeadline()).isEqualTo(today.minusDays(1));
        assertThat(outcome.getJobs()).allMatch(jobs -> jobs.getSpecification().equals("foo"));
        assertThat(sizeBeforeCall + 1).isEqualTo(inMemoryGroupRepository.count());
    }

    private JobGroupService proxyGroupRepository(final InMemoryGroupRepository inMemoryGroupRepository) {
        return new JobGroupService(inMemoryGroupRepository, null);
    }

    private Project projectWith(String specification, Set<Integer> daysToDeadline){
            Set<ProjectStep> steps = daysToDeadline.stream()
                    .map(days ->{
                        var step = mock(ProjectStep.class);
                        when(step.getSpecification()).thenReturn("foo");
                        when(step.getDaysToDeadline()).thenReturn(days);
                    return step;
                    }).collect(Collectors.toSet());

            var outcome = mock(Project.class);
            when(outcome.getSpecification()).thenReturn(specification);
            when(outcome.getSteps()).thenReturn(steps);

            return outcome;
    }

    private JobGroupRepository groupRepositoryReturning(final boolean result) {
        var mockGroupRepository = mock(JobGroupRepository.class);
        when(mockGroupRepository.existsByCompleteIsFalseAndProject_Id(anyInt())).thenReturn(result);
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

    private InMemoryGroupRepository inMemoryGroupRepository(){
        return new InMemoryGroupRepository() {

        };
    }

    private static class InMemoryGroupRepository implements JobGroupRepository {

        private Integer index = 0;
        private Map<Integer, JobGroup> map = new HashMap<>();

        public int count(){
            return map.values().size();
        }

        @Override
        public List<JobGroup> findAll() {
            return new ArrayList<>(map.values());
        }

        @Override
        public Optional<JobGroup> findById(final Integer id) {
            return Optional.ofNullable(map.get(id));
        }

        @Override
        public JobGroup save(final JobGroup entity) {
            if (entity.getId() == null){
                try {
                    Field field = null;
                    try {
                        field = JobGroup.class.getDeclaredField("id");
                    } catch (NoSuchFieldException e) {
                        try {
                            field = JobGroup.class.getSuperclass().getDeclaredField("id");
                        } catch (NoSuchFieldException e2) {
                            e2.printStackTrace();
                        }
                    }
                    field.setAccessible(true);
                    field.set(entity, ++index);
                }catch (IllegalAccessException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
            map.put(entity.getId(), entity);
            return entity;
        }

        @Override
        public void deleteById(final Integer id) {

        }

        @Override
        public boolean existsByCompleteIsFalseAndProject_Id(final Integer projectId) {
            return map.values().stream()
                    .filter(jobGroups -> !jobGroups.isComplete())
                    .anyMatch(jobGroups -> jobGroups.getProject() != null && jobGroups.getProject().getId() == projectId);
        }
    }
}
