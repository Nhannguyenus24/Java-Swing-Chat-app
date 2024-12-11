package com.admin.views.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UserFriendPanel extends JPanel {
    public JTable userTable;
    public DefaultTableModel userTableModel;

    public JTextField searchField;
    public JComboBox<String> comparisonBox;
    public JSpinner friendCountSpinner;
    public JButton filterButton;

    public UserFriendPanel() {
        this.setLayout(new BorderLayout());
        JPanel filterPanel = createFilterPanel();
        this.add(filterPanel, BorderLayout.NORTH);

        userTableModel = new DefaultTableModel(new Object[][]{},
                new String[]{"Username", "Creation time", "Direct friends", "Total friends"});

        userTable = new JTable(userTableModel);
        userTable.setAutoCreateRowSorter(true);
        this.add(new JScrollPane(userTable), BorderLayout.CENTER);
    }

    private JPanel createFilterPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        searchField = new JTextField(15);
        panel.add(new JLabel("Username:"));
        panel.add(searchField);

        comparisonBox = new JComboBox<>(new String[]{"=", ">", "<"});
        panel.add(new JLabel("Compare number of friends:"));
        panel.add(comparisonBox);

        friendCountSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        panel.add(new JLabel("Number of friends:"));
        panel.add(friendCountSpinner);

        filterButton = new JButton("Filter");
        panel.add(filterButton);

        return panel;
    }
}
