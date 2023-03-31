package com.example.task_2_5_hibernate.dao;

import com.example.task_2_5_hibernate.dao.mapper.StudentMapper;
import com.example.task_2_5_hibernate.entity.Group;
import com.example.task_2_5_hibernate.entity.Student;
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
@Sql(scripts = {
        "course-schema.sql",
        "student-schema.sql",
        "course_student-schema.sql",
        "init-courses.sql",
        "init-students.sql",
        "init-student_course.sql"
}, executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "schema-drop.sql", executionPhase = AFTER_TEST_METHOD)
class StudentCourseDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private StudentCourseDao studentCourseDao;

    @BeforeEach
    void setUp() {
        studentCourseDao = new StudentCourseDao(jdbcTemplate, new StudentMapper());
    }

    @Test
    void findStudentsByCourseName_existingCourseName_returnsStudents() {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(2, new Group(1, "aa-11"), "Yarik", "Shevchenko"));
        expectedStudents.add(new Student(3, new Group(1, "aa-11"), "Olga", "Melnyk"));

        List<Student> actualStudents = studentCourseDao.findStudentsByCourseId(2);

        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void findStudentsByCourseName_nonExistingCourseName_returnsEmptyList() {
        boolean isEmpty = studentCourseDao.findStudentsByCourseId(3).isEmpty();

        assertTrue(isEmpty);
    }

    @Test
    void saveStudentToCourse_correctStudentIdAndCourseId_Ok() {
        studentCourseDao.saveStudentToCourse(3, 3);
        List<Student> studentsAfterSaving = studentCourseDao.findStudentsByCourseId(3).stream()
                .map(student -> new Student(student.getFirstName(), student.getLastName()))
                .toList();

        boolean isSaved = studentsAfterSaving.contains(new Student("Olga", "Melnyk"));

        assertTrue(isSaved);
    }


    @Test
    void saveStudentToCourse_incorrectStudentIdAndCourseId_throwsException() {
        Exception exception = assertThrows(RuntimeException.class, () ->
            studentCourseDao.saveStudentToCourse(210, 3)
        );

        String expectedMessage = "PreparedStatementCallback; SQL [INSERT INTO students_courses(student_id, course_id) VALUES (?, ?)];";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }
    @Test
    void removeStudentFromCourse_correctCourseIdAndStudentId_Ok() {
        studentCourseDao.removeStudentFromCourse(1, 1);
        List<Student> studentsAfterRemoving = studentCourseDao.findStudentsByCourseId(1);

        assertTrue(studentsAfterRemoving.isEmpty());
    }
    @Test
    void removeStudentFromCourse_incorrectCourseIdAndStudentId_doNothing() {
        List<Student> beforeRemovingStudents = studentCourseDao.findStudentsByCourseId(3);
        studentCourseDao.removeStudentFromCourse(200, 3);
        List<Student> afterRemovingStudents = studentCourseDao.findStudentsByCourseId(3);

        assertEquals(beforeRemovingStudents, afterRemovingStudents);
    }
}