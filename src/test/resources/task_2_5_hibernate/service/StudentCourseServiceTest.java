package task_2_5_hibernate.service;

import com.example.task_2_5_hibernate.dao.StudentCourseDao;
import com.example.task_2_5_hibernate.entity.Group;
import com.example.task_2_5_hibernate.entity.Student;
import com.example.task_2_5_hibernate.service.StudentCourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {StudentCourseService.class})
class StudentCourseServiceTest {
    @MockBean
    StudentCourseDao studentCourseDao;
    @Autowired
    private StudentCourseService studentCourseService;

    @Test
    void getStudentsByCourseName_correctCourseName_returnsStudents() {
        Student student = new Student(1,  new Group(1, "aa-11"), "Dima", "Tkachuk");
        List<Student> students = new ArrayList<>();
        students.add(student);

        when(studentCourseDao.findStudentsByCourseId(any(Integer.TYPE))).thenReturn(students);

        assertTrue(studentCourseService.getStudentsByCourseId(3).contains(student));
        verify(studentCourseDao).findStudentsByCourseId(any(Integer.TYPE));
    }

    @Test
    void addStudentToCourse_correctStudentIdAndCourseId_Ok() {
        studentCourseService.addStudentToCourse(3, 3);

        verify(studentCourseDao).saveStudentToCourse(any(Integer.TYPE), any(Integer.TYPE));
    }

    @Test
    void deleteStudentFromCourse_correctStudentIdAndCourseId_Ok() {
        studentCourseService.deleteStudentFromCourse(3, 3);

        verify(studentCourseDao).removeStudentFromCourse(any(Integer.TYPE), any(Integer.TYPE));
    }
}