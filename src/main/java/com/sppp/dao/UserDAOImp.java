package com.sppp.dao;

import com.sppp.connection.DBConnection;
import com.sppp.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa las operaciones CRUD definidas en la interfaz  para el objeto User, incluye unicamente
 * un String (tableName) como atributo, el cual indica el nombre de la tabla donde se realizaran las operaciones dentro
 * de la base de datos
 */
public class UserDAOImp implements UserDAO{
    private String tableName;

    public UserDAOImp() {this.tableName = "user";}

    /**
     * Crea un usuario con credenciales dentro de la base de datos
     * @param user Objeto de tipo User a registrar
     * @throws SQLException
     */
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

    /**
     * Lee informacion de un usuario de la base de datos
     * @see com.sppp.gui.Login.LoginAction#authenticate(String, String)
     * @param id id en la base de datos del usuario administrador (1)
     * @return retorna las credenciales del usuario registrado para ser comparadas con las ingresadas
     * @throws SQLException
     */
    @Override
    public User readUser(int id) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "SELECT username,password FROM " + tableName + " WHERE iduser = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        User user = new User();
        if (rs.next()) {
            user.setUsername(rs.getString(1));
            user.setPassword(rs.getString(2));
        }
        return user;
    }

    /**
     * Actualiza los datos de un usuario registrado (No utilizado dentro del programa)
     * Util para poder cambiar claves
     * @param user Objeto de tipo User a ser actualizado
     * @throws SQLException
     */
    @Override
    public void updateUser(User user) throws SQLException {
        if(user==null) return;
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "UPDATE " + tableName + " SET username = ? , password = ? WHERE iduser = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, user.getUsername());
        ps.setInt(3, user.getIduser());
        ps.setString(2, user.getPassword());
        ps.executeUpdate();
    }

    /**
     * Elimina un usuario del sistema
     * @param user Objeto de tipo User a ser eliminado de la base de datos
     * @throws SQLException
     */
    @Override
    public void deleteUser(User user) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String query = "DELETE FROM " + tableName + " WHERE username = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, user.getUsername());
        ps.executeUpdate();
    }

    /**
     * Lista todos los usuario en el sistema
     * @return Retorna una List de objetos de tipo User
     * @throws SQLException
     */
    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        Connection conn = DBConnection.getInstance().getConnection();
        String selectQuery = "SELECT username FROM " + tableName;
        PreparedStatement stmt = conn.prepareStatement(selectQuery);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            User user = new User();
            user.setUsername(rs.getString(1));
            users.add(user);
        }
        return users;
    }
}