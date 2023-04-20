package com.example.task_2_5_hibernate.service;

import com.example.task_2_5_hibernate.entity.Group;
import com.example.task_2_5_hibernate.exception.EntityNotUpdatedException;
import com.example.task_2_5_hibernate.mapper.GroupMapper;
import com.example.task_2_5_hibernate.repository.GroupRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = GroupService.class)
class GroupServiceTest {
    @Autowired
    private GroupService groupService;
    @MockBean
    private GroupRepository groupRepository;
    @MockBean
    private GroupMapper groupMapper;

    @Test
    void getAll_returnsCourses() {
        List<Group> groups = new ArrayList<>();
        groups.add(new Group(1L, "aa-11"));
        when(groupRepository.findAll()).thenReturn(groups);

        assertEquals(groupService.getAll(), groups);
        verify(groupRepository).findAll();
    }

    @Test
    void getById_existId_returnsCourse() {
        when(groupRepository.findById(any(Long.class))).thenReturn(Optional.of(new Group(1L, "aa-11")));

        assertNotNull(groupService.getById(1L));
        verify(groupRepository).findById(1L);
    }

    @Test
    void getById_nonExistId_returnsNull() {
        when(groupRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertNull(groupService.getById(1L));
        verify(groupRepository).findById(1L);
    }

    @Test
    void update_validGroup_returnsUpdatedStudent() {
        when(groupRepository.save(any(Group.class))).thenReturn(new Group(1L, "aa-11"));
        when(groupRepository.findById(any(Long.class))).thenReturn(Optional.of(new Group(1L, "aa-11")));

        assertNotNull(groupService.update(new Group(1L, "aa-11")));
        verify(groupRepository).save(any(Group.class));
        verify(groupRepository).findById(any(Long.class));
    }

    @Test
    void update_invalidGroup_throwsException() {
        when(groupRepository.findById(any(Long.class))).thenReturn(Optional.of(new Group(1L, "aa-11")));
        when(groupRepository.save(any(Group.class))).thenThrow(IllegalArgumentException.class);

        EntityNotUpdatedException exception = assertThrows(EntityNotUpdatedException.class,
                () -> groupService.update(new Group(1L, "aa-11")));

        assertTrue(exception.getMessage().contains("wasn`t updated"));
        verify(groupRepository).save(any(Group.class));
        verify(groupRepository).findById(any(Long.class));
    }

    @Test
    void create_validGroup_returnsSavedStudent() {
        when(groupRepository.save(any(Group.class))).thenReturn(new Group(1L, "aa-11"));

        assertNotNull(groupService.create(new Group(1L, "aa-11")));
        verify(groupRepository).save(any(Group.class));
    }

    @Test
    void create_invalidGroup_throwsException() {
        when(groupRepository.save(any(Group.class))).thenThrow(IllegalArgumentException.class);

        EntityNotUpdatedException exception = assertThrows(EntityNotUpdatedException.class,
                () -> groupService.create(new Group(1L, "aa-11")));

        assertTrue(exception.getMessage().contains("wasn`t created"));
        verify(groupRepository).save(any(Group.class));
    }

    @Test
    void createAll_validGroup_returnsSavedStudent() {
        when(groupRepository.saveAll(anyList())).thenReturn(Collections.singletonList(new Group(1L, "aa-11")));

        assertNotNull(groupService.createAll(Collections.singletonList(new Group(1L, "aa-11"))));
        verify(groupRepository).saveAll(anyList());
    }

    @Test
    void createAll_invalidGroup_throwsException() {
        when(groupRepository.saveAll(anyList())).thenThrow(IllegalArgumentException.class);

        EntityNotUpdatedException exception = assertThrows(EntityNotUpdatedException.class,
                () -> groupService.createAll(Collections.singletonList(new Group(1L, "aa-11"))));

        assertTrue(exception.getMessage().contains("weren`t created"));
        verify(groupRepository).saveAll(anyList());
    }

    @Test
    void delete_validGroupId_returnsDeletedStudent() {
        when(groupRepository.findById(any(Long.class))).thenReturn(Optional.of(new Group(1L, "aa-11")));

        assertNotNull(groupService.deleteById(1L));
        verify(groupRepository).delete(any(Group.class));
        verify(groupRepository).findById(any(Long.class));
    }

    @Test
    void delete_invalidGroupId_throwsException() {
        when(groupRepository.findById(any(Long.class))).thenThrow(IllegalArgumentException.class);

        EntityNotUpdatedException exception = assertThrows(EntityNotUpdatedException.class,
                () -> groupService.deleteById(1L));

        assertTrue(exception.getMessage().contains("wasn`t deleted"));
        verify(groupRepository).findById(any(Long.class));
    }
}