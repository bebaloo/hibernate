package com.example.task_2_5_hibernate.service;

import com.example.task_2_5_hibernate.dao.CourseRepository;
import com.example.task_2_5_hibernate.dao.StudentRepository;
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
public class StudentService implements EntityService<Student, Long> {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Override
    public List<Student> getAll() {
        log.info("Getting all students");
        return studentRepository.findAll();
    }

    @Override
    public Student getById(Long id) {
        Optional<Student> student = studentRepository.findById(id);

        student.ifPresentOrElse(s -> log.info("Getting " + s),
                () -> log.info("Course with id: " + id + " not found"));

        return student.orElse(null);
    }

    @Override
    public Student update(Student student) {
        try {
            return studentRepository.findById(student.getId())
                    .map(foundStudent -> {
                        foundStudent.setFirstName(student.getFirstName());
                        foundStudent.setLastName(student.getLastName());
                        foundStudent.setGroup(student.getGroup());
                        foundStudent.setCourses(student.getCourses());

                        log.info(student + "was updated");

                        return studentRepository.save(foundStudent);
                    })
                    .orElseThrow(EntityNotUpdatedException::new);
        } catch (RuntimeException e) {
            log.warn(student + " wasn`t updated");
            throw new EntityNotUpdatedException(student + " wasn`t updated");
        }
    }

    @Override
    public Student create(Student student) {
        try {
            Student createdStudent = studentRepository.save(student);
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
            studentRepository.saveAll(students);
            log.info("Create " + students);
        } catch (RuntimeException e) {
            log.warn("Students weren`t created");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Student student = studentRepository.findById(id).orElseThrow(EntityNotUpdatedException::new);
            studentRepository.delete(student);

            log.info("Delete student with id: " + id);
        } catch (RuntimeException e) {
            log.warn("Student with id: " + id + " not found");
            throw new EntityNotUpdatedException("Student wasn`t deleted");
        }
    }

    public List<Student> getStudentsByCourseName(String courseName) {
        log.info("Getting students by course name");
        return studentRepository.findStudentsByCourseName(courseName);
    }

    public void addStudentToCourse(Long studentId, Long courseId) {
        try {
            Student student = studentRepository.findById(studentId).orElseThrow(EntityNotUpdatedException::new);
            Course course = courseRepository.findById(courseId).orElseThrow(EntityNotUpdatedException::new);

            student.getCourses().add(course);
            studentRepository.save(student);

            log.info("Adding student with id: " + studentId + " to course with id: " + courseId);
        } catch (RuntimeException e) {
            log.warn("Student with id: " + studentId + " not added to course with id: " + courseId);
            throw new EntityNotUpdatedException("Student was not added to course");
        }
    }

    public void removeStudentFromCourse(Long studentId, Long courseId) {
        try {
            Student student = studentRepository.findById(studentId).orElseThrow(EntityNotUpdatedException::new);
            Course course = courseRepository.findById(courseId).orElseThrow(EntityNotUpdatedException::new);

            student.getCourses().remove(course);
            studentRepository.save(student);

            log.info("Deleting student from course");
        } catch (RuntimeException e) {
            log.warn("Student with id: " + studentId + " not removed from course with id: " + courseId);
            throw new EntityNotUpdatedException("Student was not removed from course");
        }
    }

    public List<Student> getStudentsByCourseId(Long courseId) {
        log.info("Getting student by course id: " + courseId);
        return studentRepository.findStudentsByCourseId(courseId);
    }
}
