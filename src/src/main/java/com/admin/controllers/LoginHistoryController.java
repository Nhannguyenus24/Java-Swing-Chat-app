package com.admin.controllers;

import com.admin.models.UserModel;
import com.admin.views.panels.LoginHistoryPanel;

import java.util.List;

public class LoginHistoryController {
    private final LoginHistoryPanel loginHistoryPanel;

    public LoginHistoryController(LoginHistoryPanel panel) {
        this.loginHistoryPanel = panel;
        loadLoginHistory();
    }

    private void loadLoginHistory() {
        List<Object[]> loginHistory = UserModel.getAllLoginHistory();
        this.updateLoginHistory(loginHistory.toArray(new Object[0][]));
    }

    public void updateLoginHistory(Object[][] data) {
        loginHistoryPanel.model.setRowCount(0);
        for (Object[] row : data) {
            loginHistoryPanel.model.addRow(row);
        }
    }
}
