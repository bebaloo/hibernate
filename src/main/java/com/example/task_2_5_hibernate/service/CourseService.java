package com.example.task_2_5_hibernate.service;

import com.example.task_2_5_hibernate.dao.CourseDao;
import com.example.task_2_5_hibernate.entity.Course;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CourseService implements EntityService<Course, Integer> {
    private final CourseDao courseDao;

    @Override
    public List<Course> getAll() {
        log.info("Getting all courses");
        return courseDao.findAll();
    }

    @Override
    public Course getById(Integer id) {
        Course courseById = courseDao.findById(id);
        if (courseById == null) {
            log.info("Course with id: " + id + " not found");
        } else {
            log.info("Get " + courseById);
        }

        return courseById;
    }

    @Override
    public Course update(Course course) {
        Course updatedCourse = courseDao.update(course);

        if (updatedCourse == null) {
            log.info("Course wasn`t updated");
        } else {
            log.info(updatedCourse + " was updated");
        }

        return updatedCourse;
    }

    @Override
    public void create(Course course) {
        log.info("Create " + course);
        courseDao.create(course);
    }

    @Override
    public void createAll(List<Course> courses) {
        log.info("Create " + courses);
        courseDao.createAll(courses);
    }

    @Override
    public void delete(Integer id) {
        try {
            courseDao.delete(id);
            log.info("Delete course with id: " + id);
        } catch (RuntimeException e) {
            log.warn("Course with id: " + id + " not found");
        }
    }
}
