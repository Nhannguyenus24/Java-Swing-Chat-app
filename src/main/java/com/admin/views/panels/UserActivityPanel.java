package com.admin.views.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UserActivityPanel extends JPanel {
    public JTable userTable;
    public DefaultTableModel userTableModel;

    public JTextField searchField;
    public JComboBox<String> comparisonBox;
    public JSpinner startDateSpinner;
    public JSpinner endDateSpinner;
    public JSpinner activityCountSpinner;
    public JButton filterButton;

    public UserActivityPanel() {

        this.setLayout(new BorderLayout());

        JPanel filterAndControlPanel = new JPanel();
        filterAndControlPanel.setLayout(new BoxLayout(filterAndControlPanel, BoxLayout.Y_AXIS));

        JPanel filterPanel = createFilterPanel();
        filterAndControlPanel.add(filterPanel);

        JPanel controlPanel = createControlPanel();
        filterAndControlPanel.add(controlPanel);

        this.add(filterAndControlPanel, BorderLayout.NORTH);

        userTableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "Username", "Creation time", "Number of app openings", "Number of people chatted with",
                        "Number of groups chatted in", "Total activity count" });

        userTable = new JTable(userTableModel);
        userTable.setAutoCreateRowSorter(true);

        JScrollPane tableScrollPane = new JScrollPane(userTable);
        this.add(tableScrollPane, BorderLayout.CENTER);
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

        return panel;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        panel.add(new JLabel("Compare activity count:"));
        comparisonBox = new JComboBox<>(new String[] { "=", ">", "<" });
        panel.add(comparisonBox);

        panel.add(new JLabel("Activity count:"));
        activityCountSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        panel.add(activityCountSpinner);

        filterButton = new JButton("Filter");
        panel.add(filterButton);

        return panel;
    }
}
