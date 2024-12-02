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
                new String[] { "Tên đăng nhập", "Họ tên", "Địa chỉ", "Ngày sinh", "Giới tính", "Email", "Trạng thái" });

        userTable = new JTable(model);
        userTable.setAutoCreateRowSorter(true);

        this.add(new JScrollPane(userTable), BorderLayout.CENTER);
        JPanel actionPanel = createActionPanel();
        this.add(actionPanel, BorderLayout.SOUTH);
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        String[] searchCriteria = { "Tên đăng nhập", "Họ tên", "Trạng thái" };
        searchCriteriaComboBox = new JComboBox<>(searchCriteria);
        panel.add(new JLabel("Chọn tiêu chí tìm kiếm:"));
        panel.add(searchCriteriaComboBox);

        searchField = new JTextField(20);
        panel.add(new JLabel("Nhập từ khóa:"));
        panel.add(searchField);

        searchButton = new JButton("Tìm kiếm");
        panel.add(searchButton);

        return panel;
    }

    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        addButton = new JButton("Thêm");
        deleteButton = new JButton("Xóa");
        updateButton = new JButton("Cập nhật thông tin");
        toggleLockButton = new JButton("Khoá/Mở khoá tài khoản");
        resetPasswordButton = new JButton("Cập nhật mật khẩu");
        viewHistoryButton = new JButton("Xem lịch sử đăng nhập");
        viewFriendsButton = new JButton("Xem danh sách bạn bè");

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
