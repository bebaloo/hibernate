package com.example.task_2_5_hibernate;


import com.example.task_2_5_hibernate.entity.Course;
import com.example.task_2_5_hibernate.entity.Student;
import com.example.task_2_5_hibernate.service.CourseService;
import com.example.task_2_5_hibernate.service.GroupService;
import com.example.task_2_5_hibernate.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class ConsoleMenu {
    private final StudentService studentService;
    private final GroupService groupService;
    private final CourseService courseService;
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        int menuId;
        do {
            System.out.println("""
                    1. Find all groups with less students number
                    2. Find all students related to the course with the given name
                    3. Add a new student
                    4. Delete a student by the STUDENT_ID
                    5. Add a student to the course (from a list)
                    6. Remove the student from one of their courses.
                    7. Exit
                    """);
            menuId = scanner.nextInt();
            switch (menuId) {
                case 1 -> findAllGroupsWithLessStudentsNumber();
                case 2 -> findAllStudentsByCourseName();
                case 3 -> addNewStudent();
                case 4 -> deleteStudentById();
                case 5 -> addStudentToCourse();
                case 6 -> removeStudentFromCourse();
                case 7 -> scanner.close();
                default -> System.out.println("Enter number 1-7\n");

            }
        } while (menuId != 7);
    }

    private void findAllGroupsWithLessStudentsNumber() {
        System.out.println("Enter the number of students:");
        int number = scanner.nextInt();

        groupService.getWithLessStudentsNumber(number).forEach(System.out::println);
    }

    private void findAllStudentsByCourseName() {
        courseService.getAll().forEach(System.out::println);

        System.out.println("Enter name of course:");
        String courseName = scanner.next();

        studentService.getStudentsByCourseName(courseName).forEach(System.out::println);
    }

    private void addNewStudent() {
        System.out.println("Enter first name:");
        String firstName = scanner.next();

        System.out.println("Enter last name:");
        String lastName = scanner.next();

        groupService.getAll().forEach(System.out::println);
        System.out.println("Enter group id:");
        int groupId = scanner.nextInt();

        Student student = new Student(firstName, lastName, groupId);
        try {
            studentService.create(student);
            System.out.println(student + " was created");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

    }

    private void deleteStudentById() {
        studentService.getAll().forEach(System.out::println);

        System.out.println("Enter id of student:");
        int studentId = scanner.nextInt();

        try {
            studentService.delete(studentId);
            System.out.println("Student was deleted");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addStudentToCourse() {
        studentService.getAll().forEach(System.out::println);

        System.out.println("Enter student id:");
        int studentId = scanner.nextInt();

        courseService.getAll().forEach(System.out::println);

        System.out.println("Enter course id:");
        int courseId = scanner.nextInt();

        studentService.addStudentToCourse(studentId, courseId);
        System.out.println("Student with id: " + studentId + " was added to course with id: " + courseId);
    }

    private void removeStudentFromCourse() {
        courseService.getAll().forEach(System.out::println);
        System.out.println("Enter id of course:");
        int courseId = scanner.nextInt();
        Course course = courseService.getById(courseId);

        studentService.getStudentsByCourseId(courseId).forEach(System.out::println);
        System.out.println("Enter id of student to delete him:");
        int studentId = scanner.nextInt();

        Student student = studentService.getById(studentId);
        studentService.removeStudentFromCourse(student, course);
        System.out.println("Student with id: " + studentId + " was deleted from course with id: " + courseId);
    }
}
