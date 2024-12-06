package com.sppp.connection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * La clase DBConnection usa el patron Singleton para abrir una conexion a la base de datos
 * ademas en el constructor lee un archivo config.properties para evitar tener informacion sensible dentro del codigo
 * los atributos necesarios son una DBConnection(la misma clase), un objeto tipo Connection y 3 String, donde se puedan
 * guardar la URL de conexion a la base de datos, el usuario y contrasena correspondientes.
 */
public class DBConnection {
        private static DBConnection instance;
        private Connection connection;
        private static String URL ;
        private static  String USER;
        private static  String PASSWORD;

        private DBConnection()throws SQLException {
            try{
                Properties properties = new Properties();
                FileInputStream input = new FileInputStream("config.properties");
                properties.load(input);
                URL = properties.getProperty("DB_URL");
                USER = properties.getProperty("DB_USER");
                PASSWORD = properties.getProperty("DB_PASSWORD");
                input.close();
                this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    /**
     * El metodo getInstance sigue el patron de diseno Singleton, donde retorna una misma instancia de la conexion
     * @return Una instancia de coneccion a la base de datos,y solo si aun no esta creada o se cerro, crea una nueva
     * @throws SQLException
     */
        public static DBConnection getInstance() throws SQLException{
            if(instance==null || instance.getConnection().isClosed()){
                instance = new DBConnection();
            }
            return instance;
        }

        public Connection getConnection() {
            return connection;
        }
}