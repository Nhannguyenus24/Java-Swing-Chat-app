package com.user.Controllers;

import com.user.views.LoginPanel;
import com.user.models.DatabaseConnection;
import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterController {
    public static void handleRegister(String fullName, String address, String dob, String gender, String username, String email, String password, JFrame registerFrame) {

        if (fullName.isEmpty() || address.isEmpty() || dob.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields must be filled out", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password.length() < 6) {
            JOptionPane.showMessageDialog(null, "Password must be at least 6 characters long", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            Date parsedDate = dateFormat.parse(dob);
            dob = dateFormat.format(parsedDate); // Direct assignment
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Invalid date format. Please use DD/MM/YYYY.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean isRegistered = DatabaseConnection.registerUser(fullName, username, address, email, dob, password, gender);
        if (isRegistered) {
            JOptionPane.showMessageDialog(null, "Registration successful", "Success", JOptionPane.INFORMATION_MESSAGE);
            registerFrame.dispose();
            LoginPanel loginFrame = new LoginPanel();
            loginFrame.setVisible(true);
            loginFrame.pack();
            loginFrame.setLocationRelativeTo(null);
        } else {
            JOptionPane.showMessageDialog(null, "Registration failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
