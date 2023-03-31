package com.example.task_2_5_hibernate.service;

import com.example.task_2_5_hibernate.dao.CourseDao;
import com.example.task_2_5_hibernate.entity.Course;
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
        Course updatedCourse = courseDao.update(course);

        if (updatedCourse == null) {
            log.info("Course wasn`t updated");
        } else {
            log.info(updatedCourse + " was updated");
        }

        return updatedCourse;
    }

    @Override
    public Course create(Course course) {
        log.info(course + " was created");
        return courseDao.create(course);
    }

    @Override
    public List<Course> createAll(List<Course> courses) {
        log.info(courses + " were created");
        courseDao.createAll(courses);
        return courses;
    }

    @Override
    public boolean delete(Integer id) {
        boolean isDeleted = courseDao.delete(id);
        if (isDeleted) {
            log.info("Course with id: " + id + " wasn`t deleted");
        } else  {
            log.info("Course with id: " + id + " was deleted");
        }
        return isDeleted;
    }
}
