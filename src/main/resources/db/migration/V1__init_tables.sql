CREATE TABLE IF NOT EXISTS groups
(
    group_id   serial PRIMARY KEY,
    group_name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS courses
(
    course_id          serial PRIMARY KEY,
    course_name        VARCHAR NOT NULL,
    course_description VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS students
(
    student_id serial PRIMARY KEY,
    first_name VARCHAR NOT NULL,
    last_name  VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS students_courses
(
    student_id INT REFERENCES students(student_id),
    course_id INT REFERENCES courses(course_id),
    CONSTRAINT students_courses_pk PRIMARY KEY (student_id, course_id)
);