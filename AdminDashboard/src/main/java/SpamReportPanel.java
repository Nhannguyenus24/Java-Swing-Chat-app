import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SpamReportPanel extends JPanel {
    private JTable spamReportTable;
    private DefaultTableModel model;
    private JTextField filterField;
    private JButton blockUserButton;

    public SpamReportPanel() {
        setLayout(new BorderLayout());

        // Cấu trúc bảng
        String[] columns = { "Thời gian", "Tên đăng nhập", "Họ tên" };
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa ô trong bảng
            }
        };

        spamReportTable = new JTable(model);
        spamReportTable.setAutoCreateRowSorter(true);

        add(new JScrollPane(spamReportTable), BorderLayout.CENTER);

        loadDataFromDatabase();

        // Panel lọc theo tên đăng nhập
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Lọc theo tên đăng nhập: "));
        filterField = new JTextField(15);
        filterPanel.add(filterField);
        JButton filterButton = new JButton("Lọc");
        filterPanel.add(filterButton);
        add(filterPanel, BorderLayout.NORTH);

        // Lọc dữ liệu theo tên đăng nhập khi nhấn nút
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filterText = filterField.getText().toLowerCase();
                filterTable(filterText); // Lọc bảng theo tên đăng nhập
            }
        });

        // Panel dưới để chứa nút khóa tài khoản
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        blockUserButton = new JButton("Khóa tài khoản");
        bottomPanel.add(blockUserButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Khóa tài khoản khi nhấn nút
        blockUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = spamReportTable.getSelectedRow();
                if (selectedRow != -1) {
                    String username = (String) model.getValueAt(selectedRow, 1);
                    blockUser(username); // Gọi hàm khóa tài khoản
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn một báo cáo để khóa tài khoản.");
                }
            }
        });
    }

    // Phương thức tải dữ liệu từ cơ sở dữ liệu
    private void loadDataFromDatabase() {
        // Thay đổi câu truy vấn cho phù hợp với cơ sở dữ liệu của bạn
        String sql = """
            SELECT 
                report_time, 
                u.username, 
                u.full_name 
            FROM spam_reports sr
            JOIN Users u ON sr.user_id = u.user_id
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            model.setRowCount(0); // Xóa dữ liệu cũ trong bảng

            // Duyệt qua kết quả trả về từ cơ sở dữ liệu
            while (rs.next()) {
                String reportTime = rs.getString("report_time");
                String username = rs.getString("username");
                String fullName = rs.getString("full_name");

                model.addRow(new Object[]{reportTime, username, fullName});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Phương thức lọc bảng theo tên đăng nhập
    private void filterTable(String query) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        spamReportTable.setRowSorter(sorter);
        if (query.trim().isEmpty()) {
            sorter.setRowFilter(null); // Không lọc nếu trường tìm kiếm trống
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query, 1)); // Lọc theo tên đăng nhập (cột 1)
        }
    }

    // Phương thức khóa tài khoản
    private void blockUser(String username) {
        // Cập nhật trạng thái tài khoản thành 'blocked'
        String sql = "UPDATE Users SET status = 'blocked' WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            int rowsAffected = stmt.executeUpdate(); // Thực thi câu lệnh UPDATE

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Tài khoản " + username + " đã bị khóa.");
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy tài khoản để khóa.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi khóa tài khoản.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
