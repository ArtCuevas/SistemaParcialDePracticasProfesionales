package com.sppp.model;
/**
 *  La clase User representa y contiene la informacion de un usuario, misma que se almacenara en la base de datos
 *  y sera usada en la GUI para interacturar con el usuario
 */
public class User {
    private int iduser;
    private String username;
    private String password;

    public User() {}

    public User(int iduser, String username, String password) {
        this.iduser = iduser;
        this.username = username;
        this.password = password;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
