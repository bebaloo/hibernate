package com.example.task_2_5_hibernate.service;

import com.example.task_2_5_hibernate.dao.GroupDao;
import com.example.task_2_5_hibernate.entity.Group;
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
                () ->  log.info("Group with id: " + id + " not found"));

        return group.orElse(null);
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
    public Group create(Group group) {
        Group createdGroup = groupDao.create(group);
        log.info(createdGroup + " was created");
        return createdGroup;
    }

    @Override
    public List<Group> createAll(List<Group> groups) {
        log.info(groups + "were created");
        groupDao.createAll(groups);
        return groups;
    }

    @Override
    public boolean delete(Integer id) {
        boolean isDeleted = groupDao.delete(id);
        if (isDeleted) {
            log.info("Group with id: " + id + " wasn`t deleted");
        } else  {
            log.info("Group with id: " + id + " was deleted");
        }
        return isDeleted;
    }

    public List<Group> getWithLessStudentsNumber(int studentsNumber) {
        log.info("Getting group with less student number: " + studentsNumber);
        return groupDao.findWithLessStudentsNumber(studentsNumber);
    }
}
