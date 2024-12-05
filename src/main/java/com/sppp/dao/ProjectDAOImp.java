package com.sppp.dao;

import com.sppp.connection.DBConnection;
import com.sppp.model.Project;
import com.sppp.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAOImp implements ProjectDAO{

    private String tableName;

    public ProjectDAOImp() {this.tableName="project";}

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

    @Override
    public Project readProject(int id) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "SELECT nameprj FROM " + tableName + " WHERE idproject = ?";
        PreparedStatement ps = conn.prepareStatement(query);
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

    @Override
    public Project getProjectByName(String nameprj) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "SELECT * FROM students WHERE nameprj = ?";
        PreparedStatement ps = conn.prepareStatement(query);
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

    @Override
    public void deletePokemon(Project project) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "DELETE FROM " + tableName + " WHERE nameprj = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, project.getNameprj());
        ps.executeUpdate();
    }

    @Override
    public List<Project> getAllProjects() throws SQLException {
        List<Project> projects = new ArrayList<Project>();
        Connection conn = DBConnection.getInstance().getConnection();
        String selectQuery = "SELECT idproject, nameprj, relatedorg,quota FROM " + tableName;
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
