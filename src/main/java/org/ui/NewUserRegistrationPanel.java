package org.ui;

import org.database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;

public class NewUserRegistrationPanel extends JPanel {
    private JTable newUserTable;
    private DefaultTableModel model;
    private JTextField filterField;
    private JTextField startDateField;
    private JTextField endDateField;

    public NewUserRegistrationPanel() {
        setLayout(new BorderLayout());

        String[] columns = {"Tên đăng nhập", "Họ tên", "Thời gian đăng ký"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        newUserTable = new JTable(model);
        newUserTable.setAutoCreateRowSorter(true);
        add(new JScrollPane(newUserTable), BorderLayout.CENTER);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Lọc theo tên: "));
        filterField = new JTextField(15);
        filterPanel.add(filterField);

        JButton filterButton = new JButton("Lọc");
        filterPanel.add(filterButton);
        add(filterPanel, BorderLayout.NORTH);

        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTable(filterField.getText());
            }
        });

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.add(new JLabel("Từ ngày (yyyy-MM-dd): "));
        startDateField = new JTextField(10);
        datePanel.add(startDateField);
        datePanel.add(new JLabel("Đến ngày (yyyy-MM-dd): "));
        endDateField = new JTextField(10);
        datePanel.add(endDateField);
        JButton filterDateButton = new JButton("Lọc theo ngày");
        datePanel.add(filterDateButton);
        add(datePanel, BorderLayout.SOUTH);

        filterDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterByDateRange(startDateField.getText(), endDateField.getText());
            }
        });

        loadDataFromDatabase(); // Load data from database
    }

    // Hàm lấy dữ liệu người dùng từ cơ sở dữ liệu
    private void loadDataFromDatabase() {
        String sql = "SELECT user_id, registration_id, registration_time FROM user_registrations ORDER BY registration_time DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            model.setRowCount(0); // Clear existing rows

            while (rs.next()) {
                String user_id = rs.getString("user_id");
                String fullName = rs.getString("registration_id");
                String registrationDate = rs.getString("registration_time");

                model.addRow(new Object[]{user_id, fullName, registrationDate});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Lọc bảng theo tên người dùng
    private void filterTable(String query) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        newUserTable.setRowSorter(sorter);
        if (query.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query, 0)); // Lọc theo tên đăng nhập
        }
    }

    // Lọc bảng theo khoảng thời gian
    private void filterByDateRange(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        newUserTable.setRowSorter(sorter);

        try {
            Date start = startDate.isEmpty() ? null : sdf.parse(startDate);
            Date end = endDate.isEmpty() ? null : sdf.parse(endDate);

            sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    try {
                        String entryDateStr = entry.getStringValue(2).split(" ")[0]; // Extract date part only
                        Date entryDate = sdf.parse(entryDateStr);

                        if ((start == null || !entryDate.before(start)) && (end == null || !entryDate.after(end))) {
                            return true;
                        }
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    return false;
                }
            });
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng ngày (yyyy-MM-dd).", "Lỗi định dạng",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
