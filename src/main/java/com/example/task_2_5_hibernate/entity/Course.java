package com.example.task_2_5_hibernate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "courses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Integer id;
    @Column(name = "course_name")
    private String name;
    @Column(name = "course_description")
    private String description;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER)
    private Set<Student> students;

    public Course(int id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.description = desc;
    }

    public Course(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
