package com.example.task_2_5_hibernate.dao;

import com.example.task_2_5_hibernate.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StudentDao implements EntityDao<Student, Integer> {
    private static final String FIND_ALL_QUERY = "SELECT student FROM Student student";
    private static final String FIND_BY_ID_QUERY = "SELECT student_id, groups.group_id, group_name, first_name, last_name FROM students INNER JOIN groups ON groups.group_id = students.group_id WHERE student_id = ?";
    private static final String UPDATE_QUERY = "UPDATE students SET first_name=?, last_name=?, group_id=? WHERE student_id=?";
    private static final String REMOVE_QUERY = "DELETE FROM students WHERE student_id=?";
    private static final String SAVE_QUERY = "INSERT INTO students(first_name, last_name, group_id) VALUES(?,?,?)";
    @PersistenceContext
    private EntityManager entityManager;

    public List<Student> findAll() {
        return entityManager.createQuery(FIND_ALL_QUERY, Student.class).getResultList();
    }

    @Override
    public Student findById(Integer id) {
        return entityManager.find(Student.class, id);
    }

    @Override
    @Transactional
    public Student update(Student student) {
        return entityManager.merge(student);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Student student = entityManager.find(Student.class, id);
        entityManager.remove(student);
    }

    @Override
    @Transactional
    public void create(Student student) {
        entityManager.persist(student);
    }

    @Override
    @Transactional
    public void createAll(List<Student> students) {
        students.forEach(student -> entityManager.persist(student));
    }
}



