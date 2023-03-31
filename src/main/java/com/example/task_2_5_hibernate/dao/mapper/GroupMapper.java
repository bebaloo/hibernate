package com.example.task_2_5_hibernate.dao.mapper;

import com.example.task_2_5_hibernate.entity.Group;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GroupMapper implements RowMapper<Group> {
    @Override
    public Group mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Group group = new Group(1, "aa-11");

        group.setId(resultSet.getInt("group_id"));
        group.setName(resultSet.getString("group_name"));

        return group;
    }
}
