package com.example.task_2_5_hibernate.dao.mapper;

import com.example.task_2_5_hibernate.entity.Group;
import com.example.task_2_5_hibernate.entity.Student;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class StudentMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Student student = new Student();

        student.setId(resultSet.getInt("student_id"));
        student.setGroup(
                new Group(resultSet.getInt("group_id"), resultSet.getString("group_name")));
        student.setFirstName(resultSet.getString("first_name"));
        student.setLastName(resultSet.getString("last_name"));

        return student;
    }
}
