import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFriendListPanel extends JPanel {
    private JTable userFriendTable;
    private DefaultTableModel model;
    private JTextField filterNameField;
    private JTextField filterFriendCountField;
    private JComboBox<String> comparisonBox;

    public UserFriendListPanel() {
        setLayout(new BorderLayout());

        String[] columns = { "Tên đăng nhập", "Số bạn bè trực tiếp", "Số bạn bè gián tiếp" };
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Các ô trong bảng không thể chỉnh sửa
            }
        };

        userFriendTable = new JTable(model);
        userFriendTable.setAutoCreateRowSorter(true);
        add(new JScrollPane(userFriendTable), BorderLayout.CENTER);

        loadDataFromDatabase();

        // Panel lọc theo tên người dùng
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Lọc theo tên: "));
        filterNameField = new JTextField(15);
        filterPanel.add(filterNameField);

        JButton filterNameButton = new JButton("Lọc theo tên");
        filterPanel.add(filterNameButton);
        add(filterPanel, BorderLayout.NORTH);

        // Thêm sự kiện cho nút lọc theo tên
        filterNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTableByName(filterNameField.getText());
            }
        });

        // Panel lọc theo số lượng bạn bè trực tiếp
        JPanel countPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        countPanel.add(new JLabel("Số bạn bè trực tiếp: "));
        comparisonBox = new JComboBox<>(new String[] { "=", "<", ">" });
        countPanel.add(comparisonBox);
        filterFriendCountField = new JTextField(5);
        countPanel.add(filterFriendCountField);

        JButton filterCountButton = new JButton("Lọc theo số lượng");
        countPanel.add(filterCountButton);
        add(countPanel, BorderLayout.SOUTH);

        // Thêm sự kiện cho nút lọc theo số lượng bạn bè
        filterCountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTableByFriendCount(comparisonBox.getSelectedItem().toString(), filterFriendCountField.getText());
            }
        });
    }

    // Phương thức tải dữ liệu từ cơ sở dữ liệu
    private void loadDataFromDatabase() {
        String sql = """
            SELECT 
                u.username, 
                (SELECT COUNT(*) FROM friends uf WHERE uf.user_id = u.user_id) AS direct_friends,
                (
                    SELECT COUNT(*) FROM friends uf1
                    INNER JOIN friends uf2 ON uf1.friend_id = uf2.user_id
                    WHERE uf1.user_id = u.user_id
                ) AS indirect_friends
            FROM Users u
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            model.setRowCount(0); // Xóa các dòng hiện có trong bảng

            // Duyệt qua kết quả và thêm vào bảng
            while (rs.next()) {
                String username = rs.getString("username");
                int directFriends = rs.getInt("direct_friends");
                int indirectFriends = rs.getInt("indirect_friends");

                model.addRow(new Object[]{username, directFriends, indirectFriends});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Phương thức lọc bảng theo tên người dùng
    private void filterTableByName(String query) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        userFriendTable.setRowSorter(sorter);
        if (query.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query, 0)); // Lọc theo tên người dùng (cột 0)
        }
    }

    // Phương thức lọc bảng theo số lượng bạn bè trực tiếp
    private void filterTableByFriendCount(String comparison, String value) {
        try {
            int threshold = Integer.parseInt(value); // Lấy giá trị lọc và chuyển đổi thành số
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            userFriendTable.setRowSorter(sorter);

            // Lọc theo số lượng bạn bè
            sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    int friendCount = Integer.parseInt(entry.getStringValue(1)); // Lấy số bạn bè trực tiếp từ cột 1
                    switch (comparison) {
                        case "=":
                            return friendCount == threshold;
                        case "<":
                            return friendCount < threshold;
                        case ">":
                            return friendCount > threshold;
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
