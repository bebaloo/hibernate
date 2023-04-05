package com.example.task_2_5_hibernate.service;

import com.example.task_2_5_hibernate.dao.CourseDao;
import com.example.task_2_5_hibernate.entity.Course;
import com.example.task_2_5_hibernate.exception.EntityNotUpdatedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Course> course = courseDao.findById(id);

        course.ifPresentOrElse(c -> log.info("Getting " + c),
                () -> log.info("Course with id: " + id + " not found"));

        return course.orElse(null);
    }

    @Override
    public Course update(Course course) {
        Course updatedCourse;
        try {
            updatedCourse = courseDao.update(course);
            log.info(updatedCourse + " was updated");
        } catch (RuntimeException e) {
            log.warn(course + " wasn`t updated");
            throw new EntityNotUpdatedException(course + " wasn`t updated");
        }

        return updatedCourse;
    }

    @Override
    public void create(Course course) {
        try {
            courseDao.create(course);
            log.info("Create " + course);
        } catch (RuntimeException e) {
            log.warn(course + " wasn`t created");
            throw new EntityNotUpdatedException(course + " wasn`t created");
        }
    }

    @Override
    public void createAll(List<Course> courses) {
        try {
            courseDao.createAll(courses);
            log.info("Create " + courses);
        } catch (RuntimeException e) {
            log.warn("Courses wasn`t created");
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            courseDao.delete(id);
            log.info("Delete course with id: " + id);
        } catch (RuntimeException e) {
            log.warn("Course with id: " + id + " not found");
            throw new EntityNotUpdatedException("Course wasn`t deleted");
        }
    }
}
