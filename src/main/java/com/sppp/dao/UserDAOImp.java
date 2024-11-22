package com.sppp.dao;

import com.sppp.connection.DBConnection;
import com.sppp.model.Student;
import com.sppp.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImp implements UserDAO{
    private String tableName;

    public UserDAOImp() {this.tableName = "user";}

    @Override
    public void createUser(User user) throws SQLException {
        if(user==null) return;
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "INSERT INTO " + tableName + "(username,password) VALUES (?,?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());
        ps.execute();
    }

    @Override
    public User readUser(int id) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "SELECT username FROM " + tableName + " WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        User user = new User();
        if (rs.next()) {
            user.setUsername(rs.getString(1));
        }
        return user;
    }

    @Override
    public void updateUser(User user) throws SQLException {
        if(user==null) return;
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "UPDATE " + tableName + " SET username = ? , password = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, user.getUsername());
        ps.setInt(3, user.getIduser());
        ps.setString(2, user.getPassword());
        ps.executeUpdate();
    }

    @Override
    public void deleteUser(User user) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "DELETE FROM " + tableName + " WHERE username = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, user.getUsername());
        ps.executeUpdate();
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> students = new ArrayList<User>();
        Connection conn = DBConnection.getInstance().getConnection();
        String selectQuery = "SELECT username FROM " + tableName;
        PreparedStatement stmt = conn.prepareStatement(selectQuery);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            User user = new User();
            user.setUsername(rs.getString(1));
            students.add(user);
        }
        return students;
    }
}