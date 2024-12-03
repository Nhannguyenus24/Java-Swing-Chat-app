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
                new String[]{"Tên người dùng", "Thời gian tạo", "Bạn bè trực tiếp", "Tổng bạn bè"});

        userTable = new JTable(userTableModel);
        userTable.setAutoCreateRowSorter(true);
        this.add(new JScrollPane(userTable), BorderLayout.CENTER);
    }

    private JPanel createFilterPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        searchField = new JTextField(15);
        panel.add(new JLabel("Tên người dùng:"));
        panel.add(searchField);

        comparisonBox = new JComboBox<>(new String[]{"=", ">", "<"});
        panel.add(new JLabel("So sánh số bạn:"));
        panel.add(comparisonBox);

        friendCountSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        panel.add(new JLabel("Số lượng bạn:"));
        panel.add(friendCountSpinner);

        filterButton = new JButton("Lọc");
        panel.add(filterButton);

        return panel;
    }
}
