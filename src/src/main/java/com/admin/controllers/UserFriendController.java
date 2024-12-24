package com.admin.controllers;

import com.admin.models.UserModel;
import com.admin.views.panels.UserFriendPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.List;

public class UserFriendController {
    private UserFriendPanel userFriendPanel;
    private DefaultTableModel userTableModel;

    public UserFriendController(UserFriendPanel panel) {
        this.userFriendPanel = panel;
        this.userTableModel = panel.userTableModel;

        loadAllUsers();
        addEventListeners();
    }

    private void loadAllUsers() {
        List<Object[]> users = UserModel.getAllUsersWithFriends();

        userTableModel.setRowCount(0);
        for (Object[] user : users) {
            userTableModel.addRow(user);
        }
    }

    private void addEventListeners() {
        userFriendPanel.filterButton.addActionListener(this::filterUsersAction);
    }

    private void filterUsersAction(ActionEvent e) {
        String name = userFriendPanel.searchField.getText().trim();
        int directFriends = (int) userFriendPanel.friendCountSpinner.getValue();
        String comparison = switch (userFriendPanel.comparisonBox.getSelectedIndex()) {
            case 0 -> "=";
            case 1 -> ">";
            case 2 -> "<";
            default -> "=";
        };

        List<Object[]> filteredUsers = UserModel.filterUsers(name, directFriends, comparison);

        userTableModel.setRowCount(0);
        for (Object[] user : filteredUsers) {
            userTableModel.addRow(user);
        }
    }
}
