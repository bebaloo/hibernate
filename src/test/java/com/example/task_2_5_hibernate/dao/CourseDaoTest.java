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
@Sql(value = {"clear-courses.sql"}, executionPhase = AFTER_TEST_METHOD)
class CourseDaoTest {
    @Autowired
    private CourseDao courseDao;

    @Test
    void findAll_returnsCourses() {
        List<Course> expectedCourses = new ArrayList<>();
        expectedCourses.add(new Course("English", "desc"));
        expectedCourses.add(new Course("Math", "desc"));
        expectedCourses.add(new Course("Art", "desc"));

        List<Course> actualCourses = courseDao.findAll()
                .stream()
                .map(course -> new Course(course.getName(), course.getDescription()))
                .toList();


        assertTrue(actualCourses.containsAll(expectedCourses));
    }

    @Test
    void findById_existingCourseId_returnsCourse() {
        Course course = courseDao.findById(1).orElse(null);

        assertNotNull(course);
    }

    @Test
    void findById_nonExistingCourseId_returnsNull() {
        Course course = courseDao.findById(1000).orElse(null);

        assertNull(course);
    }

    @Test
    void update_correctCourse_Ok() {
        Course courseBeforeUpdate = courseDao.findById(1).orElse(null);
        assertEquals(courseBeforeUpdate, new Course(1, "English", "desc"));

        Course courseAfterUpdate = courseDao.update(new Course(1, "Physic", "desc"));
        assertEquals(courseAfterUpdate, new Course(1, "Physic", "desc"));
    }

    @Test
    void remove_existingCourseId_Ok() {
        Course isEmptyBeforeRemoving = courseDao.findById(1).orElse(null);
        courseDao.delete(1);
        Course isEmptyAfterRemoving = courseDao.findById(1).orElse(null);

        assertNotEquals(isEmptyAfterRemoving, isEmptyBeforeRemoving);
    }

    @Test
    void save_correctCourse_Ok() {
        Course course = new Course("Physic", "desc");
        Course savedCourse = courseDao.create(course);

        assertEquals(course, savedCourse);
    }
}