package com.example.task_2_5_hibernate.dao;

import com.example.task_2_5_hibernate.dao.mapper.CourseMapper;
import com.example.task_2_5_hibernate.entity.Course;
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
@Sql(value = {"course-schema.sql", "init-courses.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "schema-drop.sql", executionPhase = AFTER_TEST_METHOD)
class CourseDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private CourseDao courseDao;

    @BeforeEach
    void setUp() {
        courseDao = new CourseDao(jdbcTemplate, new CourseMapper());
    }

    @Test
    void findAll_returnsCourses() {
        List<Course> expectedCourses = new ArrayList<>();
        expectedCourses.add(new Course(1, "English", "desc"));
        expectedCourses.add(new Course(2, "Math", "desc"));
        expectedCourses.add(new Course(3, "Art", "desc"));

        List<Course> actualCourses = courseDao.findAll();

        assertEquals(expectedCourses, actualCourses);
    }

    @Test
    void findById_existingCourseId_returnsCourse() {
        Course course = courseDao.findById(1).orElse(null);

        assertNotNull(course);
    }

    @Test
    void findById_nonExistingCourseId_returnsNull() {
        Course course = courseDao.findById(11).orElse(null);

        assertNull(course);
    }

    @Test
    void update_correctCourse_Ok() {
        Course updatedCourse = courseDao.findById(1).orElse(null);

        courseDao.update(new Course(1, "Physic", "desc"));
        Course actualCourse = courseDao.findById(1).orElse(null);

        assertNotEquals(updatedCourse, actualCourse);
    }

    @Test
    void remove_existingCourseId_Ok() {
        boolean isEmptyBeforeRemoving = courseDao.findById(1).isEmpty();
        courseDao.delete(1);
        boolean isEmptyAfterRemoving = courseDao.findById(1).isEmpty();

        assertFalse(isEmptyBeforeRemoving);
        assertTrue(isEmptyAfterRemoving);
    }

    @Test
    void remove_nonExistingCourseId_returnsFalse() {
        assertFalse(courseDao.delete(11));
    }

    @Test
    void save_correctCourse_Ok() {
        Course course = new Course("Physic", "desc");
        Course savedCourse = courseDao.create(course);

        assertEquals(savedCourse.getName(), course.getName());
        assertEquals(savedCourse.getDescription(), savedCourse.getDescription());
    }

    @Test
    void saveAll_correctCourses_Ok() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("Physic", "desc"));
        courses.add(new Course("Geography", "desc"));

        courseDao.createAll(courses);

        boolean isSaved = courseDao.findAll().stream()
                .map(Course::getName)
                .toList()
                .containsAll(courses.stream()
                        .map(Course::getName)
                        .toList());

        assertTrue(isSaved);
    }
}