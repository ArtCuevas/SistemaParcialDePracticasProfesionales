import com.sppp.connection.DBConnection;
import com.sppp.dao.ProjectDAO;
import com.sppp.dao.ProjectDAOImp;
import com.sppp.model.Project;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectDaoImpTest {
    private static ProjectDAO projectDAO;
    private static Connection connection;

    @BeforeAll
    static void setupDatabaseConnection() throws SQLException {
        connection = DBConnection.getInstance().getConnection();
        projectDAO = new ProjectDAOImp();
    }

        @Test
    void testCreateProject() throws SQLException {
        Project project = new Project();
        project.setNameprj("Project A");
        project.setRelatedorg("Organization A");
        project.setQuota(10);

        projectDAO.createProject(project);

        assertNotNull(project.getIdproject(), "The project ID should have been generated.");
        Project createdProject = projectDAO.readProject(project.getIdproject());
        assertEquals("Project A", createdProject.getNameprj());
        assertEquals("Organization A", createdProject.getRelatedorg());
        assertEquals(10, createdProject.getQuota());
    }

    @Test
    void testReadProject() throws SQLException {
        // Insertar proyecto directamente
        int projectId;
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO project (nameprj, relatedorg, quota) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, "Project B");
            ps.setString(2, "Organization B");
            ps.setInt(3, 5);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            assertTrue(rs.next());
            projectId = rs.getInt(1);
        }

        // Leer proyecto
        Project project = projectDAO.readProject(projectId);
        assertEquals("Project B", project.getNameprj());
        assertEquals("Organization B", project.getRelatedorg());
        assertEquals(5, project.getQuota());
    }

    @Test
    void testGetProjectByName() throws SQLException {
        // Insertar proyecto
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO project (nameprj, relatedorg, quota) VALUES (?, ?, ?)")) {
            ps.setString(1, "Project C");
            ps.setString(2, "Organization C");
            ps.setInt(3, 15);
            ps.executeUpdate();
        }

        // Buscar por nombre
        Project project = projectDAO.getProjectByName("Project C");
        assertNotNull(project, "The project should exist.");
        assertEquals("Organization C", project.getRelatedorg());
        assertEquals(15, project.getQuota());
    }

    @Test
    void testUpdateProject() throws SQLException {
        // Insertar proyecto
        Project project = new Project();
        project.setNameprj("Project D");
        project.setRelatedorg("Organization D");
        project.setQuota(20);
        projectDAO.createProject(project);

        // Actualizar proyecto
        project.setRelatedorg("Updated Organization D");
        project.setQuota(25);
        projectDAO.updateProject(project);

        // Verificar actualización
        Project updatedProject = projectDAO.readProject(project.getIdproject());
        assertEquals("Updated Organization D", updatedProject.getRelatedorg());
        assertEquals(25, updatedProject.getQuota());
    }

    @Test
    void testDeleteProject() throws SQLException {
        // Insertar proyecto
        Project project = new Project();
        project.setNameprj("Project E");
        project.setRelatedorg("Organization E");
        project.setQuota(30);
        projectDAO.createProject(project);

        // Eliminar proyecto
        projectDAO.deleteProject(project);

        // Verificar eliminación
        Project deletedProject = projectDAO.getProjectByName("Project E");
        assertNull(deletedProject, "The project should no longer exist in the database.");
    }

    @Test
    void testGetAllProjects() throws SQLException {
        // Insertar múltiples proyectos
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO project (nameprj, relatedorg, quota) VALUES (?, ?, ?)")) {
            ps.setString(1, "Project F");
            ps.setString(2, "Organization F");
            ps.setInt(3, 50);
            ps.executeUpdate();

            ps.setString(1, "Project G");
            ps.setString(2, "Organization G");
            ps.setInt(3, 60);
            ps.executeUpdate();
        }

        // Obtener todos los proyectos
        List<Project> projects = projectDAO.getAllProjects();

        assertTrue(projects.stream().anyMatch(p -> p.getNameprj().equals("Project F")));
        assertTrue(projects.stream().anyMatch(p -> p.getNameprj().equals("Project G")));
    }
}
