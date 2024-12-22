package com.admin.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static final String CONFIG_FILE = "dbconfig.properties";

    public static Connection getConnection() throws SQLException {
        Properties properties = new Properties();

        File externalConfigFile = new File(CONFIG_FILE);
        if (externalConfigFile.exists()) {
            try (FileInputStream fis = new FileInputStream(externalConfigFile)) {
                properties.load(fis);
                System.out.println("Loaded configuration from external file.");
            } catch (IOException e) {
                throw new SQLException("Failed to load external configuration file.", e);
            }
        } else {

            try (InputStream is = DatabaseConnection.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
                if (is == null) {
                    throw new SQLException("Configuration file not found in resources or external location.");
                }
                properties.load(is);
                System.out.println("Loaded configuration from resources.");
            } catch (IOException e) {
                throw new SQLException("Failed to load configuration file.", e);
            }
        }

        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        if (url == null || user == null) {
            throw new SQLException("Database URL and user must be specified in the configuration file.");
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC Driver not found.", e);
        }

        return DriverManager.getConnection(url, user, password);
    }
}
