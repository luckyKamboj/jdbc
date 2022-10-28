package com.company.database.config;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Contains different ways of connection to database.
 */
public class DBUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/jdbc?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "^H@84125Lucky";
    private static final String DRIVER_MANAGER = "java.sql.DriverManager";
    private static final Logger LOGGER = Logger.getLogger(DBUtil.class.getCanonicalName());
    private static final String FILE_PATH = "resource/db_credentials.properties";

    private DBUtil(){}
    public static Connection getConnectionWithStringArgConstructor(){
        try {
            Class.forName(DRIVER_MANAGER);
            return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.severe(e.getMessage());
        }
        return null;
    }

    public static Connection getConnectionWithPropertiesFile(){
        try (FileReader fileReader = new FileReader(FILE_PATH)){
            Class.forName(DRIVER_MANAGER);
            Properties properties = new Properties();
            properties.load(fileReader);
            return DriverManager.getConnection(DB_URL, properties);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            LOGGER.severe(e.getMessage());
        }
        return null;
    }

    public static Connection getConnectionWithURLOnly(){
        try {
            Class.forName(DRIVER_MANAGER);
            String userJoiner = "&user=";
            String passwordJoiner = "&password=";
            String url = DB_URL + userJoiner + USERNAME + passwordJoiner +  PASSWORD;
            return DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.severe(e.getMessage());
        }
        return null;
    }

    public static Connection getConnectionWithProperties(){
        try {
            Class.forName(DRIVER_MANAGER);
            Properties properties = new Properties();
            properties.put("User", USERNAME);
            properties.put("password", PASSWORD);
            return DriverManager.getConnection(DB_URL, properties );
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
