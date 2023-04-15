package com.example.task_2_5_hibernate.service;

import com.example.task_2_5_hibernate.entity.Course;
import com.example.task_2_5_hibernate.entity.Group;
import com.example.task_2_5_hibernate.entity.Student;
import com.example.task_2_5_hibernate.exception.EntityNotUpdatedException;
import com.example.task_2_5_hibernate.repository.CourseRepository;
import com.example.task_2_5_hibernate.repository.StudentRepository;
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

@SpringBootTest(classes = {StudentService.class})
class StudentServiceTest {
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private CourseRepository courseRepository;
    @Autowired
    private StudentService studentService;


    @Test
    void getAll_returnsStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1L, new Group(1L), "Dima", "Tkachuk"));

        when(studentRepository.findAll()).thenReturn(students);

        assertTrue(studentService.getAll().containsAll(students));
        verify(studentRepository).findAll();
    }

    @Test
    void getById_existId_returnsStudent() {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(new Student(1L, new Group(1L), "SS", "SS")));

        assertNotNull(studentService.getById(1L));
        verify(studentRepository).findById(1L);
    }

    @Test
    void getById_nonExistId_returnsNull() {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertNull(studentService.getById(1L));
        verify(studentRepository).findById(1L);
    }

    @Test
    void update_validStudent_returnsUpdatedStudent() {
        when(studentRepository.save(any(Student.class))).thenReturn(new Student(1L, new Group(1L), "SS", "SS"));
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(new Student(1L, new Group(1L), "SS", "SS")));

        assertNotNull(studentService.update(new Student(1L, new Group(1L), "SS", "SS")));
        verify(studentRepository).save(any(Student.class));
        verify(studentRepository).findById(any(Long.class));
    }

    @Test
    void update_invalidStudent_throwsException() {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(new Student(1L, new Group(1L), "SS", "SS")));
        when(studentRepository.save(any(Student.class))).thenThrow(IllegalArgumentException.class);

        EntityNotUpdatedException exception = assertThrows(EntityNotUpdatedException.class,
                () -> studentService.update(new Student(1L, new Group(1L), "SS", "SS")));

        assertTrue(exception.getMessage().contains("wasn`t updated"));
        verify(studentRepository).save(any(Student.class));
        verify(studentRepository).findById(any(Long.class));
    }

    @Test
    void create_validStudent_returnsSavedStudent() {
        when(studentRepository.save(any(Student.class))).thenReturn(new Student(1L, new Group(1L), "SS", "SS"));

        assertNotNull(studentService.create(new Student(1L, new Group(1L), "SS", "SS")));
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void create_invalidStudent_throwsException() {
        when(studentRepository.save(any(Student.class))).thenThrow(IllegalArgumentException.class);

        EntityNotUpdatedException exception = assertThrows(EntityNotUpdatedException.class,
                () -> studentService.create(new Student(1L, new Group(1L), "SS", "SS")));

        assertTrue(exception.getMessage().contains("wasn`t created"));
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void createAll_validStudent_returnsSavedStudent() {
        when(studentRepository.saveAll(anyList())).thenReturn(Collections.singletonList(new Student(1L, new Group(1L), "SS", "SS")));

        assertNotNull(studentService.createAll(Collections.singletonList(new Student(1L, new Group(1L), "SS", "SS"))));
        verify(studentRepository).saveAll(anyList());
    }

    @Test
    void createAll_invalidStudent_throwsException() {
        when(studentRepository.saveAll(anyList())).thenThrow(IllegalArgumentException.class);

        EntityNotUpdatedException exception = assertThrows(EntityNotUpdatedException.class,
                () -> studentService.createAll(Collections.singletonList(new Student(1L, new Group(1L), "SS", "SS"))));

        assertTrue(exception.getMessage().contains("weren`t created"));
        verify(studentRepository).saveAll(anyList());
    }

    @Test
    void delete_validStudentId_returnsDeletedStudent() {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(new Student(1L, new Group(1L), "SS", "SS")));

        assertNotNull(studentService.deleteById(1L));
        verify(studentRepository).delete(any(Student.class));
        verify(studentRepository).findById(any(Long.class));
    }

    @Test
    void delete_invalidStudentId_throwsException() {
        when(studentRepository.findById(any(Long.class))).thenThrow(IllegalArgumentException.class);

        EntityNotUpdatedException exception = assertThrows(EntityNotUpdatedException.class,
                () -> studentService.deleteById(1L));

        assertTrue(exception.getMessage().contains("wasn`t deleted"));
        verify(studentRepository).findById(any(Long.class));
    }

    @Test
    void addStudentToCourse_validIds_updateStudent() {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(new Student(1L, new Group(1L), "SS", "SS")));
        when(courseRepository.findById(any(Long.class))).thenReturn(Optional.of(new Course(1L, "English", "desc")));
        when(studentRepository.save(any(Student.class))).thenReturn(new Student(1L, new Group(1L), "SS", "SS"));

        assertDoesNotThrow(() -> studentService.addStudentToCourse(1L, 1L));

        verify(studentRepository).findById(any(Long.class));
        verify(courseRepository).findById(any(Long.class));
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void addStudentToCourse_invalidIds_throwsException() {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(new Student(1L, new Group(1L), "SS", "SS")));
        when(courseRepository.findById(any(Long.class))).thenReturn(Optional.of(new Course(1L, "English", "desc")));
        when(studentRepository.save(any(Student.class))).thenThrow(IllegalArgumentException.class);

        EntityNotUpdatedException exception = assertThrows(EntityNotUpdatedException.class,
                () -> studentService.addStudentToCourse(1L, 1L));

        assertTrue(exception.getMessage().contains("not added"));
        verify(studentRepository).findById(any(Long.class));
        verify(courseRepository).findById(any(Long.class));
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void removeStudentFromCourse_validIds_updateStudent() {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(new Student(1L, new Group(1L), "SS", "SS")));
        when(courseRepository.findById(any(Long.class))).thenReturn(Optional.of(new Course(1L, "English", "desc")));
        when(studentRepository.save(any(Student.class))).thenReturn(new Student(1L, new Group(1L), "SS", "SS"));

        assertDoesNotThrow(() -> studentService.removeStudentFromCourse(1L, 1L));

        verify(studentRepository).findById(any(Long.class));
        verify(courseRepository).findById(any(Long.class));
        verify(studentRepository).save(any(Student.class));
    }
    @Test
    void removeStudentFromCourse_invalidIds_throwsException() {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(new Student(1L, new Group(1L), "SS", "SS")));
        when(courseRepository.findById(any(Long.class))).thenReturn(Optional.of(new Course(1L, "English", "desc")));
        when(studentRepository.save(any(Student.class))).thenThrow(IllegalArgumentException.class);

        EntityNotUpdatedException exception = assertThrows(EntityNotUpdatedException.class,
                () -> studentService.removeStudentFromCourse(1L, 1L));

        assertTrue(exception.getMessage().contains("not removed"));
        verify(studentRepository).findById(any(Long.class));
        verify(courseRepository).findById(any(Long.class));
        verify(studentRepository).save(any(Student.class));
    }
}