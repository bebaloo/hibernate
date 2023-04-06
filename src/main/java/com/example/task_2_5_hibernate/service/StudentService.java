package com.example.task_2_5_hibernate.service;

import com.example.task_2_5_hibernate.dao.StudentDao;
import com.example.task_2_5_hibernate.entity.Course;
import com.example.task_2_5_hibernate.entity.Student;
import com.example.task_2_5_hibernate.exception.EntityNotUpdatedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class StudentService implements EntityService<Student, Integer> {
    private final StudentDao studentDao;

    @Override
    public List<Student> getAll() {
        log.info("Getting all students");
        return studentDao.findAll();
    }

    @Override
    public Student getById(Integer id) {
        Optional<Student> student = studentDao.findById(id);

        student.ifPresentOrElse(s -> log.info("Getting " + s),
                () -> log.info("Course with id: " + id + " not found"));

        return student.orElse(null);
    }

    @Override
    public Student update(Student student) {
        Student updatedStudent;
        try {
            updatedStudent = studentDao.update(student);
            log.info(student + " was updated");
        } catch (RuntimeException e) {
            log.warn(student + " wasn`t updated");
            throw new EntityNotUpdatedException(student + " wasn`t updated");
        }

        return updatedStudent;
    }

    @Override
    public Student create(Student student) {
        try {
            Student createdStudent = studentDao.create(student);
            log.info("Create + " + student);
            return createdStudent;
        } catch (RuntimeException e) {
            log.warn(student + " wasn`t created");
            throw new EntityNotUpdatedException(student + " wasn`t created");
        }
    }

    @Override
    public void createAll(List<Student> students) {
        try {
            studentDao.createAll(students);
            log.info("Create " + students);
        } catch (RuntimeException e) {
            log.warn("Students weren`t created");
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            studentDao.delete(id);
            log.info("Delete student with id: " + id);
        } catch (RuntimeException e) {
            log.warn("Student with id: " + id + " not found");
            throw new EntityNotUpdatedException("Student wasn`t deleted");
        }
    }

    public List<Student> getStudentsByCourseName(String courseName) {
        log.info("Getting students by course name");
        return studentDao.findStudentsByCourseName(courseName);
    }

    public void addStudentToCourse(int studentId, int courseId) {
        log.info("Adding student with id: " + studentId + " to course with id: " + courseId);
        studentDao.saveStudentToCourse(studentId, courseId);
    }

    public void deleteStudentFromCourse(Student student, Course course) {
        log.info("Deleting student from course");
        studentDao.removeStudentFromCourse(student, course);
    }

    public List<Student> getStudentsByCourseId(int courseId) {
        log.info("Getting student by course id: " + courseId);
        return studentDao.findStudentsByCourseId(courseId);
    }
}
