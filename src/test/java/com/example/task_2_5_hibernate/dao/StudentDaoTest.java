package com.example.task_2_5_hibernate.dao;

import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@JdbcTest
@Sql(scripts = {"student-schema.sql", "init-students.sql"},
        executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "schema-drop.sql", executionPhase = AFTER_TEST_METHOD)
class StudentDaoTest {

   /* @Autowired
    private JdbcTemplate jdbcTemplate;
    private StudentDao studentDao;
    @BeforeEach
    void setUp() {
        studentDao = new StudentDao(jdbcTemplate, new StudentMapper());
    }

    @Test
    void findAll_returnsStudents() {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student("Dima", "Tkachuk"));
        expectedStudents.add(new Student("Yarik", "Shevchenko"));
        expectedStudents.add(new Student("Olga", "Melnyk"));

        List<Student> actualStudents = studentDao.findAll().stream()
                .map(student -> new Student(student.getFirstName(), student.getLastName()))
                .toList();
        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void findById_existingStudentId_returnsStudent() {
        Student student = studentDao.findById(1).orElse(null);

        assertNotNull(student);
    }

    @Test
    void findById_nonExistingStudentId_returnsNull() {
        Student student = studentDao.findById(201).orElse(null);

        assertNull(student);
    }

    @Test
    void update_correctStudent_Ok() {
        Student updatedStudent = studentDao.findById(1).orElse(null);

        studentDao.update(new Student(1 ,new Group(1, "aa-11"),"Igor", "Frolov"));
        Student actualStudent = studentDao.findById(1).orElse(null);

        assertNotEquals(updatedStudent, actualStudent);
    }

    @Test
    void remove_existingStudent_Ok() {
        studentDao.delete(1);
        boolean isEmptyAfterRemoving = studentDao.findById(1).isEmpty();

        assertTrue(isEmptyAfterRemoving);
    }

    @Test
    void remove_nonExistingStudent_throwsException() {
        assertFalse(studentDao.delete(201));

    }


    @Test
    void save_correctStudent_Ok() {
        Student student = new Student(new Group(1, "aa-11"), "Oleg", "Guk");
        Student savedStudent = studentDao.create(student);

        assertEquals(savedStudent.getGroup(), student.getGroup());
        assertEquals(savedStudent.getFirstName(), student.getFirstName());
        assertEquals(savedStudent.getLastName(), student.getLastName());
    }

    @Test
    void saveAll_correctStudents_Ok() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(new Group(1, "aa-11"), "Misha", "Misha"));
        students.add(new Student(new Group(1, "aa-11"), "Oleg", "Guk"));

        studentDao.createAll(students);

        List<Student> savedStudents = studentDao.findAll().stream()
                .map(s -> new Student(s.getGroup(), s.getFirstName(), s.getLastName()))
                .toList();
        boolean isSaved = savedStudents.containsAll(students);

        assertTrue(isSaved);
    }*/
}