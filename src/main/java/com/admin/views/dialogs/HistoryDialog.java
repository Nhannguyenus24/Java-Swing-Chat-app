package com.admin.views.dialogs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HistoryDialog extends JDialog {
    private JTable historyTable;
    private DefaultTableModel model;

    public HistoryDialog(List<Object[]> historyData) {
        this.setSize(960, 540);
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setTitle("Lịch sử đăng nhập");

        model = new DefaultTableModel(new Object[][] {},
                new String[] { "Thời gian đăng nhập" });
        historyTable = new JTable(model);

        for (Object[] row : historyData) {
            model.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(historyTable);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
}
