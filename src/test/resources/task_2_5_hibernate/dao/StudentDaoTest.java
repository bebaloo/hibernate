package task_2_5_hibernate.dao;

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
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        StudentDao.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(value = {"student-schema.sql", "init-students.sql"}, executionPhase = BEFORE_TEST_METHOD)
@ComponentScan("com.example.task_2_5_hibernate.dao")
class StudentDaoTest {
    @Autowired
    private StudentDao studentDao;

    @Test
    void findAll_returnsStudents() {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student("Dima", "Tkachuk"));
        expectedStudents.add(new Student("Yarik", "Shevchenko"));
        expectedStudents.add(new Student("Olga", "Melnyk"));

        List<Student> actualStudents = studentDao.findAll().stream()
                .map(student -> new Student(student.getFirstName(), student.getLastName()))
                .toList();
        assertTrue(actualStudents.containsAll(expectedStudents));
    }

    @Test
    void findById_existingStudentId_returnsStudent() {
        Student student = studentDao.findById(1).orElse(null);

        assertNotNull(student);
    }

    @Test
    void findById_nonExistingStudentId_returnsNull() {
        Student student = studentDao.findById(201).orElse(null);

        assertNull(student);
    }

    @Test
    void update_correctStudent_Ok() {
        Student updatedStudent = studentDao.findById(1).orElse(null);

        Student actualStudent = studentDao.update(new Student(1, new Group(1, "aa-11"), "Igor", "Frolov"));

        assertNotEquals(updatedStudent, actualStudent);
    }

    @Test
    void remove_existingStudent_Ok() {
        studentDao.delete(1);
        boolean isEmptyAfterRemoving = studentDao.findById(1).isEmpty();

        assertTrue(isEmptyAfterRemoving);
    }


    @Test
    void save_correctStudent_Ok() {
        Student student = new Student(new Group(1, "aa-11"), "Oleg", "Guk");
        Student savedStudent = studentDao.create(student);

        assertEquals(savedStudent, student);
    }
}