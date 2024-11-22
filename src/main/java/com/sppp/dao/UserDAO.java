package com.sppp.dao;

import com.sppp.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO{
    public void createUser(User user) throws SQLException;
    public User readUser(int id) throws SQLException;
    public void updateUser(User user) throws SQLException;
    public void deleteUser(User user) throws SQLException;
    public List<User> getAllUsers() throws SQLException;
}