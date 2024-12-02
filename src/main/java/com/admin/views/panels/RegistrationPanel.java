package com.admin.views.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RegistrationPanel extends JPanel {
    public JTable registrationTable;
    public DefaultTableModel registrationTableModel;
    public JTextField searchField;
    public JButton filterButton;
    public JSpinner startDateSpinner;
    public JSpinner endDateSpinner;

    public RegistrationPanel() {
        this.setLayout(new BorderLayout());
        JPanel filterPanel = createFilterPanel();
        this.add(filterPanel, BorderLayout.NORTH);

        registrationTableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "Tên đăng nhập", "Họ tên", "Thời gian đăng ký" });
        registrationTable = new JTable(registrationTableModel);
        registrationTable.setAutoCreateRowSorter(true);
        this.add(new JScrollPane(registrationTable), BorderLayout.CENTER);
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
}
