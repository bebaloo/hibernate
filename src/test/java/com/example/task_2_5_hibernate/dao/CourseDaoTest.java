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
        CourseDao.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(value = {"course-schema.sql", "init-courses.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "schema-drop.sql", executionPhase = AFTER_TEST_METHOD)
class CourseDaoTest {
    @Autowired
    private CourseDao courseDao;

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
        Course course = courseDao.findById(1);

        assertNotNull(course);
    }

    @Test
    void findById_nonExistingCourseId_returnsNull() {
        Course course = courseDao.findById(11);

        assertNull(course);
    }

    @Test
    void update_correctCourse_Ok() {
        Course updatedCourse = courseDao.findById(1);

        courseDao.update(new Course(1, "Physic", "desc"));
        Course actualCourse = courseDao.findById(1);

        assertNotEquals(updatedCourse, actualCourse);
    }

    @Test
    void remove_existingCourseId_Ok() {
        Course isEmptyBeforeRemoving = courseDao.findById(1);
        courseDao.delete(1);
        Course isEmptyAfterRemoving = courseDao.findById(1);

        assertEquals(isEmptyAfterRemoving, isEmptyBeforeRemoving);
    }

    @Test
    void remove_nonExistingCourseId_returnsFalse() {

    }

    @Test
    void save_correctCourse_Ok() {
        /*Course course = new Course("Physic", "desc");
        Course savedCourse = courseDao.create(course);

        assertEquals(savedCourse.getName(), course.getName());
        assertEquals(savedCourse.getDescription(), savedCourse.getDescription());*/
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