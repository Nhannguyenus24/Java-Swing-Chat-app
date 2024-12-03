package com.admin.views;

import com.admin.controllers.*;
import com.admin.views.panels.*;
import javax.swing.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        this.setTitle("Phân hệ dành cho người quản trị");
        this.setSize(960, 540);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        UserManagementPanel userPanel = new UserManagementPanel();
        new UserController(userPanel);
        tabbedPane.addTab("Quản lý người dùng", userPanel);

        LoginHistoryPanel loginHistoryPanel = new LoginHistoryPanel();
        new LoginHistoryController(loginHistoryPanel);
        tabbedPane.addTab("Lịch sử đăng nhập", loginHistoryPanel);

        ChatGroupPanel chatGroupPanel = new ChatGroupPanel();
        new ChatGroupController(chatGroupPanel);
        tabbedPane.addTab("Quản lý nhóm chat", chatGroupPanel);

        SpamReportsPanel spamReportsPanel = new SpamReportsPanel();
        new SpamReportController(spamReportsPanel);
        tabbedPane.addTab("Báo cáo spam", spamReportsPanel);

        RegistrationPanel registrationPanel = new RegistrationPanel();
        new RegistrationController(registrationPanel);
        tabbedPane.addTab("Người dùng đăng ký mới", registrationPanel);

        RegistrationChartPanel chartPanel = new RegistrationChartPanel();
        new RegistrationChartController(chartPanel);
        tabbedPane.addTab("Biểu đồ đăng ký mới", chartPanel);

        UserFriendPanel userFriendPanel = new UserFriendPanel();
        new UserFriendController(userFriendPanel);
        tabbedPane.addTab("Danh sách người dùng và bạn bè", userFriendPanel);

        this.add(tabbedPane);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new AdminDashboard();
    }
}
