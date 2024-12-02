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

        int option = JOptionPane.showConfirmDialog(null, panel, "Nhập thông tin người dùng",
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

            if (username.isEmpty() || fullName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Tên đăng nhập và họ tên là bắt buộc.");
            } else {

                boolean success = UserModel.addUser(username, fullName, address, dob, gender, status, email, password);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Người dùng đã được thêm thành công!");
                    loadUserData();
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm người dùng thất bại.");
                }
            }
        }
    }

    private void deleteUserAction(ActionEvent e) {
        int viewRow = this.userPanel.userTable.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một người dùng để xóa.");
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
                JOptionPane.showMessageDialog(null, "Xóa người dùng thành công!");
                loadUserData();
            } else {
                JOptionPane.showMessageDialog(null, "Xóa người dùng thất bại.");
            }
        }
    }

    private void updateUserAction(ActionEvent e) {
        int viewRow = this.userPanel.userTable.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một người dùng để cập nhật.");
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

        int option = JOptionPane.showConfirmDialog(null, panel, "Cập nhật thông tin người dùng",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String fullName = panel.fullNameField.getText().trim();
            String address = panel.addressField.getText().trim();
            String dob = panel.dobField.getText().trim();
            String gender = (String) panel.genderComboBox.getSelectedItem();
            String email = panel.emailField.getText().trim();

            if (fullName.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Họ tên và Email là bắt buộc.");
            } else {

                boolean success = UserModel.updateUser(username, fullName, address, dob, gender, email);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Cập nhật thông tin người dùng thành công!");
                    loadUserData();
                } else {
                    JOptionPane.showMessageDialog(null, "Cập nhật thông tin người dùng thất bại.");
                }
            }
        }
    }

    private void toggleLockUnlockAction(ActionEvent e) {
        int viewRow = this.userPanel.userTable.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một người dùng để khóa/mở khóa.");
            return;
        }
        int modelRow = this.userPanel.userTable.convertRowIndexToModel(viewRow);

        String username = (String) this.userPanel.model.getValueAt(modelRow, 0);
        String status = (String) this.userPanel.model.getValueAt(modelRow, 6);

        boolean success = UserModel.toggleLockUnlockStatus(username, status);
        if (success) {
            if ("Locked".equals(status))
                JOptionPane.showMessageDialog(null, "Mở khóa người dùng thành công!");
            else
                JOptionPane.showMessageDialog(null, "Khóa người dùng thành công!");
            loadUserData();
        } else {
            if ("Locked".equals(status))
                JOptionPane.showMessageDialog(null, "Mở khóa người dùng thất bại.");
            else
                JOptionPane.showMessageDialog(null, "Khóa người dùng thất bại.");
        }
    }

    private void resetPasswordAction(ActionEvent e) {
        int viewRow = this.userPanel.userTable.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một người dùng để cập nhật mật khẩu.");
            return;
        }
        int modelRow = this.userPanel.userTable.convertRowIndexToModel(viewRow);

        String username = (String) this.userPanel.model.getValueAt(modelRow, 0);
        String currentPassword = UserModel.getPasswordByUsername(username);
        if (currentPassword == null) {
            JOptionPane.showMessageDialog(null, "Không thể lấy thông tin mật khẩu người dùng.");
            return;
        }

        ResetPasswordPanel panel = new ResetPasswordPanel();

        int option = JOptionPane.showConfirmDialog(null, panel, "Đặt lại mật khẩu",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String oldPassword = new String(panel.oldPasswordField.getPassword());
            String newPassword = new String(panel.newPasswordField.getPassword());
            String confirmPassword = new String(panel.confirmPasswordField.getPassword());

            if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ các trường.");
                return;
            }

            if (!oldPassword.equals(currentPassword)) {
                JOptionPane.showMessageDialog(null, "Mật khẩu cũ không chính xác.");
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(null, "Mật khẩu mới và xác nhận mật khẩu không khớp.");
                return;
            }

            boolean success = UserModel.resetPassword(username, newPassword);
            if (success) {
                JOptionPane.showMessageDialog(null, "Cập nhật mật khẩu người dùng thành công!");
                loadUserData();
            } else {
                JOptionPane.showMessageDialog(null, "Cập nhật mật khẩu thất bại.");
            }
        }
    }

    public void viewHistoryAction(ActionEvent e) {
        int viewRow = this.userPanel.userTable.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một người dùng để xem lịch sử đăng nhập.");
            return;
        }
        int modelRow = this.userPanel.userTable.convertRowIndexToModel(viewRow);

        String username = (String) this.userPanel.model.getValueAt(modelRow, 0);
        List<Object[]> history = UserModel.getLoginHistoryByUsername(username);
        if (history.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không có lịch sử đăng nhập nào cho người dùng này.");
            return;
        }
        new HistoryDialog(history);
    }

    public void viewFriendsAction(ActionEvent e) {
        int viewRow = this.userPanel.userTable.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một người dùng để xem danh sách bạn bè.");
            return;
        }
        int modelRow = this.userPanel.userTable.convertRowIndexToModel(viewRow);

        String username = (String) this.userPanel.model.getValueAt(modelRow, 0);
        List<Object[]> friends = UserModel.getFriendListByUsername(username);
        if (friends.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Người dùng này chưa có bạn bè nào.");
            return;
        }

        new FriendsDialog(friends);
    }

}