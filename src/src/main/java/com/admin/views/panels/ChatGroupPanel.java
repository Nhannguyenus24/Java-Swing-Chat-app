package com.admin.views.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class ChatGroupPanel extends JPanel {
    public JTable groupTable;
    public DefaultTableModel groupTableModel;

    public JTextField searchField;
    public JButton searchButton;

    public JButton viewMembersButton;
    public JButton viewAdminsButton;

    public ChatGroupPanel() {
        this.setLayout(new BorderLayout());
        JPanel searchPanel = createSearchPanel();
        this.add(searchPanel, BorderLayout.NORTH);

        groupTableModel = new DefaultTableModel(new Object[][] {},
                new String[] {"Group name", "Number of members"});

        groupTable = new JTable(groupTableModel);
        groupTable.setAutoCreateRowSorter(true);

        this.add(new JScrollPane(groupTable), BorderLayout.CENTER);
        JPanel buttonPanel = createButtonPanel();
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        searchField = new JTextField(20);
        panel.add(new JLabel("Enter group name:"));
        panel.add(searchField);

        searchButton = new JButton("Search");
        panel.add(searchButton);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        viewMembersButton = new JButton("View members");
        viewAdminsButton = new JButton("View admins");
        panel.add(viewMembersButton);
        panel.add(viewAdminsButton);
        return panel;
    }
}
