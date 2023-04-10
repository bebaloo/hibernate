package com.example.task_2_5_hibernate.dao;

import com.example.task_2_5_hibernate.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT c.students FROM Course c WHERE c.name = :name")
    List<Student> findStudentsByCourseName(@Param("name") String courseName);

    @Query("SELECT c.students FROM Course c WHERE c.id = :id")
    List<Student> findStudentsByCourseId(@Param("id") Long courseId);
}
