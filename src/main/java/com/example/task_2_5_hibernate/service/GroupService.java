package com.example.task_2_5_hibernate.service;

import com.example.task_2_5_hibernate.dao.GroupDao;
import com.example.task_2_5_hibernate.entity.Group;
import com.example.task_2_5_hibernate.exception.EntityNotUpdatedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class GroupService implements EntityService<Group, Integer> {
    private final GroupDao groupDao;

    @Override
    public List<Group> getAll() {
        log.info("Getting all groups");
        return groupDao.findAll();
    }

    @Override
    public Group getById(Integer id) {
        Optional<Group> group = groupDao.findById(id);

        group.ifPresentOrElse(g -> log.info("Getting " + g),
                () -> log.info("Course with id: " + id + " not found"));

        return group.orElse(null);
    }

    @Override
    public Group update(Group group) {
        Group updatedGroup;
        try {
            updatedGroup = groupDao.update(group);
            log.info(group + " was updated");
        } catch (RuntimeException e) {
            log.warn("Group wasn`t updated");
            throw new EntityNotUpdatedException(group + " wasn`t updated");
        }

        return updatedGroup;
    }

    @Override
    public void create(Group group) {
       try {
           groupDao.create(group);
           log.info("Create + " + group);
       } catch (RuntimeException e) {
           log.warn( group + " wasn`t created");
           throw new EntityNotUpdatedException(group + " wasn`t created");
       }
    }

    @Override
    public void createAll(List<Group> groups) {
        try {
            groupDao.createAll(groups);
            log.info("Create + " + groups);
        } catch (RuntimeException e) {
            log.warn("Groups weren`t created");
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            groupDao.delete(id);
            log.info("Delete group with id: " + id);
        } catch (RuntimeException e) {
            log.warn("Group with id: " + id + " not found");
            throw new EntityNotUpdatedException("Group wasn`t deleted");
        }
    }

    public List<Group> getWithLessStudentsNumber(int studentsNumber) {
        log.info("Getting group with less student number: " + studentsNumber);
        return groupDao.findWithLessStudentsNumber(studentsNumber);
    }
}
