package com.admin.views.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RegistrationPanel extends JPanel {
    public JTable registrationTable;
    public DefaultTableModel registrationTableModel;
    public JTextField searchField;
    public JTextField emailField;
    public JButton filterButton;
    public JSpinner startDateSpinner;
    public JSpinner endDateSpinner;

    public RegistrationPanel() {
        this.setLayout(new BorderLayout());
        JPanel filterPanel = createFilterPanel();
        this.add(filterPanel, BorderLayout.NORTH);

        registrationTableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "Username", "Full name", "Email", "Registration time" });
        registrationTable = new JTable(registrationTableModel);
        registrationTable.setAutoCreateRowSorter(true);
        this.add(new JScrollPane(registrationTable), BorderLayout.CENTER);
    }

    private JPanel createFilterPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        panel.add(new JLabel("Filter by name:"));
        searchField = new JTextField(15);
        panel.add(searchField);

        panel.add(new JLabel("Filter by email:"));
        emailField = new JTextField(15);
        panel.add(emailField);

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
}
