CREATE TABLE IF NOT EXISTS groups
(
    group_id   IDENTITY NOT NULL PRIMARY KEY,
    group_name VARCHAR  NOT NULL
);

CREATE TABLE IF NOT EXISTS students
(
    student_id IDENTITY NOT NULL PRIMARY KEY,
    group_id   INT REFERENCES groups (group_id),
    first_name VARCHAR NOT NULL,
    last_name  VARCHAR NOT NULL
);