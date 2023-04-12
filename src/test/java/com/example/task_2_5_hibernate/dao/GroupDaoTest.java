package com.example.task_2_5_hibernate.dao;

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
        GroupDao.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(value = {"groups-schema.sql", "init-groups.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(value = "clear-groups.sql", executionPhase = AFTER_TEST_METHOD)
class GroupDaoTest {
    @Autowired
    private GroupDao groupDao;

    @Test
    void findAll_returnsGroups() {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(new Group("aa-11"));
        expectedGroups.add(new Group("bb-22"));
        expectedGroups.add(new Group("cc-33"));

        List<Group> actualGroups = groupDao.findAll()
                .stream()
                .map(group -> new Group(group.getName()))
                .toList();

        assertTrue(actualGroups.containsAll(expectedGroups));
    }

    @Test
    void findById_existingGroupId_returnsGroup() {
        Group group = groupDao.findById(1).orElse(null);

        assertNotNull(group);
    }

    @Test
    void findById_nonExistingGroupId_returnsNull() {
        Group group = groupDao.findById(11).orElse(null);

        assertNull(group);
    }

    @Test
    void update_correctGroup_Ok() {
        Group updatedGroup = groupDao.findById(1).orElse(null);
        assertEquals(updatedGroup, new Group(1, "aa-11"));

        Group actualGroup = groupDao.update(new Group(1, "w-11"));

        assertEquals(actualGroup, new Group(1, "w-11"));
    }

    @Test
    void remove_existingGroupId_Ok() {
        groupDao.delete(1);
        boolean isEmptyAfterRemoving = groupDao.findById(1).isEmpty();

        assertTrue(isEmptyAfterRemoving);
    }

    @Test
    void save_correctGroup_Ok() {
        Group group = new Group();
        group.setName("dd-44");

        Group savedGroup = groupDao.create(group);

        assertEquals(group, savedGroup);
    }
}