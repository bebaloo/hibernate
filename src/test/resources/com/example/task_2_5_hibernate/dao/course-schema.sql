CREATE TABLE IF NOT EXISTS courses
(
    course_id          IDENTITY NOT NULL PRIMARY KEY,
    course_name        VARCHAR  NOT NULL,
    course_description VARCHAR  NOT NULL
);