package com.example.task_2_5_hibernate.dao;

import com.example.task_2_5_hibernate.dao.mapper.StudentMapper;
import com.example.task_2_5_hibernate.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StudentCourseDao {
    private static final String FIND_STUDENTS_BY_COURSE_NAME_QUERY = "SELECT s.student_id, s.group_id, first_name, last_name, group_name FROM students_courses AS s_c INNER JOIN courses c on c.course_id = s_c.course_id INNER JOIN students s on s_c.student_id = s.student_id INNER JOIN groups g ON g.group_id = s.group_id WHERE s_c.course_name = ?";
    private static final String FIND_STUDENTS_BY_COURSE_ID_QUERY = "SELECT s.student_id, s.group_id, first_name, last_name, group_name FROM students_courses AS s_c INNER JOIN courses c on c.course_id = s_c.course_id INNER JOIN students s on s_c.student_id = s.student_id INNER JOIN groups g ON g.group_id = s.group_id WHERE s_c.course_id = ?";
    private static final String ADD_STUDENT_TO_COURSE_QUERY = "INSERT INTO students_courses(student_id, course_id) VALUES (?, ?)";
    private static final String REMOVE_STUDENT_FROM_COURSE_QUERY = "DELETE FROM students_courses WHERE student_id = ? AND course_id = ?";
    private static final String REMOVE_STUDENT_COURSE_RECORD_QUERY = "DELETE FROM students_courses WHERE student_id = ?";
    private final JdbcTemplate jdbcTemplate;
    private final StudentMapper studentMapper;

    public List<Student> findStudentsByCourseName(String courseName) {
        return jdbcTemplate.query(FIND_STUDENTS_BY_COURSE_NAME_QUERY, studentMapper, courseName);
    }

    public boolean saveStudentToCourse(int studentId, int courseId) {
        int isSaved = jdbcTemplate.update(ADD_STUDENT_TO_COURSE_QUERY, studentId, courseId);
        return isSaved != 0;
    }

    public boolean removeStudentFromCourse(int studentId, int courseId) {
        int isRemoved = jdbcTemplate.update(REMOVE_STUDENT_FROM_COURSE_QUERY, studentId, courseId);
        return isRemoved != 0;
    }

    public boolean removeStudentCourse(int studentId) {
        int isRemoved = jdbcTemplate.update(REMOVE_STUDENT_COURSE_RECORD_QUERY, studentId);
        return isRemoved != 0;
    }

    public List<Student> findStudentsByCourseId(int courseId) {
        return jdbcTemplate.query(FIND_STUDENTS_BY_COURSE_ID_QUERY, studentMapper, courseId);
    }
}
