package com.example.task_2_5_hibernate.dao;

import com.example.task_2_5_hibernate.dao.mapper.StudentMapper;
import com.example.task_2_5_hibernate.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StudentDao implements EntityDao<Student, Integer> {
    private static final String FIND_ALL_QUERY = "SELECT student_id, groups.group_id, group_name, first_name, last_name FROM students INNER JOIN groups ON groups.group_id = students.group_id";
    private static final String FIND_BY_ID_QUERY = "SELECT student_id, groups.group_id, group_name, first_name, last_name FROM students INNER JOIN groups ON groups.group_id = students.group_id WHERE student_id = ?";
    private static final String UPDATE_QUERY = "UPDATE students SET first_name=?, last_name=?, group_id=? WHERE student_id=?";
    private static final String REMOVE_QUERY = "DELETE FROM students WHERE student_id=?";
    private static final String SAVE_QUERY = "INSERT INTO students(first_name, last_name, group_id) VALUES(?,?,?)";
    private final JdbcTemplate jdbcTemplate;
    private final StudentMapper studentMapper;

    public List<Student> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, studentMapper);
    }

    @Override
    public Optional<Student> findById(Integer id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, studentMapper, id));
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    @Override
    public Student update(Student student) {
        int isUpdated = jdbcTemplate.update(UPDATE_QUERY,
                student.getFirstName(),
                student.getLastName(),
                student.getGroup().getId(),
                student.getId());

        if (isUpdated == 0) {
            student = null;
        }

        return student;
    }

    @Override
    public boolean delete(Integer id) {
        int isDeleted = jdbcTemplate.update(REMOVE_QUERY, id);

        return isDeleted != 0;
    }

    @Override
    public Student create(Student student) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, student.getFirstName());
            ps.setString(2, student.getLastName());
            ps.setInt(3, student.getGroup().getId());
            return ps;
        }, keyHolder);

        if (keyHolder.getKeys().get("student_id") != null) {
            student.setId((Integer) keyHolder.getKeys().get("student_id"));
        }

        return student;
    }

    @Override
    @Transactional
    public int[][] createAll(List<Student> students) {
        return jdbcTemplate.batchUpdate(SAVE_QUERY, students, BATCH_SIZE,
                (ps, student) -> {
                    ps.setString(1, student.getFirstName());
                    ps.setString(2, student.getLastName());
                    ps.setInt(3, student.getGroup().getId());
                });
    }
}



