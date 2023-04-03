package com.example.task_2_5_hibernate.dao;

import com.example.task_2_5_hibernate.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StudentCourseDao {
    private static final String FIND_STUDENTS_BY_COURSE_NAME_QUERY = "SELECT s.student_id, s.group_id, first_name, last_name, group_name FROM students_courses AS s_c INNER JOIN courses c on c.course_id = s_c.course_id INNER JOIN students s on s_c.student_id = s.student_id INNER JOIN groups g ON g.group_id = s.group_id WHERE c.course_name = :name";
    private static final String FIND_STUDENTS_BY_COURSE_ID_QUERY = "SELECT s.student_id, s.group_id, first_name, last_name, group_name FROM students_courses AS s_c INNER JOIN courses c on c.course_id = s_c.course_id INNER JOIN students s on s_c.student_id = s.student_id INNER JOIN groups g ON g.group_id = s.group_id WHERE s_c.course_id = :course_id";
    private static final String ADD_STUDENT_TO_COURSE_QUERY = "INSERT INTO students_courses(student_id, course_id) VALUES (:student_id, :course_id)";
    private static final String REMOVE_STUDENT_FROM_COURSE_QUERY = "DELETE FROM students_courses WHERE student_id = :student_id AND course_id = :course_id";
    private static final String REMOVE_STUDENT_COURSE_RECORD_QUERY = "DELETE FROM students_courses WHERE student_id = :student_id";
    @PersistenceContext
    private EntityManager entityManager;

    public List<Student> findStudentsByCourseName(String courseName) {
        return entityManager.createNativeQuery(FIND_STUDENTS_BY_COURSE_NAME_QUERY)
                .setParameter("name", courseName)
                .getResultList();
    }

    public void saveStudentToCourse(int studentId, int courseId) {
       entityManager.createNativeQuery(ADD_STUDENT_TO_COURSE_QUERY)
               .setParameter("student_id", studentId)
               .setParameter("course_id", courseId);
    }

    public void removeStudentFromCourse(int studentId, int courseId) {
       entityManager.createNativeQuery(REMOVE_STUDENT_FROM_COURSE_QUERY)
               .setParameter("student_id", studentId)
               .setParameter("course_id", courseId);
    }

    public void removeStudentCourse(int studentId) {
        entityManager.createNativeQuery(REMOVE_STUDENT_COURSE_RECORD_QUERY)
                .setParameter("student_id", studentId);
    }

    public List<Student> findStudentsByCourseId(int courseId) {
        return entityManager.createNativeQuery(FIND_STUDENTS_BY_COURSE_ID_QUERY)
                .setParameter("course_id", courseId).getResultList();
    }
}
