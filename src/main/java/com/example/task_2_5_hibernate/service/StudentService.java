package com.example.task_2_5_hibernate.service;

import com.example.task_2_5_hibernate.dao.StudentDao;
import com.example.task_2_5_hibernate.entity.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class StudentService implements EntityService<Student, Integer> {
    private final StudentDao studentDao;

    @Override
    public List<Student> getAll() {
        log.info("Getting all students");
        return studentDao.findAll();
    }

    @Override
    public Student getById(Integer id) {
        Student studentById = studentDao.findById(id);
        if (studentById == null) {
            log.info("Student with id: " + id + " not found");
        } else {
            log.info("Get " + studentById);
        }
        return studentById;
    }

    @Override
    public Student update(Student student) {
        Student updatedStudent = studentDao.update(student);

        if (updatedStudent == null) {
            log.info("Student wasn`t updated");
        } else {
            log.info(student + " was updated");
        }

        return updatedStudent;
    }

    @Override
    public void create(Student student) {
        log.info("Create + " + student);
        studentDao.create(student);
    }

    @Override
    public void createAll(List<Student> students) {
        log.info("Create " + students);
        studentDao.createAll(students);
    }

    @Override
    public void delete(Integer id) {
        try {
            studentDao.delete(id);
            log.info("Delete student with id: " + id);
        } catch (RuntimeException e) {
            log.warn("Student with id: " + id + " not found");
        }
    }
}
