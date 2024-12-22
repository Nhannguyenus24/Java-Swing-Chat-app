package com.user.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
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
    public static boolean validateLogin(String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean isValid = false;
        Integer userId = -1;
        try {
            connection = getConnection();

            String sql = "SELECT user_id FROM user WHERE username = ? AND password = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                userId = resultSet.getInt("user_id");
                isValid = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (isValid){
            String query = "INSERT INTO login_history (user_id) VALUES (?)";

            try {
                connection = getConnection();
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, userId);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isValid;
    }
    public static int getUserID(String username, String password) {
        String sql = "SELECT user_id FROM user WHERE username = ? AND password = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static boolean registerUser(String fullName, String userName, String address, String email, String dateOfBirth, String password, String gender) {
        String query = "INSERT INTO user (full_name, username, address, email, dob, password, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, fullName);
            stmt.setString(2, userName);
            stmt.setString(3, address);
            stmt.setString(4, email);
            stmt.setString(5, dateOfBirth);
            stmt.setString(6, password); // Hash password for security
            stmt.setString(7, gender);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}