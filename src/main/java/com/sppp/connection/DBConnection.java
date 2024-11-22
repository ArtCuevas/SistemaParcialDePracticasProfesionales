package com.sppp.connection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

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