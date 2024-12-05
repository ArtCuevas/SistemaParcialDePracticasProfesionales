package com.sppp.dao;

import com.sppp.model.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentDAO {
    public void createStudent(Student student) throws SQLException;
    public Student readStudent(int id) throws SQLException;
    public void updateStudent(Student student) throws SQLException;
    public void deleteStudent(Student student) throws SQLException;
    public List<Student> getAllStudents() throws SQLException;
    public void assignStudentToProject(int studentId, int projectId) throws SQLException;
}