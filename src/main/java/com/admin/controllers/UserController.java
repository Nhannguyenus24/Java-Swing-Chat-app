package com.admin.controllers;

import com.admin.models.UserModel;
import com.admin.views.dialogs.FriendsDialog;
import com.admin.views.dialogs.HistoryDialog;
import com.admin.views.panels.AddUserPanel;
import com.admin.views.panels.ResetPasswordPanel;
import com.admin.views.panels.UpdateUserPanel;
import com.admin.views.panels.UserManagementPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.util.*;

public class UserController {
    public UserManagementPanel userPanel;
    public List<Object[]> users;
    public DefaultTableModel model;

    public UserController(UserManagementPanel panel) {
        this.userPanel = panel;
        this.loadUserData();
        this.addEventListeners();
    }

    private void loadUserData() {
        users = UserModel.getAllUsers();
        model = this.userPanel.model;
        model.setRowCount(0);
        for (Object[] user : this.users) {
            model.addRow(user);
        }
    }

    private void addEventListeners() {
        this.userPanel.searchButton.addActionListener(this::searchUserAction);
        this.userPanel.addButton.addActionListener(this::addUserAction);
        this.userPanel.deleteButton.addActionListener(this::deleteUserAction);
        this.userPanel.updateButton.addActionListener(this::updateUserAction);
        this.userPanel.toggleLockButton.addActionListener(this::toggleLockUnlockAction);
        this.userPanel.resetPasswordButton.addActionListener(this::resetPasswordAction);
        this.userPanel.viewHistoryButton.addActionListener(this::viewHistoryAction);
        this.userPanel.viewFriendsButton.addActionListener(this::viewFriendsAction);
    }

    private void searchUserAction(ActionEvent e) {
        String selectedCriteria = (String) this.userPanel.searchCriteriaComboBox.getSelectedItem();
        String keyword = this.userPanel.searchField.getText();
        List<Object[]> searchResults = UserModel.searchUsers(selectedCriteria, keyword);

        model.setRowCount(0);
        for (Object[] row : searchResults)
            model.addRow(row);
    }

    private void addUserAction(ActionEvent e) {
        AddUserPanel panel = new AddUserPanel();

        int option = JOptionPane.showConfirmDialog(null, panel, "Enter user information",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String username = panel.usernameField.getText().trim();
            String fullName = panel.fullNameField.getText().trim();
            String address = panel.addressField.getText().trim();
            String dob = panel.dobField.getText().trim();
            String gender = (String) panel.genderComboBox.getSelectedItem();
            String status = (String) panel.statusComboBox.getSelectedItem();
            String email = panel.emailField.getText().trim();
            String password = new String(panel.passwordField.getPassword());

            if (username.isEmpty() || fullName.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "username, full name and password are required");
            } else {

                boolean success = UserModel.addUser(username, fullName, address, dob, gender, status, email, password);
                if (success) {
                    JOptionPane.showMessageDialog(null, "The user has been successfully added");
                    loadUserData();
                } else {
                    JOptionPane.showMessageDialog(null, "User add failed");
                }
            }
        }
    }

    private void deleteUserAction(ActionEvent e) {
        int viewRow = this.userPanel.userTable.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a user to delete");
            return;
        }
        int modelRow = this.userPanel.userTable.convertRowIndexToModel(viewRow);

        String username = (String) this.userPanel.model.getValueAt(modelRow, 0);

        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Bạn có chắc chắn muốn xóa người dùng \"" + username + "\"?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = UserModel.deleteUser(username);
            if (success) {
                JOptionPane.showMessageDialog(null, "User deleted successfully!");
                loadUserData();
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete user.");
            }
        }
    }

    private void updateUserAction(ActionEvent e) {
        int viewRow = this.userPanel.userTable.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a user to update.");
            return;
        }

        int modelRow = this.userPanel.userTable.convertRowIndexToModel(viewRow);

        String username = (String) this.userPanel.model.getValueAt(modelRow, 0);
        String currentFullName = (String) this.userPanel.model.getValueAt(modelRow, 1);
        String currentAddress = (String) this.userPanel.model.getValueAt(modelRow, 2);
        String currentDob = (String) this.userPanel.model.getValueAt(modelRow, 3);
        String currentGender = (String) this.userPanel.model.getValueAt(modelRow, 4);
        String currentEmail = (String) this.userPanel.model.getValueAt(modelRow, 5);

        UpdateUserPanel panel = new UpdateUserPanel(currentFullName, currentAddress, currentDob,
                currentGender, currentEmail);

        int option = JOptionPane.showConfirmDialog(null, panel, "Update user information",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String fullName = panel.fullNameField.getText().trim();
            String address = panel.addressField.getText().trim();
            String dob = panel.dobField.getText().trim();
            String gender = (String) panel.genderComboBox.getSelectedItem();
            String email = panel.emailField.getText().trim();

            if (fullName.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Full name and email are required.");
            } else {

                boolean success = UserModel.updateUser(username, fullName, address, dob, gender, email);
                if (success) {
                    JOptionPane.showMessageDialog(null, "User information updated successfully!");
                    loadUserData();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update user information.");
                }
            }
        }
    }

    private void toggleLockUnlockAction(ActionEvent e) {
        int viewRow = this.userPanel.userTable.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a user to lock/unlock.");
            return;
        }
        int modelRow = this.userPanel.userTable.convertRowIndexToModel(viewRow);

        String username = (String) this.userPanel.model.getValueAt(modelRow, 0);
        String status = (String) this.userPanel.model.getValueAt(modelRow, 6);

        boolean success = UserModel.toggleLockUnlockStatus(username, status);
        if (success) {
            if ("locked".equals(status))
                JOptionPane.showMessageDialog(null, "User unlocked successfully!");
            else
                JOptionPane.showMessageDialog(null, "User locked successfully!");
            loadUserData();
        } else {
            if ("locked".equals(status))
                JOptionPane.showMessageDialog(null, "Failed to unlock user.");
            else
                JOptionPane.showMessageDialog(null, "Failed to lock user.");
        }
    }

    private void resetPasswordAction(ActionEvent e) {
        int viewRow = this.userPanel.userTable.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a user to update the password.");
            return;
        }
        int modelRow = this.userPanel.userTable.convertRowIndexToModel(viewRow);

        String username = (String) this.userPanel.model.getValueAt(modelRow, 0);
        String currentPassword = UserModel.getPasswordByUsername(username);
        if (currentPassword == null) {
            JOptionPane.showMessageDialog(null, "Unable to retrieve user password information.");
            return;
        }

        ResetPasswordPanel panel = new ResetPasswordPanel();

        int option = JOptionPane.showConfirmDialog(null, panel, "Reset password",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String oldPassword = new String(panel.oldPasswordField.getPassword());
            String newPassword = new String(panel.newPasswordField.getPassword());
            String confirmPassword = new String(panel.confirmPasswordField.getPassword());

            if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all the fields.");
                return;
            }

            if (!oldPassword.equals(currentPassword)) {
                JOptionPane.showMessageDialog(null, "The old password is incorrect.");
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(null, "The new password and confirm password do not match.");
                return;
            }

            boolean success = UserModel.resetPassword(username, newPassword);
            if (success) {
                JOptionPane.showMessageDialog(null, "User password updated successfully!");
                loadUserData();
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update password.");
            }
        }
    }

    public void viewHistoryAction(ActionEvent e) {
        int viewRow = this.userPanel.userTable.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a user to view the login history.");
            return;
        }
        int modelRow = this.userPanel.userTable.convertRowIndexToModel(viewRow);

        String username = (String) this.userPanel.model.getValueAt(modelRow, 0);
        List<Object[]> history = UserModel.getLoginHistoryByUsername(username);
        if (history.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There is no login history for this user.");
            return;
        }
        new HistoryDialog(history);
    }

    public void viewFriendsAction(ActionEvent e) {
        int viewRow = this.userPanel.userTable.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a user to view the friend list.");
            return;
        }
        int modelRow = this.userPanel.userTable.convertRowIndexToModel(viewRow);

        String username = (String) this.userPanel.model.getValueAt(modelRow, 0);
        List<Object[]> friends = UserModel.getFriendListByUsername(username);
        if (friends.isEmpty()) {
            JOptionPane.showMessageDialog(null, "This user has no friends.");
            return;
        }

        new FriendsDialog(friends);
    }

}