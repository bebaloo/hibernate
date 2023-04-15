package com.example.task_2_5_hibernate.repository;

import com.example.task_2_5_hibernate.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("SELECT g FROM Group g JOIN Student s ON g = s.group GROUP BY s.group, g.name, g.id HAVING count(s.group) < :studentsNumber")
    List<Group> findGroupsByStudentsNumber(@Param("studentsNumber") int studentsNumber);
}
