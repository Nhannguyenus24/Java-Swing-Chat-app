package org;
import java.sql.Connection;
import java.sql.SQLException;
import org.database.DatabaseConnection;
import org.ui.LoginPanel;
public class Main {
    public static void main(String[] args) {
        try {
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();

            Connection connection = dbConnection.getConnection();

            System.out.println("Connected to the database successfully.");
            LoginPanel loginPanel = new LoginPanel();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
