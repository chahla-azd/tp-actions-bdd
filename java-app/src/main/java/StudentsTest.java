import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.Statement;
import org.junit.jupiter.api.*;

class StudentsTest {

    private Connection conn;

    @BeforeEach
    void setUp() throws Exception {
        conn = Students.connect();
        try (Statement st = conn.createStatement()) { // table vidée avant chaque test
            st.execute("TRUNCATE students RESTART IDENTITY");
        }
    }

    @AfterEach
    void tearDown() throws Exception {
        conn.close();
    }

    @Test
    void ajouterEtLire() throws Exception {
        int id = Students.addStudent(conn, "Amel", 15);
        Students.Student s = Students.getStudent(conn, id).orElseThrow();
        assertEquals("Amel", s.name());
        assertEquals(15.0, s.grade());
    }

    @Test
    void etudiantInexistant() throws Exception {
        assertTrue(Students.getStudent(conn, 9999).isEmpty());
    }

    @Test
    void moyenne() throws Exception {
        Students.addStudent(conn, "Amel", 10);
        Students.addStudent(conn, "Karim", 14);
        assertEquals(12.0, Students.averageGrade(conn));
    }
}