package com.example.task_2_5_hibernate.dao.mapper;

import com.example.task_2_5_hibernate.entity.Course;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CourseMapper implements RowMapper<Course> {
    @Override
    public Course mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Course course = new Course();

        course.setId(resultSet.getInt("course_id"));
        course.setName(resultSet.getString("course_name"));
        course.setDescription(resultSet.getString("course_description"));

        return course;
    }
}
