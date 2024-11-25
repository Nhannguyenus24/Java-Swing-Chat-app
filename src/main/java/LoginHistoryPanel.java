import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class LoginHistoryPanel extends JPanel {
    private JTable loginHistoryTable;
    private DefaultTableModel model;

    public LoginHistoryPanel() {
        setLayout(new BorderLayout());

        model = createTableModel();
        loginHistoryTable = new JTable(model);
        loginHistoryTable.setAutoCreateRowSorter(true);

        add(new JScrollPane(loginHistoryTable), BorderLayout.CENTER);

        loadLoginHistoryFromDatabase(); // Load login history from database
    }

    // Hàm tạo DefaultTableModel
    private DefaultTableModel createTableModel() {
        String[] columns = {"Thời gian", "Tên đăng nhập", "Họ tên"};
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Cột không thể chỉnh sửa
            }
        };
    }

    // Hàm lấy dữ liệu lịch sử đăng nhập từ cơ sở dữ liệu
    private void loadLoginHistoryFromDatabase() {
        String sql = "SELECT login_time, user_id, history_id FROM login_history ORDER BY login_time DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            model.setRowCount(0); // Clear existing rows

            while (rs.next()) {
                String loginTime = rs.getString("login_time");
                String user_id = rs.getString("user_id");
                String fullName = rs.getString("history_id");

                model.addRow(new Object[]{loginTime, user_id, fullName});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải lịch sử đăng nhập từ cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
