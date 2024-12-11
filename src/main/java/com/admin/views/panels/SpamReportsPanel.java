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
                new String[] { "Username", "Full name", "Report time", "Reason" });
        reportsTable = new JTable(reportsTableModel);
        reportsTable.setAutoCreateRowSorter(true);
        this.add(new JScrollPane(reportsTable), BorderLayout.CENTER);

        JPanel actionPanel = createActionPanel();
        this.add(actionPanel, BorderLayout.SOUTH);
    }

    private JPanel createFilterPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        panel.add(new JLabel("Filter by name:"));
        searchField = new JTextField(15);
        panel.add(searchField);

        panel.add(new JLabel("From date:"));
        startDateSpinner = new JSpinner(new SpinnerDateModel());
        startDateSpinner.setEditor(new JSpinner.DateEditor(startDateSpinner, "yyyy-MM-dd"));
        panel.add(startDateSpinner);

        panel.add(new JLabel("To date:"));
        endDateSpinner = new JSpinner(new SpinnerDateModel());
        endDateSpinner.setEditor(new JSpinner.DateEditor(endDateSpinner, "yyyy-MM-dd"));
        panel.add(endDateSpinner);

        filterButton = new JButton("Filter");
        panel.add(filterButton);

        return panel;
    }

    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        blockUserButton = new JButton("Lock account");
        panel.add(blockUserButton);

        return panel;
    }
}
