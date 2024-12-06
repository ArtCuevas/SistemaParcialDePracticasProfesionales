package com.sppp.dao;

import com.sppp.connection.DBConnection;
import com.sppp.model.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa las operaciones CRUD definidas en la interfaz  para el objeto Project, incluye unicamente
 * un String (tableName) como atributo, el cual indica el nombre de la tabla donde se realizaran las operaciones dentro
 * de la base de datos
 */
public class ProjectDAOImp implements ProjectDAO{

    private String tableName;

    public ProjectDAOImp() {this.tableName="project";}

    /**
     * Crea un registro de Project en la base de datos, adicionalmente solicita las claves generadas, es decir el id
     * autoincremental, el cual sera util para futuras operacioens dentro de la app
     * @param project Project que se desea insertar a la base de datos
     * @throws SQLException
     */
    @Override
    public void createProject(Project project) throws SQLException {
        if(project==null) return;
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "INSERT INTO " + tableName + "(nameprj, relatedorg, quota) VALUES (?,?,?)";
        PreparedStatement ps = conn.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, project.getNameprj());
        ps.setString(2,project.getRelatedorg());
        ps.setInt(3,project.getQuota());
        int affectedRows = ps.executeUpdate();
        if (affectedRows > 0) {
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                project.setIdproject(id);
            }
        }
    }

    /**
     * Lee un registro de Project de la base de datos dado su ID
     * @param id idproject (PRIMARY KEY) en la base de datos
     * @return Un objecto de tipo Project
     * @throws SQLException
     */
    @Override
    public Project readProject(int id) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "SELECT idproject, nameprj, relatedorg, quota FROM " + tableName + " WHERE idproject = ?";
        PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Project project = new Project();
        if (rs.next()) {
            project.setIdproject(rs.getInt(1));
            project.setNameprj(rs.getString(2));
            project.setRelatedorg(rs.getString(3));
            project.setQuota(rs.getInt(4));
        }
        return project;
    }
    /**
     * Lee un registro de Project de la base de datos dado su nombre utilizando comodines
     * @param nameprj nameprj (nombre del proyecto) en la base de datos VARCHAR
     * @return Un objecto de tipo Project
     * @throws SQLException
     */
    @Override
    public Project getProjectByName(String nameprj) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "SELECT * FROM " + tableName + " WHERE nameprj LIKE CONCAT('%',?,'%')";
        PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, nameprj);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Project project = new Project();
            project.setIdproject(rs.getInt("idproject"));
            project.setNameprj(rs.getString("nameprj"));
            project.setRelatedorg(rs.getString("relatedorg"));
            project.setQuota(rs.getInt("quota"));
            return project;
        }
        return null;
    }

    /**
     * Actualiza los registros de un proyecto en la base de datos
     * @param project objeto de tipo Project ya existente en la base de datos, con una o varias columnas actualizadas
     * @throws SQLException
     */
    @Override
    public void updateProject(Project project) throws SQLException {
        if(project==null) return;
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "UPDATE " + tableName + " SET nameprj = ?, relatedorg = ?, quota = ?  WHERE idproject = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, project.getNameprj());
        ps.setString(2,project.getRelatedorg());
        ps.setInt(3,project.getQuota());
        ps.setInt(4, project.getIdproject());
        ps.executeUpdate();
    }

    /**
     * Elimina un registro en la base de datos siempre y cuando no tenga a ningun alumno asignado
     * Dicha alerta se manda al usuario desde la GUI
     * @param project Objeto de tipo Project a eliminar
     * @throws SQLException
     */
    @Override
    public void deleteProject(Project project) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "DELETE FROM " + tableName + " WHERE nameprj = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, project.getNameprj());
        ps.executeUpdate();
    }

    /**
     * Lee todos los registros de proyectos en la base de datos
     * @return projects Una lista de objetos tipo Project con los registros de la base de datos
     * @throws SQLException
     */
    @Override
    public List<Project> getAllProjects() throws SQLException {
        List<Project> projects = new ArrayList<>();
        Connection conn = DBConnection.getInstance().getConnection();
        String selectQuery = "SELECT idproject, nameprj, relatedorg, quota FROM " + tableName;
        PreparedStatement stmt = conn.prepareStatement(selectQuery);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Project project = new Project();
            project.setIdproject(rs.getInt(1));
            project.setNameprj(rs.getString(2));
            project.setRelatedorg(rs.getString(3));
            project.setQuota(rs.getInt(4));
            projects.add(project);
        }
        return projects;
    }
}
