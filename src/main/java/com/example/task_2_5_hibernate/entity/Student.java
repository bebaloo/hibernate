package com.example.task_2_5_hibernate.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "students_courses",
    joinColumns = {@JoinColumn(name = "student_id")},
    inverseJoinColumns = {@JoinColumn(name = "course_id")})
    private Set<Course> courses = new HashSet<>();

    public Student(Group group, String firstName, String lastName) {
        this.group = group;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(String firstName, String lastName, int groupId) {
        this.group = new Group(groupId);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(int id, Group group, String firstName, String lastName) {
        this.id = id;
        this.group = group;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void removeCourse(Course course) {
        this.courses.remove(course);
        course.getStudents().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Student student = (Student) o;
        return id != null && Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
