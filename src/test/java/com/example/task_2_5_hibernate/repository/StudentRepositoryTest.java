package com.example.task_2_5_hibernate.repository;

import com.example.task_2_5_hibernate.entity.Group;
import com.example.task_2_5_hibernate.entity.Student;
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
        StudentRepository.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(value = {"student-schema.sql", "init-students.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(value = "clear-students.sql", executionPhase = AFTER_TEST_METHOD)
class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    void findAll_returnsStudents() {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(1L, new Group(1L), "Dima", "Tkachuk"));
        expectedStudents.add(new Student(2L, new Group(1L), "Yarik", "Shevchenko"));
        expectedStudents.add(new Student(3L, new Group(1L), "Olga", "Melnyk"));

        List<Student> actualStudents = studentRepository.findAll();

        assertTrue(actualStudents.containsAll(expectedStudents));
    }

    @Test
    void findById_existingStudentId_returnsStudent() {
        Student student = studentRepository.findById(1L).orElse(null);

        assertNotNull(student);
    }

    @Test
    void findById_nonExistingStudentId_returnsNull() {
        Student student = studentRepository.findById(201L).orElse(null);

        assertNull(student);
    }

    @Test
    void update_correctStudent_Ok() {
        Student studentBeforeUpdate = studentRepository.findById(1L).orElse(null);
        assertEquals(studentBeforeUpdate, new Student(1L, new Group(1L, "aa-11"), "Dima", "Tkachuk"));

        Student studentAfterUpdate = studentRepository.save(new Student(1L, new Group(1L, "aa-11"), "Igor", "Frolov"));

        assertEquals(studentAfterUpdate, new Student(1L, new Group(1L, "aa-11"), "Igor", "Frolov"));
    }

    @Test
    void remove_existingStudent_Ok() {
        studentRepository.deleteById(1L);
        boolean isEmptyAfterRemoving = studentRepository.findById(1L).isEmpty();

        assertTrue(isEmptyAfterRemoving);
    }


    @Test
    void save_correctStudent_Ok() {
        Student student = new Student(new Group(1L, "aa-11"), "Oleg", "Guk");
        Student savedStudent = studentRepository.save(student);

        assertEquals(savedStudent, student);
    }
}
