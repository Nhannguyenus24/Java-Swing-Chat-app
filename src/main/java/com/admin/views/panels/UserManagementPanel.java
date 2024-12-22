package com.admin.views.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class UserManagementPanel extends JPanel {
    public JTable userTable;
    public DefaultTableModel model;

    public JTextField searchField;
    public JComboBox<String> searchCriteriaComboBox;
    public JButton searchButton;

    public JButton addButton;
    public JButton deleteButton;
    public JButton updateButton;
    public JButton toggleLockButton;
    public JButton resetPasswordButton;

    public JButton viewHistoryButton;
    public JButton viewFriendsButton;

    public UserManagementPanel() {
        this.setLayout(new BorderLayout());
        JPanel searchPanel = createSearchPanel();
        this.add(searchPanel, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[][] {},
                new String[] { "Username", "Full name", "Address", "Date of birth", "Gender", "Email"});

        userTable = new JTable(model);
        userTable.setAutoCreateRowSorter(true);

        this.add(new JScrollPane(userTable), BorderLayout.CENTER);
        JPanel actionPanel = createActionPanel();
        this.add(actionPanel, BorderLayout.SOUTH);
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        String[] searchCriteria = { "Username", "Full name"};
        searchCriteriaComboBox = new JComboBox<>(searchCriteria);
        panel.add(new JLabel("Select search criteria:"));
        panel.add(searchCriteriaComboBox);

        searchField = new JTextField(20);
        panel.add(new JLabel("Enter keyword:"));
        panel.add(searchField);

        searchButton = new JButton("Search");
        panel.add(searchButton);

        return panel;
    }

    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        updateButton = new JButton("Update information");
        toggleLockButton = new JButton("Lock/Unlock");
        resetPasswordButton = new JButton("Update password");
        viewHistoryButton = new JButton("View login history");
        viewFriendsButton = new JButton("View friend list");

        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(updateButton);
        panel.add(toggleLockButton);
        panel.add(resetPasswordButton);
        panel.add(viewHistoryButton);
        panel.add(viewFriendsButton);
        return panel;
    }
}
