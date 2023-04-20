package com.example.task_2_5_hibernate.service;

import com.example.task_2_5_hibernate.entity.Course;
import com.example.task_2_5_hibernate.exception.EntityNotUpdatedException;
import com.example.task_2_5_hibernate.mapper.CourseMapper;
import com.example.task_2_5_hibernate.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CourseService.class)
class CourseServiceTest {
    @Autowired
    private CourseService courseService;
    @MockBean
    private CourseMapper courseMapper;
    @MockBean
    private CourseRepository courseRepository;

    @Test
    void getAll_returnsCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1L, "Name", "Desc"));
        when(courseRepository.findAll()).thenReturn(courses);

        assertEquals(courseService.getAll(), courses);
        verify(courseRepository).findAll();
    }

    @Test
    void getById_existId_returnsCourse() {
        when(courseRepository.findById(any(Long.class))).thenReturn(Optional.of(new Course(1L, "Name", "Desc")));

        assertNotNull(courseService.getById(1L));
        verify(courseRepository).findById(1L);
    }

    @Test
    void getById_nonExistId_returnsNull() {
        when(courseRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertNull(courseService.getById(1L));
        verify(courseRepository).findById(1L);
    }

    @Test
    void update_validCourse_returnsUpdatedStudent() {
        when(courseRepository.save(any(Course.class))).thenReturn(new Course(1L, "Name", "Desc"));
        when(courseRepository.findById(any(Long.class))).thenReturn(Optional.of(new Course(1L, "Name", "Desc")));

        assertNotNull(courseService.update(new Course(1L, "Name", "Desc")));
        verify(courseRepository).save(any(Course.class));
        verify(courseRepository).findById(any(Long.class));
    }

    @Test
    void update_invalidCourse_throwsException() {
        when(courseRepository.findById(any(Long.class))).thenReturn(Optional.of(new Course(1L, "Name", "Desc")));
        when(courseRepository.save(any(Course.class))).thenThrow(IllegalArgumentException.class);

        EntityNotUpdatedException exception = assertThrows(EntityNotUpdatedException.class,
                () -> courseService.update(new Course(1L, "Name", "Desc")));

        assertTrue(exception.getMessage().contains("wasn`t updated"));
        verify(courseRepository).save(any(Course.class));
        verify(courseRepository).findById(any(Long.class));
    }

    @Test
    void create_validCourse_returnsSavedStudent() {
        when(courseRepository.save(any(Course.class))).thenReturn(new Course(1L, "Name", "Desc"));

        assertNotNull(courseService.create(new Course(1L, "Name", "Desc")));
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void create_invalidCourse_throwsException() {
        when(courseRepository.save(any(Course.class))).thenThrow(IllegalArgumentException.class);

        EntityNotUpdatedException exception = assertThrows(EntityNotUpdatedException.class,
                () -> courseService.create(new Course(1L, "Name", "Desc")));

        assertTrue(exception.getMessage().contains("wasn`t created"));
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void createAll_validCourse_returnsSavedStudent() {
        when(courseRepository.saveAll(anyList())).thenReturn(Collections.singletonList(new Course(1L, "Name", "Desc")));

        assertNotNull(courseService.createAll(Collections.singletonList(new Course(1L, "Name", "Desc"))));
        verify(courseRepository).saveAll(anyList());
    }

    @Test
    void createAll_invalidCourse_throwsException() {
        when(courseRepository.saveAll(anyList())).thenThrow(IllegalArgumentException.class);

        EntityNotUpdatedException exception = assertThrows(EntityNotUpdatedException.class,
                () -> courseService.createAll(Collections.singletonList(new Course(1L, "Name", "Desc"))));

        assertTrue(exception.getMessage().contains("weren`t created"));
        verify(courseRepository).saveAll(anyList());
    }

    @Test
    void delete_validCourseId_returnsDeletedStudent() {
        when(courseRepository.findById(any(Long.class))).thenReturn(Optional.of(new Course(1L, "Name", "Desc")));

        assertNotNull(courseService.deleteById(1L));
        verify(courseRepository).delete(any(Course.class));
        verify(courseRepository).findById(any(Long.class));
    }

    @Test
    void delete_invalidCourseId_throwsException() {
        when(courseRepository.findById(any(Long.class))).thenThrow(IllegalArgumentException.class);

        EntityNotUpdatedException exception = assertThrows(EntityNotUpdatedException.class,
                () -> courseService.deleteById(1L));

        assertTrue(exception.getMessage().contains("wasn`t deleted"));
        verify(courseRepository).findById(any(Long.class));
    }
}