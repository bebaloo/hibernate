package com.example.task_2_5_hibernate.dao;

import com.example.task_2_5_hibernate.entity.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CourseDao implements EntityDao<Course, Integer> {
    private static final String FIND_ALL_QUERY = "SELECT course FROM Course course";
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Course> findAll() {
        return entityManager.createQuery(FIND_ALL_QUERY, Course.class).getResultList();
    }

    @Override
    public Course findById(Integer id) {
        return entityManager.find(Course.class, id);
    }

    @Override
    @Transactional
    public Course update(Course course) {
        return entityManager.merge(course);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Course course = entityManager.find(Course.class, id);
        entityManager.remove(course);
    }

    @Override
    @Transactional
    public void create(Course course) {
        entityManager.persist(course);

    }

    @Override
    @Transactional
    public void createAll(List<Course> courses) {
       courses.forEach(course -> entityManager.persist(course));
    }
}
