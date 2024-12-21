package com.user.Controllers;

import com.user.views.LoginPanel;
import com.user.models.DatabaseConnection;
import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterController {
    public static void handleRegister(String fullName, String address, String dob, String gender,
                                      String username, String email, String password, JFrame registerFrame) {

        if (fullName.isEmpty() || address.isEmpty() || dob.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields must be filled out", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password.length() < 6) {
            JOptionPane.showMessageDialog(null, "Password must be at least 6 characters long", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            Date parsedDate = dateFormat.parse(dob);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Invalid date format. Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = inputFormat.parse(dob);
            String formattedDate = dateFormat.format(date);
            dob = formattedDate;
        } catch (ParseException e) {
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
