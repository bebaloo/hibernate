package com.example.task_2_5_hibernate.service;

import com.example.task_2_5_hibernate.dao.GroupDao;
import com.example.task_2_5_hibernate.entity.Group;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Group groupById = groupDao.findById(id);
        if (groupById == null) {
            log.info("Group with id: " + id + " not found");
        } else {
            log.info("Get " + groupById);
        }
        return groupById;
    }

    @Override
    public Group update(Group group) {
        Group updatedGroup = groupDao.update(group);

        if (updatedGroup == null) {
            log.info("Group wasn`t updated");
        } else {
            log.info(group + " was updated");
        }

        return updatedGroup;
    }

    @Override
    public void create(Group group) {
        log.info("Create + " + group);
        groupDao.create(group);
    }

    @Override
    public void createAll(List<Group> groups) {
        log.info("Create + " + groups);
        groupDao.createAll(groups);
    }

    @Override
    public void delete(Integer id) {
        try {
            groupDao.delete(id);
            log.info("Delete group with id: " + id);
        } catch (RuntimeException e) {
            log.warn("Group with id: " + id + " not found");
        }

    }

    public List<Group> getWithLessStudentsNumber(int studentsNumber) {
        log.info("Getting group with less student number: " + studentsNumber);
        return groupDao.findWithLessStudentsNumber(studentsNumber);
    }
}
