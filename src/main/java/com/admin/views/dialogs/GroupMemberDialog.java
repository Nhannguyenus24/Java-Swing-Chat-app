package com.admin.views.dialogs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GroupMemberDialog extends JDialog {
    private JTable membersTable;
    private DefaultTableModel model;

    public GroupMemberDialog(List<Object[]> members) {
        this.setSize(960, 540);
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setTitle("List of members in the group");

        model = new DefaultTableModel(new Object[][] {},
                new String[] { "Username", "Full name", "Address", "Date of birth", "Gender", "Email", "Status"});
        membersTable = new JTable(model);

        for (Object[] row : members) {
            model.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(membersTable);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
}
