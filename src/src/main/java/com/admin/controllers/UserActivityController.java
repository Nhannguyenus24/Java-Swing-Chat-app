package com.admin.controllers;

import com.admin.models.UserActivityModel;
import com.admin.views.panels.UserActivityPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.util.List;

public class UserActivityController {
    public UserActivityPanel userActivityPanel;
    public List<Object[]> userActivities;
    public DefaultTableModel userActivityModel;

    public UserActivityController(UserActivityPanel panel) {
        this.userActivityPanel = panel;
        this.loadUserActivityData();
        this.addEventListeners();
    }

    private void loadUserActivityData() {

        userActivities = UserActivityModel.getAllUserActivities();
        userActivityModel = this.userActivityPanel.userTableModel;

        userActivityModel.setRowCount(0);
        for (Object[] activity : userActivities) {
            userActivityModel.addRow(activity);
        }
    }

    private void addEventListeners() {
        this.userActivityPanel.filterButton.addActionListener(this::filterUserActivitiesAction);
    }

    private void filterUserActivitiesAction(ActionEvent e) {

        String name = this.userActivityPanel.searchField.getText().trim();
        Date startDate = new Date(((java.util.Date) this.userActivityPanel.startDateSpinner.getValue()).getTime());
        Date endDate = new Date(((java.util.Date) this.userActivityPanel.endDateSpinner.getValue()).getTime());
        int activityCount = (int) this.userActivityPanel.activityCountSpinner.getValue();

        String comparison = switch (this.userActivityPanel.comparisonBox.getSelectedIndex()) {
            case 0 -> "=";
            case 1 -> ">";
            case 2 -> "<";
            default -> "=";
        };

        List<Object[]> filteredResults = UserActivityModel.filterUserActivity(name, startDate.toString(),
                endDate.toString(), activityCount, comparison);

        userActivityModel.setRowCount(0);
        for (Object[] row : filteredResults) {
            userActivityModel.addRow(row);
        }
    }
}
