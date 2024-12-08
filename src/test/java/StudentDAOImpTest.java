import com.sppp.connection.DBConnection;
import com.sppp.dao.StudentDAO;
import com.sppp.dao.StudentDAOImp;
import com.sppp.model.Project;
import com.sppp.model.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentDAOImpTest {

    private static StudentDAO studentDAO;
    private static Connection connection;

    @BeforeAll
    static void setupDatabaseConnection() throws SQLException {
        connection = DBConnection.getInstance().getConnection();
        studentDAO = new StudentDAOImp();
    }

    @Test
    void testCreateStudent() throws SQLException {
        Student student = new Student();
        student.setName("Erick");
        student.setLastname("Vazquez");
        student.setNrc("12345");
        student.setEnrolment("ZS23011");

        studentDAO.createStudent(student);

        assertNotNull(student.getIdstudent(), "The student ID should have been generated");
    }

    @Test
    public void getAllTest(){
        try {
            List<Student> studentList = studentDAO.getAllStudents();
            for(Student student : studentList){
                System.out.println(student.getName());
                assertNotNull(student);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testReadStudent() throws SQLException {
        // Insertar datos directamente
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO student (name, lastname, nrc, enrolment) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, "Jane");
            ps.setString(2, "Doe");
            ps.setString(3, "54321");
            ps.setString(4, "B002");
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            assertTrue(rs.next());
            int id = rs.getInt(1);

            // Probar el método readStudent
            Student student = studentDAO.readStudent(id);
            assertEquals("Jane", student.getName());
            assertEquals("Doe", student.getLastname());
            assertEquals("54321", student.getNrc());
            assertEquals("B002", student.getEnrolment());
        }
    }


    @Test
    void testUpdateStudent() throws SQLException {
        Student student = new Student();
        student.setName("John");
        student.setLastname("Smith");
        student.setNrc("99999");
        student.setEnrolment("C003");

        studentDAO.createStudent(student);

        student.setLastname("Brown");
        studentDAO.updateStudent(student);

        Student updatedStudent = studentDAO.readStudent(student.getIdstudent());
        assertEquals("Brown", updatedStudent.getLastname());
    }

    @Test
    void testDeleteStudent() throws SQLException {
        Student student = new Student();
        student.setName("Alice");
        student.setLastname("Green");
        student.setNrc("11111");
        student.setEnrolment("D004");

        studentDAO.createStudent(student);
        studentDAO.deleteStudent(student);

        Student deletedStudent = studentDAO.readStudent(student.getIdstudent());
        assertNull(deletedStudent.getName(), "The student should no longer exist in the database");
    }

    @Test
    void testAssignStudentToProject() throws SQLException {
        // Insertar proyecto
        Project project = new Project();
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO project (nameprj, relatedorg, quota) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, "Project A");
            ps.setString(2, "Org A");
            ps.setInt(3, 5);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            assertTrue(rs.next());
            project.setIdproject(rs.getInt(1));
        }

        // Insertar estudiante
        Student student = new Student();
        student.setName("Eve");
        student.setLastname("White");
        student.setNrc("22222");
        student.setEnrolment("E005");
        student.setIdproject(project);

        studentDAO.createStudent(student);
        studentDAO.assignStudentToProject(student, project);

        try (PreparedStatement ps = connection.prepareStatement("SELECT quota FROM project WHERE idproject = ?")) {
            ps.setInt(1, project.getIdproject());
            ResultSet rs = ps.executeQuery();
            assertTrue(rs.next());
            assertEquals(4, rs.getInt(1));
        }
    }


}

