package com.example.task_2_5_hibernate;

import com.example.task_2_5_hibernate.entity.Course;
import com.example.task_2_5_hibernate.entity.Group;
import com.example.task_2_5_hibernate.entity.Student;
import com.example.task_2_5_hibernate.service.CourseService;
import com.example.task_2_5_hibernate.service.GroupService;
import com.example.task_2_5_hibernate.service.StudentService;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class Storage {
    private static final int NUMBER_OF_STUDENTS = 200;
    private static final int NUMBER_OF_GROUPS_COURSES = 10;
    private static final int[] ID_OF_GROUPS_COURSES = new int[]{1, 10};
    private final FakeValuesService fakeValuesService;
    private final Faker faker;
    private final GroupService groupService;
    private final StudentService studentService;
    private final CourseService courseService;

    public void fillDB() {
        log.info("Filling database");
        if (groupService.getAll().isEmpty() && studentService.getAll().isEmpty() && courseService.getAll().isEmpty()) {
            groupService.createAll(generateGroups());
            studentService.createAll(generateStudents());
            courseService.createAll(generateCourses());
            saveStudentsToCourses();
        }
    }

    private List<Student> generateStudents() {
        List<Student> students = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_STUDENTS; i++) {
            Student student = new Student();

            student.setFirstName(faker.name().firstName());
            student.setLastName(faker.name().lastName());
            student.setGroup(new Group(faker.number().numberBetween(ID_OF_GROUPS_COURSES[0], ID_OF_GROUPS_COURSES[1])));

            students.add(student);
        }
        return students;
    }

    private List<Group> generateGroups() {
        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_GROUPS_COURSES; i++) {
            Group group = new Group();

            group.setName(fakeValuesService.bothify("??-##"));

            groups.add(group);
        }
        return groups;
    }

    private List<Course> generateCourses() {
        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_GROUPS_COURSES; i++) {
            courses.add(new Course(faker.app().name(), faker.howIMetYourMother().catchPhrase()));
        }
        return courses;
    }

    private void saveStudentsToCourses() {
        List<Integer> studentsId = studentService.getAll().stream()
                .map(Student::getId)
                .toList();

        for (int studentId : studentsId) {
            studentService.addStudentToCourse(studentId, faker.number().numberBetween(ID_OF_GROUPS_COURSES[0], ID_OF_GROUPS_COURSES[1]));
        }
    }
}
