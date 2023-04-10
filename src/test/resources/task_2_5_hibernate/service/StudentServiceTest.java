package task_2_5_hibernate.service;

import com.example.task_2_5_hibernate.entity.Group;
import com.example.task_2_5_hibernate.entity.Student;
import com.example.task_2_5_hibernate.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {StudentService.class})
class StudentServiceTest {
    @MockBean
    StudentDao studentDao;
    @Autowired
    StudentService studentService;

    @Test
    void add_correctStudent_Ok() {
        Student newStudent = new Student(new Group(1), "Dima", "Tkachuk");
        studentService.create(newStudent);

        verify(studentDao).create(any(Student.class));
    }

    @Test
    void delete_correctId_Ok() {
        studentService.delete(1);
        verify(studentDao).delete(any());
    }

    @Test
    void delete_incorrectId_Ok() {
        studentService.delete(210);
        verify(studentDao).delete(any(Integer.TYPE));
    }
}