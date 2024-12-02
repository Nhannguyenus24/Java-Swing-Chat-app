package com.admin.views.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SpamReportsPanel extends JPanel {
    public JTable reportsTable;
    public DefaultTableModel reportsTableModel;
    public JTextField searchField;
    public JButton filterButton, blockUserButton;
    public JSpinner startDateSpinner;
    public JSpinner endDateSpinner;

    public SpamReportsPanel() {
        this.setLayout(new BorderLayout());
        JPanel filterPanel = createFilterPanel();
        this.add(filterPanel, BorderLayout.NORTH);

        reportsTableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "Tên đăng nhập", "Họ tên", "Thời gian báo cáo", "Lý do" });
        reportsTable = new JTable(reportsTableModel);
        reportsTable.setAutoCreateRowSorter(true);
        this.add(new JScrollPane(reportsTable), BorderLayout.CENTER);

        JPanel actionPanel = createActionPanel();
        this.add(actionPanel, BorderLayout.SOUTH);
    }

    private JPanel createFilterPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        panel.add(new JLabel("Lọc theo tên:"));
        searchField = new JTextField(15);
        panel.add(searchField);

        panel.add(new JLabel("Từ ngày:"));
        startDateSpinner = new JSpinner(new SpinnerDateModel());
        startDateSpinner.setEditor(new JSpinner.DateEditor(startDateSpinner, "yyyy-MM-dd"));
        panel.add(startDateSpinner);

        panel.add(new JLabel("Đến ngày:"));
        endDateSpinner = new JSpinner(new SpinnerDateModel());
        endDateSpinner.setEditor(new JSpinner.DateEditor(endDateSpinner, "yyyy-MM-dd"));
        panel.add(endDateSpinner);

        filterButton = new JButton("Lọc");
        panel.add(filterButton);

        return panel;
    }

    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        blockUserButton = new JButton("Khóa tài khoản");
        panel.add(blockUserButton);

        return panel;
    }
}
