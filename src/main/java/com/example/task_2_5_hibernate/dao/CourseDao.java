package com.example.task_2_5_hibernate.dao;

import com.example.task_2_5_hibernate.dao.mapper.CourseMapper;
import com.example.task_2_5_hibernate.entity.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CourseDao implements EntityDao<Course, Integer> {
    private static final String FIND_ALL_QUERY = "SELECT * FROM courses";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM courses WHERE course_id=?";
    private static final String UPDATE_QUERY = "UPDATE courses SET course_name=?, course_description=? WHERE course_id=?";
    private static final String REMOVE_QUERY = "DELETE FROM courses WHERE course_id=?";
    private static final String SAVE_QUERY = "INSERT INTO courses(course_name, course_description) VALUES(?,?)";
    private final JdbcTemplate jdbcTemplate;
    private final CourseMapper courseMapper;

    @Override
    public List<Course> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, courseMapper);
    }

    @Override
    public Optional<Course> findById(Integer id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, courseMapper, id));
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    @Override
    public Course update(Course course) {
        int isUpdated = jdbcTemplate.update(UPDATE_QUERY,
                course.getName(),
                course.getDescription(),
                course.getId());

            return isUpdated != 0 ? course : null;
        }

        @Override
        public boolean delete (Integer id){
            int isDeleted = jdbcTemplate.update(REMOVE_QUERY, id);

            return isDeleted != 0;
        }

        @Override
        public Course create (Course course){
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, course.getName());
                ps.setString(2, course.getDescription());
                return ps;
            }, keyHolder);

            if (keyHolder.getKeys().get("course_id") != null) {
                course.setId((Integer) keyHolder.getKeys().get("course_id"));
            }

            return course;
        }

        @Override
        public int[][] createAll (List < Course > courses) {
            return jdbcTemplate.batchUpdate(SAVE_QUERY, courses, BATCH_SIZE,
                    ((ps, course) -> {
                        ps.setString(1, course.getName());
                        ps.setString(2, course.getDescription());
                    }));
        }
    }
