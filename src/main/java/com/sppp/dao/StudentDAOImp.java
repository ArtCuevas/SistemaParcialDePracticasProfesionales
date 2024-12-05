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

public class StudentDAOImp implements StudentDAO{
    private String tableName;

    public StudentDAOImp() {this.tableName = "student";}

    @Override
    public void createStudent(Student student) throws SQLException {
        if(student==null) return;
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "INSERT INTO " + tableName + "(name,lastname,nrc,enrolment) VALUES (?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, student.getName());
        ps.setString(2,student.getLastname());
        ps.setString(3,student.getNrc());
        ps.setString(4,student.getEnrolment());
        int affectedRows = ps.executeUpdate();
        if (affectedRows > 0) {
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                student.setIdstudent(id);
            }
        }
    }

    @Override
    public Student readStudent(int id) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "SELECT name, lastname, nrc, enrolment, nameprj, relatedorg, quota FROM " + tableName +
                " JOIN project ON "+ tableName + ".idproyect = project.idproject WHERE idstudent = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Student student = new Student();
        if (rs.next()) {
            student.setName(rs.getString(1));
            student.setLastname(rs.getString(2));
            student.setNrc(rs.getString(3));
            student.setEnrolment(rs.getString(4));
        }
        Project project = new Project();
        if(rs.next()){
            project.setNameprj(rs.getString(5));
            project.setRelatedorg(rs.getString(6));
            project.setQuota(rs.getInt(7));
        }
        student.setIdproject(project);
        return student;
    }
    @Override
    public Student getStudentByName(String name) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "SELECT * FROM students WHERE name = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, name);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Student student = new Student();
            student.setIdstudent(rs.getInt("idstudent"));
            student.setName(rs.getString("name"));
            student.setLastname(rs.getString("lastname"));
            student.setNrc(rs.getString("nrc"));
            student.setEnrolment(rs.getString("enrolment"));
            return student;
        }
        return null;
    }


    @Override
    public void updateStudent(Student student) throws SQLException {
        if(student==null) return;
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "UPDATE " + tableName + " SET name = ?,lastname = ?, nrc = ?, enrolment = ? " +
                "WHERE idstudent = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, student.getName());
        ps.setString(2,student.getLastname());
        ps.setString(3,student.getNrc());
        ps.setString(4,student.getEnrolment());
        ps.setInt(5, student.getIdstudent());
        ps.executeUpdate();
    }

    @Override
    public void deleteStudent(Student student) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "DELETE FROM " + tableName + " WHERE name = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, student.getName());
        ps.executeUpdate();
    }

    @Override
    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<Student>();
        Connection conn = DBConnection.getInstance().getConnection();
        String selectQuery = "SELECT name, lastname, nrc, enrolment, nameprj, relatedorg, quota FROM " + tableName +
                " JOIN project ON "+ tableName + ".idproyect = project.idproject WHERE idstudent = ?";
        PreparedStatement stmt = conn.prepareStatement(selectQuery);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Student student = new Student();
            student.setName(rs.getString(1));
            student.setLastname(rs.getString(2));
            student.setNrc(rs.getString(3));
            student.setEnrolment(rs.getString(4));
            students.add(student);
            Project project = new Project();
            project.setNameprj(rs.getString(5));
            project.setRelatedorg(rs.getString(6));
            project.setQuota(rs.getInt(7));
            student.setIdproject(project);
        }
        return students;
    }

    @Override
    public void assignStudentToProject(int studentId, int projectId) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "UPDATE " + tableName + " SET project_id = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, projectId);
        ps.setInt(2, studentId);

        int affectedRows = ps.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("No se pudo asignar el proyecto al estudiante.");
        }
    }
}
