package com.admin.views;

import com.admin.controllers.*;
import com.admin.views.panels.*;
import javax.swing.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        this.setTitle("Admin module");
        this.setSize(960, 540);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        UserManagementPanel userPanel = new UserManagementPanel();
        new UserController(userPanel);
        tabbedPane.addTab("User management", userPanel);

        LoginHistoryPanel loginHistoryPanel = new LoginHistoryPanel();
        new LoginHistoryController(loginHistoryPanel);
        tabbedPane.addTab("Login history", loginHistoryPanel);

        ChatGroupPanel chatGroupPanel = new ChatGroupPanel();
        new ChatGroupController(chatGroupPanel);
        tabbedPane.addTab("Manage chat groups", chatGroupPanel);

        SpamReportsPanel spamReportsPanel = new SpamReportsPanel();
        new SpamReportController(spamReportsPanel);
        tabbedPane.addTab("Spam report", spamReportsPanel);

        RegistrationPanel registrationPanel = new RegistrationPanel();
        new RegistrationController(registrationPanel);
        tabbedPane.addTab("Newly registered users", registrationPanel);

        RegistrationChartPanel chartPanel = new RegistrationChartPanel();
        new RegistrationChartController(chartPanel);
        tabbedPane.addTab("New registrations chart", chartPanel);

        UserFriendPanel userFriendPanel = new UserFriendPanel();
        new UserFriendController(userFriendPanel);
        tabbedPane.addTab("User and friend list", userFriendPanel);

        UserActivityPanel userActivityPanel = new UserActivityPanel();
        new UserActivityController(userActivityPanel);
        tabbedPane.addTab("Active user list", userActivityPanel);

        UserActivityChartPanel userActivityChartPanel = new UserActivityChartPanel();
        new UserActivityChartController(userActivityChartPanel);
        tabbedPane.addTab("Active user chart", userActivityChartPanel);

        this.add(tabbedPane);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new AdminDashboard();
    }
}
