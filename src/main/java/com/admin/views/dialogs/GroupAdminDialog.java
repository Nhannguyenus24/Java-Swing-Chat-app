package com.admin.views.dialogs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GroupAdminDialog extends JDialog {
    private JTable membersTable;
    private DefaultTableModel model;

    public GroupAdminDialog(List<Object[]> admins) {
        this.setSize(960, 540);
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setTitle("Danh sách admin trong nhóm");

        model = new DefaultTableModel(new Object[][] {},
                new String[] { "Tên đăng nhập", "Họ tên", "Địa chỉ", "Ngày sinh", "Giới tính", "Email", "Trạng thái" });
        membersTable = new JTable(model);

        for (Object[] row : admins) {
            model.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(membersTable);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
}
