package com.example.task_2_5_hibernate.service;

import com.example.task_2_5_hibernate.mapper.GroupMapper;
import com.example.task_2_5_hibernate.repository.GroupRepository;
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
public class GroupService implements EntityService<Group, Long> {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Override
    public List<Group> getAll() {
        log.info("Getting all groups");
        return groupRepository.findAll();
    }

    @Override
    public Group getById(Long id) {
        Optional<Group> group = groupRepository.findById(id);

        group.ifPresentOrElse(g -> log.info("Getting " + g),
                () -> log.info("Course with id: " + id + " not found"));

        return group.orElse(null);
    }

    @Override
    public Group update(Group group) {
        try {
            Group updatedGroup = groupRepository.findById(group.getId()).orElseThrow(EntityNotUpdatedException::new);
            groupMapper.updateGroup(group, updatedGroup);
            groupRepository.save(updatedGroup);

            log.info(updatedGroup + " was updated");
            return updatedGroup;
        } catch (RuntimeException e) {
            log.warn(group + " wasn`t updated");
            throw new EntityNotUpdatedException(group + " wasn`t updated");
        }
    }

    @Override
    public Group create(Group group) {
       try {
           Group createdGroup = groupRepository.save(group);
           log.info("Create + " + group);

           return createdGroup;
       } catch (RuntimeException e) {
           log.warn( group + " wasn`t created");
           throw new EntityNotUpdatedException(group + " wasn`t created");
       }
    }

    @Override
    public List<Group> createAll(List<Group> groups) {
        try {
            List<Group> createdGroups = groupRepository.saveAll(groups);
            log.info("Create + " + groups);

            return createdGroups;
        } catch (RuntimeException e) {
            log.warn("Groups weren`t created");
            throw new EntityNotUpdatedException(groups + " weren`t created");
        }
    }

    @Override
    public Group deleteById(Long id) {
        try {
            Group group = groupRepository.findById(id).orElseThrow(EntityNotUpdatedException::new);
            groupRepository.delete(group);

            log.info("Delete group with id: " + id);
            return group;
        } catch (RuntimeException e) {
            log.warn("Group with id: " + id + " not found");
            throw new EntityNotUpdatedException("Group wasn`t deleted");
        }
    }

    public List<Group> getWithLessStudentsNumber(int studentsNumber) {
        log.info("Getting group with less student number: " + studentsNumber);
        return groupRepository.findGroupsByStudentsNumber(studentsNumber);
    }
}
