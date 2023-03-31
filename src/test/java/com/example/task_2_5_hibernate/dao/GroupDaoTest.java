package com.example.task_2_5_hibernate.dao;

import com.example.task_2_5_hibernate.dao.mapper.GroupMapper;
import com.example.task_2_5_hibernate.entity.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@JdbcTest
@Sql(scripts = {"groups-schema.sql", "init-groups.sql"},
        executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "schema-drop.sql", executionPhase = AFTER_TEST_METHOD)
class GroupDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private GroupDao groupDao;

    @BeforeEach
    void setUp() {
        groupDao = new GroupDao(jdbcTemplate, new GroupMapper());
    }

    @Test
    void findAll_returnsGroups() {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(new Group(1, "aa-11"));
        expectedGroups.add(new Group(2, "bb-22"));
        expectedGroups.add(new Group(3, "cc-33"));

        List<Group> actualGroups = groupDao.findAll();

        assertEquals(expectedGroups, actualGroups);
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

        groupDao.update(new Group(1, "ww-11"));
        Group actualGroup = groupDao.findById(1).orElse(null);

        assertNotEquals(updatedGroup, actualGroup);
    }

    @Test
    void remove_existingGroupId_Ok() {
        groupDao.delete(1);
        boolean isEmptyAfterRemoving = groupDao.findById(1).isEmpty();

        assertTrue(isEmptyAfterRemoving);
    }

    @Test
    void remove_nonExistingGroupId_returnsFalse() {
        assertFalse(groupDao.delete(11));
    }

    @Test
    void save_correctGroup_Ok() {
        Group group = new Group();
        group.setName("dd-44");

        Group savedGroup = groupDao.create(group);

        assertEquals(group.getName(), savedGroup.getName());
    }

    @Test
    void saveAll_correctGroups_Ok() {
        List<Group> groups = new ArrayList<>();
        groups.add(new Group("wwww"));
        groups.add(new Group("ssss"));

        groupDao.createAll(groups);

        boolean isSaved = groupDao.findAll().stream()
                .map(Group::getName)
                .toList()
                .containsAll(groups.stream()
                        .map(Group::getName)
                        .toList());

        assertTrue(isSaved);
    }
}