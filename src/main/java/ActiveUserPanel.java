import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;

public class ActiveUserPanel extends JPanel {
    private JTable activeUserTable;
    private DefaultTableModel model;
    private JTextField filterNameField;
    private JTextField filterActivityCountField;
    private JComboBox<String> comparisonBox;
    private JTextField startDateField;
    private JTextField endDateField;
    private SimpleDateFormat dateFormat;

    public ActiveUserPanel() {
        setLayout(new BorderLayout());

        String[] columns = { "Tên đăng nhập", "Hoạt động", "Số nhóm tham gia", "Thời gian hoạt động" };
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        activeUserTable = new JTable(model);
        activeUserTable.setAutoCreateRowSorter(true);
        add(new JScrollPane(activeUserTable), BorderLayout.CENTER);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        JPanel filterPanel = new JPanel(new GridLayout(1, 2));

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.add(new JLabel("Lọc theo tên: "));
        filterNameField = new JTextField(15);
        namePanel.add(filterNameField);
        JButton filterNameButton = new JButton("Lọc theo tên");
        namePanel.add(filterNameButton);
        filterPanel.add(namePanel);

        JPanel countPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        countPanel.add(new JLabel("Số hoạt động: "));
        comparisonBox = new JComboBox<>(new String[] { "=", "<", ">" });
        countPanel.add(comparisonBox);
        filterActivityCountField = new JTextField(5);
        countPanel.add(filterActivityCountField);
        JButton filterCountButton = new JButton("Lọc theo số lượng");
        countPanel.add(filterCountButton);
        filterPanel.add(countPanel);

        add(filterPanel, BorderLayout.NORTH);

        filterNameButton.addActionListener(e -> filterTableByName(filterNameField.getText()));

        filterCountButton.addActionListener(e -> filterTableByActivityCount(comparisonBox.getSelectedItem().toString(),
                filterActivityCountField.getText()));

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.add(new JLabel("Từ ngày (yyyy-MM-dd HH:mm): "));
        startDateField = new JTextField(15);
        datePanel.add(startDateField);
        datePanel.add(new JLabel("Đến ngày (yyyy-MM-dd HH:mm): "));
        endDateField = new JTextField(15);
        datePanel.add(endDateField);
        JButton filterDateButton = new JButton("Lọc theo thời gian");
        datePanel.add(filterDateButton);
        add(datePanel, BorderLayout.SOUTH);

        filterDateButton.addActionListener(e -> filterTableByDateRange(startDateField.getText(), endDateField.getText()));

        loadActiveUserDataFromDatabase(); // Load active user data from the database
    }

    // Hàm lấy dữ liệu người dùng từ cơ sở dữ liệu
    private void loadActiveUserDataFromDatabase() {
        String sql = "SELECT user_id, activity_id, activity_count, activity_date FROM user_activity ORDER BY activity_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            model.setRowCount(0); // Clear existing rows

            while (rs.next()) {
                String user_id = rs.getString("user_id");
                String activity_id = rs.getString("activity_id");
                int groupCount = rs.getInt("activity_count");
                String activityTime = rs.getString("activity_date");

                model.addRow(new Object[]{user_id, activity_id, groupCount, activityTime});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu người dùng từ cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Hàm lọc theo tên
    private void filterTableByName(String query) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        activeUserTable.setRowSorter(sorter);
        if (query.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query, 0));
        }
    }

    // Hàm lọc theo thời gian
    private void filterTableByDateRange(String start, String end) {
        try {
            Date startDate = start.isEmpty() ? null : dateFormat.parse(start);
            Date endDate = end.isEmpty() ? null : dateFormat.parse(end);
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            activeUserTable.setRowSorter(sorter);

            sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    try {
                        Date entryDate = dateFormat.parse(entry.getStringValue(3));
                        if (startDate != null && entryDate.before(startDate)) {
                            return false;
                        }
                        if (endDate != null && entryDate.after(endDate)) {
                            return false;
                        }
                        return true;
                    } catch (ParseException ex) {
                        return false;
                    }
                }
            });
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày hợp lệ.", "Lỗi định dạng",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Hàm lọc theo số nhóm tham gia
    private void filterTableByActivityCount(String comparison, String value) {
        try {
            int threshold = Integer.parseInt(value);
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            activeUserTable.setRowSorter(sorter);

            sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    int activityCount = Integer.parseInt(entry.getStringValue(2));
                    switch (comparison) {
                        case "=":
                            return activityCount == threshold;
                        case "<":
                            return activityCount < threshold;
                        case ">":
                            return activityCount > threshold;
                        default:
                            return true;
                    }
                }
            });
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ.", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
        }
    }
}
