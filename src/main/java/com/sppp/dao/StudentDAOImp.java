package com.sppp.dao;

import com.sppp.connection.DBConnection;
import com.sppp.model.Project;
import com.sppp.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa las operaciones CRUD definidas en la interfaz  para el objeto Student, incluye unicamente
 * un String (tableName) como atributo, el cual indica el nombre de la tabla donde se realizaran las operaciones dentro
 * de la base de datos.
 */
public class StudentDAOImp implements StudentDAO{
    private String tableName;

    public StudentDAOImp() {this.tableName = "student";}

    /**
     * Crea un registro de estudiante dentro de la base de datos y retorna la PRIMARY KEY generada en la base de datos
     * @param student Objeto de tipo Student que sera insertado
     * @throws SQLException
     */
    @Override
    public void createStudent(Student student) throws SQLException {
        if(student==null) return;
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "INSERT INTO " + tableName + "(name,lastname,nrc,enrolment) VALUES (?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
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

    /**
     * Consulta el registro de un estudiante junto con su projecto si es que existe (LEFT JOIN)
     * @param id ID que indica la PRIMARY KEY en la base de datos para identificar al estudiante
     * @return returna un objeto de tipo Student con la información leida en la base de datos
     * @throws SQLException
     */
    @Override
    public Student readStudent(int id) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "SELECT name, lastname, nrc, enrolment, nameprj, relatedorg, quota FROM " + tableName +
                " LEFT JOIN project ON "+ tableName + ".idproject = project.idproject WHERE idstudent = ?";
        PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Student student = new Student();
        student.setIdstudent(id);
        if (rs.next()) {
            student.setName(rs.getString(1));
            student.setLastname(rs.getString(2));
            student.setNrc(rs.getString(3));
            student.setEnrolment(rs.getString(4));
            Project project = new Project();
            project.setNameprj(rs.getString(5));
            project.setRelatedorg(rs.getString(6));
            project.setQuota(rs.getInt(7));
            student.setIdproject(project);
        }
        return student;
    }

    /**
     * Obtiene al estudiante dado su nombre sin preguntar por los datos del projecto si es que tiene asigando alguno
     * @param name (String) Nombre del estudiante a buscar en la base de datos
     * @return Un objeto de tipo Student dado el nombre proporcionado o NULL en caso de no encontrarlo
     * @throws SQLException
     */
    @Override
    public Student getStudentByName(String name) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "SELECT * FROM "+ tableName +" WHERE name LIKE CONCAT('%',?,'%')";
        PreparedStatement ps = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
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

    /**
     * Actualiza todas o algunas columnas de un estudiante en la base de datos
     * @param student Objeto de tipo Student con datos actualizados con respecto a alguno ya guardado en la base de datos
     * @throws SQLException
     */
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

    /**
     * Elimina un estudiante de la base de datos y en caso de que haya sido asignado a algun proyecto vuelve a aumentar
     * el cupo de dicho proyecto
     * @param student Objeto de tipo Student a ser eliminado de la base de datos
     * @throws SQLException
     */
    @Override
    public void deleteStudent(Student student) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "DELETE FROM " + tableName + " WHERE name = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, student.getName());
        int affectedRows = ps.executeUpdate();
        if(affectedRows == 1 && student.getIdproject() != null){
            String updateQuotaQuery = "UPDATE project SET quota = quota + 1 WHERE idproject = ?";
            PreparedStatement psQuota = conn.prepareStatement(updateQuotaQuery);
            psQuota.setInt(1, student.getIdproject().getIdproject());
            psQuota.executeUpdate();
        }
    }

    /**
     * Lee cada registro de un estudiante de la base de datos, y si tiene un proyecto tambien lee la informacion de este
     * @return retorna una List<Student> de objetos de tipo Student
     * @throws SQLException
     */
    @Override
    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<Student>();
        Connection conn = DBConnection.getInstance().getConnection();
        String selectQuery = "SELECT idstudent, name, lastname, nrc, enrolment, nameprj, relatedorg, quota FROM "
                + tableName + " LEFT JOIN project ON "+ tableName + ".idproject = project.idproject";
        PreparedStatement stmt = conn.prepareStatement(selectQuery);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Student student = new Student();
            student.setIdstudent(rs.getInt(1));
            student.setName(rs.getString(2));
            student.setLastname(rs.getString(3));
            student.setNrc(rs.getString(4));
            student.setEnrolment(rs.getString(5));
            students.add(student);
            Project project = new Project();
            project.setNameprj(rs.getString(6));
            project.setRelatedorg(rs.getString(7));
            project.setQuota(rs.getInt(8));
            student.setIdproject(project);
        }
        return students;
    }

    /**
     * Asigna un proyecto a un estudiante (Relacion 1-n) donde la PRIMARY KEY del proyecto se agrega como FOREING KEY
     * al estudiante, adicionalmente decrementa el cupo del proyecto
     * @param student Objeto de tipo Student a ser asginado (Relacionado con un projecto en la base de datos)
     * @param project Objeto de tipo Project que sera usado para obtener el ID para relacionarlo al estudiante
     * @throws SQLException
     */
    @Override
    public void assignStudentToProject(Student student, Project project) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "UPDATE " + tableName + " SET idproject = ? WHERE idstudent = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, project.getIdproject());
        ps.setInt(2, student.getIdstudent());

        int affectedRows = ps.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("No se pudo asignar el proyecto al estudiante.");
        }
        if(affectedRows == 1){
            String reduceQuotaQuery = "UPDATE project SET quota = quota - 1 WHERE idproject = ?";
            PreparedStatement psQuota = conn.prepareStatement(reduceQuotaQuery);
            psQuota.setInt(1, project.getIdproject());
            psQuota.executeUpdate();
        }
    }
}