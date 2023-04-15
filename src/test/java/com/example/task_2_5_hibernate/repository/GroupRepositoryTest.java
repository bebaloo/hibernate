package com.example.task_2_5_hibernate.repository;

import com.example.task_2_5_hibernate.entity.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        GroupRepository.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(value = {"groups-schema.sql", "init-groups.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(value = "clear-groups.sql", executionPhase = AFTER_TEST_METHOD)
class GroupRepositoryTest {
    @Autowired
    private GroupRepository groupRepository;

    @Test
    void findGroupsByStudentsNumber_returnGroups() {
        List<Group> groups = groupRepository.findGroupsByStudentsNumber(100);

        assertFalse(groups.isEmpty());
    }

    @Test
    void findAll_returnsGroups() {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(new Group(1L, "aa-11"));
        expectedGroups.add(new Group(2L, "bb-22"));
        expectedGroups.add(new Group(3L, "cc-33"));

        List<Group> actualGroups = groupRepository.findAll();

        assertTrue(actualGroups.containsAll(expectedGroups));
    }

    @Test
    void findById_existingGroupId_returnsGroup() {
        Group group = groupRepository.findById(1L).orElse(null);

        assertNotNull(group);
    }

    @Test
    void findById_nonExistingGroupId_returnsNull() {
        Group group = groupRepository.findById(11L).orElse(null);

        assertNull(group);
    }

    @Test
    void update_correctGroup_Ok() {
        Group groupBeforeUpdate = groupRepository.findById(1L).orElse(null);
        assertEquals(groupBeforeUpdate, new Group(1L, "aa-11"));

        Group groupAfterUpdate = groupRepository.save(new Group(1L, "w-11"));

        assertEquals(groupAfterUpdate, new Group(1L, "w-11"));
    }

    @Test
    void remove_existingGroupId_Ok() {
        groupRepository.deleteById(1L);
        boolean isEmptyAfterRemoving = groupRepository.findById(1L).isEmpty();

        assertTrue(isEmptyAfterRemoving);
    }

    @Test
    void save_correctGroup_Ok() {
        Group group = new Group();
        group.setName("dd-44");

        Group savedGroup = groupRepository.save(group);

        assertEquals(group, savedGroup);
    }

}