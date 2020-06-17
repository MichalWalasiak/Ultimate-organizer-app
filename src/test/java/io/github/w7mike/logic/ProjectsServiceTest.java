package io.github.w7mike.logic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectsServiceTest {

    @Test
    @DisplayName("Should throw IllegalStateException when properties allows one groups and other incomplete groups exist")
    void createGroup_NoMultipleGroupsProperties_And_IncompleteGroupsExists_throwsIllegalStateException() {
    }
}