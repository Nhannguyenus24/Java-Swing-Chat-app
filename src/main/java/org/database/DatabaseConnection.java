package org.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/user_management";
    private static final String USER = "root";
    private static final String PASSWORD = "nhannhan2004";

    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connection established successfully.");
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC Driver not found.", e);
        }
    }

    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                    instance = new DatabaseConnection();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

//    public static boolean registerUser(String fullName, String userName, String address, String email,
//                                       String dateOfBirth, String password, String gender) {
//        // Establish a connection to the database
//        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
//            // Create an SQL query to insert user data
//            String query = "INSERT INTO user (full_name, user_name, address, email, date_of_birth, password, gender) " +
//                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
//
//            try (PreparedStatement stmt = conn.prepareStatement(query)) {
//                // Set the parameters for the query
//                stmt.setString(1, fullName);
//                stmt.setString(2, userName);
//                stmt.setString(3, address);
//                stmt.setString(4, email);
//                stmt.setString(5, dateOfBirth);
//                stmt.setString(6, password);
//                stmt.setString(7, gender);
//
//                int rowsInserted = stmt.executeUpdate();
//
//                return rowsInserted > 0;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
}
