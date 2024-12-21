package com.user.Controllers;

import com.user.models.DatabaseConnection;
import com.user.models.UserModel;
import com.user.views.ChatUI;

import javax.swing.*;

public class LogInController {
    public static void handleLogin(String username, String password, JFrame frame) {

        boolean isValid = DatabaseConnection.validateLogin(username, password);

        if (isValid) {
            JOptionPane.showMessageDialog(null, "Login Successful");
            int user_id = DatabaseConnection.getUserID(username, password);
            UserModel user = new UserModel(user_id);
            ChatUI ui = new ChatUI(user);
            ui.setVisible(true);
            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid login credentials", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}