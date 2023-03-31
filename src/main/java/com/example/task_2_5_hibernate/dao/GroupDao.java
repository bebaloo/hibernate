package com.example.task_2_5_hibernate.dao;

import com.example.task_2_5_hibernate.dao.mapper.GroupMapper;
import com.example.task_2_5_hibernate.entity.Group;
import com.example.task_2_5_hibernate.exception.EntityNotUpdatedException;
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
public class GroupDao implements EntityDao<Group, Integer> {
    private static final String FIND_ALL = "SELECT * FROM groups";
    private static final String FIND_BY_ID = "SELECT * FROM groups WHERE group_id=?";
    private static final String UPDATE_QUERY = "UPDATE groups SET group_name=? WHERE group_id=?";
    private static final String REMOVE_QUERY = "DELETE FROM groups WHERE group_id=?";
    private static final String SAVE_QUERY = "INSERT INTO groups(group_name) VALUES(?)";
    private static final String FIND_WITH_LESS_STUDENTS_NUMBER_QUERY = "SELECT g.group_id, g.group_name FROM groups g INNER JOIN students s on g.group_id = s.group_id GROUP BY s.group_id, group_name, g.group_id HAVING count(s.group_id) < ? ORDER BY group_id";

    private final JdbcTemplate jdbcTemplate;
    private final GroupMapper groupMapper;

    @Override
    public List<Group> findAll() {
        return jdbcTemplate.query(FIND_ALL, groupMapper);
    }

    @Override
    public Optional<Group> findById(Integer id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID, groupMapper, id));
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    @Override
    public Group update(Group group) {
        int isUpdated = jdbcTemplate.update(UPDATE_QUERY,
                group.getName(),
                group.getId());

        if (isUpdated == 0) {
            throw new EntityNotUpdatedException(group + " not updated");
        }

        return group;
    }

    @Override
    public boolean delete(Integer id) {
        int isDeleted = jdbcTemplate.update(REMOVE_QUERY, id);

        return isDeleted != 0;
    }

    @Override
    public Group create(Group group) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, group.getName());
            return ps;
        }, keyHolder);

        if (keyHolder.getKeys().get("group_id") != null) {
            group.setId((Integer) keyHolder.getKeys().get("group_id"));
        }

        return group;
    }

    @Override
    @Transactional
    public int[][] createAll(List<Group> groups) {
        return jdbcTemplate.batchUpdate(SAVE_QUERY, groups, BATCH_SIZE,
                ((ps, group) -> ps.setString(1, group.getName())));
    }

    public List<Group> findWithLessStudentsNumber(int studentsNumber) {
        return jdbcTemplate.query(FIND_WITH_LESS_STUDENTS_NUMBER_QUERY, groupMapper, studentsNumber);
    }
}
