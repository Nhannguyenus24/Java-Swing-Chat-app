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
        this.setTitle("Danh sách bạn bè");

        model = new DefaultTableModel(new Object[][] {},
                new String[] { "Tên đăng nhập", "Họ tên", "Địa chỉ", "Ngày sinh", "Giới tính", "Email", "Trạng thái" });
        friendsTable = new JTable(model);

        for (Object[] row : friendData) {
            model.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(friendsTable);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
}
