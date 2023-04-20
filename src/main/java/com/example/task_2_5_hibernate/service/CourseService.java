package com.example.task_2_5_hibernate.service;

import com.example.task_2_5_hibernate.entity.Course;
import com.example.task_2_5_hibernate.exception.EntityNotUpdatedException;
import com.example.task_2_5_hibernate.mapper.CourseMapper;
import com.example.task_2_5_hibernate.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CourseService implements EntityService<Course, Long> {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public List<Course> getAll() {
        log.info("Getting all courses");
        return courseRepository.findAll();
    }

    @Override
    public Course getById(Long id) {
        Optional<Course> course = courseRepository.findById(id);

        course.ifPresentOrElse(c -> log.info("Getting " + c),
                () -> log.info("Course with id: " + id + " not found"));

        return course.orElse(null);
    }

    @Override
    public Course update(Course course) {
        try {
            Course updatedCourse = courseRepository.findById(course.getId()).orElseThrow(EntityNotUpdatedException::new);
            courseMapper.updateCourse(course, updatedCourse);
            courseRepository.save(updatedCourse);

            log.info(updatedCourse + " was updated");
            return updatedCourse;
        } catch (RuntimeException e) {
            log.warn(course + " wasn`t updated");
            throw new EntityNotUpdatedException(course + " wasn`t updated");
        }
    }

    @Override
    public Course create(Course course) {
        try {
            Course createdCourse = courseRepository.save(course);
            log.info("Create " + course);

            return createdCourse;
        } catch (RuntimeException e) {
            log.warn(course + " wasn`t created");
            throw new EntityNotUpdatedException(course + " wasn`t created");
        }
    }

    @Override
    public List<Course> createAll(List<Course> courses) {
        try {
            List<Course> createdCourses = courseRepository.saveAll(courses);
            log.info("Create " + courses);

            return createdCourses;
        } catch (RuntimeException e) {
            log.warn("Courses wasn`t created");
            throw new EntityNotUpdatedException(courses + " weren`t created");
        }
    }

    @Override
    public Course deleteById(Long id) {
        try {
            Course course = courseRepository.findById(id).orElseThrow(EntityNotUpdatedException::new);
            courseRepository.delete(course);

            log.info("Delete course with id: " + id);
            return course;
        } catch (RuntimeException e) {
            log.warn("Course with id: " + id + " not found");
            throw new EntityNotUpdatedException("Course wasn`t deleted");
        }
    }
}
