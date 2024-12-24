package com.admin.views.dialogs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FriendsDialog extends JDialog {
    private JTable friendsTable;
    private DefaultTableModel model;

    public FriendsDialog(List<Object[]> friendData) {
        this.setSize(960, 540);
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setTitle("Friend list");

        model = new DefaultTableModel(new Object[][] {},
                new String[] { "Username", "Full name", "Address", "Date of birth", "Gender", "Email", "Status" });
        friendsTable = new JTable(model);

        for (Object[] row : friendData) {
            model.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(friendsTable);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
}
