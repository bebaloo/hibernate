package com.example.task_2_5_hibernate.dao;

import com.example.task_2_5_hibernate.entity.Group;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GroupDao implements EntityDao<Group, Integer> {
    private static final String FIND_ALL = "SELECT group FROM Group group";
    private static final String FIND_WITH_LESS_STUDENTS_NUMBER_QUERY = "SELECT g.group_id, g.group_name FROM groups g INNER JOIN students s on g.group_id = s.group_id GROUP BY s.group_id, group_name, g.group_id HAVING count(s.group_id) < :number ORDER BY group_id";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Group> findAll() {
        return entityManager.createQuery(FIND_ALL, Group.class).getResultList();
    }

    @Override
    public Group findById(Integer id) {
        return entityManager.find(Group.class, id);
    }

    @Override
    public Group update(Group group) {
        return entityManager.merge(group);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Group group = entityManager.find(Group.class, id);
        entityManager.remove(group);
    }

    @Override
    @Transactional
    public void create(Group group) {
       entityManager.persist(group);
    }

    @Override
    @Transactional
    public void createAll(List<Group> groups) {
       groups.forEach(group -> entityManager.persist(group));
    }

    public List<Group> findWithLessStudentsNumber(int studentsNumber) {
        return entityManager.createNativeQuery(FIND_WITH_LESS_STUDENTS_NUMBER_QUERY, Group.class)
                .setParameter("number", studentsNumber)
                .getResultList();
    }
}
