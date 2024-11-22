package com.sppp.dao;

import com.sppp.connection.DBConnection;
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
        String query = "INSERT INTO " + tableName + "(name,lastname,nrc,enrolment) VALUES (?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, student.getName());
        ps.setString(2,student.getLastname());
        ps.setString(3,student.getNrc());
        ps.setString(4,student.getEnrolment());
        ps.execute();
    }

    @Override
    public Student readStudent(int id) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "SELECT name, lastname, nrc, enrolment FROM " + tableName + " WHERE idstudent = ?";
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
        return student;
    }

    @Override
    public void updateStudent(Student student) throws SQLException {
        if(student==null) return;
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "UPDATE " + tableName + " SET name = ? WHERE idstudent = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, student.getName());
        ps.setInt(2, student.getIdstudent());
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
        String selectQuery = "SELECT name, lastname, nrc, enrolment FROM " + tableName;
        PreparedStatement stmt = conn.prepareStatement(selectQuery);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Student student = new Student();
            student.setName(rs.getString(1));
            student.setLastname(rs.getString(2));
            student.setNrc(rs.getString(3));
            student.setEnrolment(rs.getString(4));
            students.add(student);
        }
        return students;
    }
}
