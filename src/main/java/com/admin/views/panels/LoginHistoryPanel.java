package com.admin.views.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LoginHistoryPanel extends JPanel {
    public JTable loginHistoryTable;
    public DefaultTableModel model;

    public LoginHistoryPanel() {
        this.setLayout(new BorderLayout());

        model = new DefaultTableModel(new Object[][] {},
                new String[] { "Thời gian đăng nhập", "Tên đăng nhập", "Họ tên" });
        loginHistoryTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(loginHistoryTable);
        this.add(scrollPane, BorderLayout.CENTER);
    }

}