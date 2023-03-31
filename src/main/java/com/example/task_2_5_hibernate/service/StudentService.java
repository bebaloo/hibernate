package com.example.task_2_5_hibernate.service;

import com.example.task_2_5_hibernate.dao.StudentDao;
import com.example.task_2_5_hibernate.entity.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Student> student = studentDao.findById(id);

        student.ifPresentOrElse(s ->  log.info("Getting " + s),
                () ->  log.info("Student with id: " + id + " not found"));

        return student.orElse(null);
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
    public Student create(Student student) {
        Student createdStudent = studentDao.create(student);
        log.info(createdStudent + " was created");
        return createdStudent;
    }

    @Override
    public List<Student> createAll(List<Student> students) {
        log.info(students + "were created");
        studentDao.createAll(students);
        return students;
    }

    @Override
    public boolean delete(Integer id) {
        boolean isDeleted = studentDao.delete(id);

        if (isDeleted) {
            log.info("Student with id: " + id + " wasn`t deleted");
        } else  {
            log.info("Student with id: " + id + " was deleted");
        }
        return isDeleted;
    }
}
