package com.example.task_2_5_hibernate.dao;

import com.example.task_2_5_hibernate.entity.Course;
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
        CourseRepository.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(value = {"course-schema.sql", "init-courses.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(value = "clear-courses.sql", executionPhase = AFTER_TEST_METHOD)
class CourseRepositoryTest {
    @Autowired
    private CourseRepository courseRepository;

    @Test
    void findAll_returnsCourses() {
        List<Course> expectedCourses = new ArrayList<>();
        expectedCourses.add(new Course(1L,"English", "desc"));
        expectedCourses.add(new Course(2L, "Math", "desc"));
        expectedCourses.add(new Course(3L, "Art", "desc"));

        List<Course> actualCourses = courseRepository.findAll();
        System.out.println(actualCourses);

        assertTrue(actualCourses.containsAll(expectedCourses));
    }

    @Test
    void findById_existingCourseId_returnsCourse() {
        Course course = courseRepository.findById(1L).orElse(null);

        assertNotNull(course);
    }

    @Test
    void findById_nonExistingCourseId_returnsNull() {
        Course course = courseRepository.findById(1000L).orElse(null);

        assertNull(course);
    }

    @Test
    void update_correctCourse_Ok() {
        Course courseBeforeUpdate = courseRepository.findById(1L).orElse(null);
        assertEquals(courseBeforeUpdate, new Course(1L, "English", "desc"));

        Course courseAfterUpdate = courseRepository.save(new Course(1L, "Physic", "desc"));
        assertEquals(courseAfterUpdate, new Course(1L, "Physic", "desc"));
    }

    @Test
    void remove_existingCourseId_Ok() {
        Course isEmptyBeforeRemoving = courseRepository.findById(1L).orElse(null);
        courseRepository.deleteById(1L);
        Course isEmptyAfterRemoving = courseRepository.findById(1L).orElse(null);

        assertNotEquals(isEmptyAfterRemoving, isEmptyBeforeRemoving);
    }

    @Test
    void save_correctCourse_Ok() {
        Course course = new Course("Physic", "desc");
        Course savedCourse = courseRepository.save(course);

        assertEquals(course, savedCourse);
    }
}