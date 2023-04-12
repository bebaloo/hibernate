package com.example.task_2_5_hibernate.dao;

import com.example.task_2_5_hibernate.entity.Course;
import com.example.task_2_5_hibernate.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StudentDao implements EntityDao<Student, Integer> {
    private static final String FIND_ALL_QUERY = "SELECT student FROM Student student";
    private static final String FIND_STUDENTS_BY_COURSE_NAME_QUERY =
            "SELECT course.students FROM Course course WHERE name = :name";
    private static final String FIND_STUDENTS_BY_COURSE_ID_QUERY =
            "SELECT course.students FROM Course course WHERE id = :course_id";

    @PersistenceContext
    private EntityManager entityManager;

    public List<Student> findAll() {
        return entityManager
                .createQuery(FIND_ALL_QUERY, Student.class)
                .getResultList();
    }

    @Override
    public Optional<Student> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Student.class, id));
    }

    @Override
    @Transactional
    public Student update(Student student) {
        return entityManager.merge(student);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Student student = entityManager.find(Student.class, id);
        student.getCourses().forEach(student::removeCourse);
        entityManager.remove(student);
    }

    @Override
    @Transactional
    public Student create(Student student) {
        entityManager.persist(student);
        entityManager.flush();
        return student;
    }

    @Override
    @Transactional
    public void createAll(List<Student> students) {
        students.forEach(student -> entityManager.persist(student));
    }

    public List<Student> findStudentsByCourseName(String courseName) {
        return entityManager
                .createQuery(FIND_STUDENTS_BY_COURSE_NAME_QUERY, Student.class)
                .setParameter("name", courseName)
                .getResultList();
    }

    @Transactional
    public void saveStudentToCourse(Student student, Course course) {
        student.getCourses().add(course);
        entityManager.merge(student);
    }

    @Transactional
    public void removeStudentFromCourse(Student student, Course course) {
        student.removeCourse(course);
        entityManager.merge(student);
    }

    public List<Student> findStudentsByCourseId(int courseId) {
        return entityManager
                .createQuery(FIND_STUDENTS_BY_COURSE_ID_QUERY, Student.class)
                .setParameter("course_id", courseId).getResultList();
    }
}



