import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserManagementPanel extends JPanel {
    private JTable userTable;
    private JTextField searchField;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    public UserManagementPanel() {
        setLayout(new BorderLayout());

        JPanel searchPanel = createSearchPanel();
        add(searchPanel, BorderLayout.NORTH);

        model = createTableModel();
        userTable = new JTable(model);
        userTable.setAutoCreateRowSorter(true);

        sorter = new TableRowSorter<>(model);
        userTable.setRowSorter(sorter);

        add(new JScrollPane(userTable), BorderLayout.CENTER);

        JPanel actionPanel = createActionPanel();
        add(actionPanel, BorderLayout.SOUTH);
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel("Tìm kiếm:"));
        searchField = new JTextField(20);
        panel.add(searchField);

        JButton searchButton = new JButton("Tìm kiếm");
        searchButton.addActionListener(e -> filterTable(searchField.getText().toLowerCase()));
        panel.add(searchButton);

        return panel;
    }

    private DefaultTableModel createTableModel() {
        String[] columns = {"Tên đăng nhập", "Họ tên", "Địa chỉ", "Ngày sinh", "Giới tính", "Email", "Trạng thái"};
        Object[][] data = getUserDataFromDatabase();
        return new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Các ô không được chỉnh sửa
            }
        };
    }

    private Object[][] getUserDataFromDatabase() {
        String sql = "SELECT username, full_name, address, dob, gender, email, status FROM Users";
        List<Object[]> dataList = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = new Object[7];
                row[0] = rs.getString("username");
                row[1] = rs.getString("full_name");
                row[2] = rs.getString("address");
                row[3] = rs.getString("dob");
                row[4] = rs.getString("gender");
                row[5] = rs.getString("email");
                row[6] = rs.getString("status");
                dataList.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataList.toArray(new Object[0][]);
    }

    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton addButton = new JButton("Thêm");
        JButton updateButton = new JButton("Cập nhật");
        JButton deleteButton = new JButton("Xoá");
        JButton lockButton = new JButton("Khoá tài khoản");
        JButton unlockButton = new JButton("Mở khoá tài khoản");
        JButton resetPasswordButton = new JButton("Cập nhật mật khẩu");
        JButton viewHistoryButton = new JButton("Xem lịch sử đăng nhập");
        JButton viewFriendsButton = new JButton("Xem danh sách bạn bè");

        addButton.addActionListener(this::addUserAction);
        updateButton.addActionListener(this::updateUserAction);
        deleteButton.addActionListener(this::deleteUserAction);
        lockButton.addActionListener(this::lockUserAction);
        unlockButton.addActionListener(this::unlockUserAction);
        resetPasswordButton.addActionListener(this::resetPasswordAction);
//        viewHistoryButton.addActionListener(this::viewHistoryAction);
//        viewFriendsButton.addActionListener(this::viewFriendsAction);

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(lockButton);
        panel.add(unlockButton);
        panel.add(resetPasswordButton);
        panel.add(viewHistoryButton);
        panel.add(viewFriendsButton);

        return panel;
    }

    private void filterTable(String query) {
        if (query.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
        }
    }

    private void addUserAction(ActionEvent e) {
        String username = JOptionPane.showInputDialog("Nhập tên đăng nhập:");
        String fullName = JOptionPane.showInputDialog("Nhập họ tên:");
        String email = JOptionPane.showInputDialog("Nhập email:");
        String password = JOptionPane.showInputDialog("Nhập mật khẩu:");

        String sql = "INSERT INTO Users (username, full_name, email, password, status) VALUES (?, ?, ?, ?, 'Active')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, fullName);
            stmt.setString(3, email);
            stmt.setString(4, password);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Người dùng đã được thêm thành công!");
                refreshTable(); // Cập nhật lại bảng sau khi thêm người dùng
            } else {
                JOptionPane.showMessageDialog(null, "Thêm người dùng thất bại.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi thêm người dùng.");
        }
    }

    private void refreshTable() {
        model.setDataVector(getUserDataFromDatabase(),
                new String[]{"Tên đăng nhập", "Họ tên", "Địa chỉ", "Ngày sinh", "Giới tính", "Email", "Trạng thái"});
    }

    private void updateUserAction(ActionEvent e) {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            String username = (String) model.getValueAt(selectedRow, 0);
            String newFullName = JOptionPane.showInputDialog("Nhập họ tên mới:");
            String newEmail = JOptionPane.showInputDialog("Nhập email mới:");

            String sql = "UPDATE Users SET full_name = ?, email = ? WHERE username = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, newFullName);
                stmt.setString(2, newEmail);
                stmt.setString(3, username);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Cập nhật thất bại.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi cập nhật.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn người dùng để cập nhật.");
        }
    }

    private void deleteUserAction(ActionEvent e) {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            String username = (String) model.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa người dùng này?",
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                String sql = "DELETE FROM Users WHERE username = ?";
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {

                    stmt.setString(1, username);
                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Xóa người dùng thành công!");
                        refreshTable();
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa người dùng thất bại.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi xóa người dùng.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn người dùng để xóa.");
        }
    }

    private void lockUserAction(ActionEvent e) {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            String username = (String) model.getValueAt(selectedRow, 0);

            String sql = "UPDATE Users SET status = 'Locked' WHERE username = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, username);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Tài khoản đã bị khoá.");
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Không thể khoá tài khoản.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi khoá tài khoản.");
            }
        }
    }

    private void unlockUserAction(ActionEvent e) {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            String username = (String) model.getValueAt(selectedRow, 0);

            String sql = "UPDATE Users SET status = 'Active' WHERE username = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, username);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Tài khoản đã được mở khoá.");
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Không thể mở khoá tài khoản.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi mở khoá tài khoản.");
            }
        }
    }

    private void resetPasswordAction(ActionEvent e) {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            String username = (String) model.getValueAt(selectedRow, 0);
            String newPassword = JOptionPane.showInputDialog("Nhập mật khẩu mới:");

            String sql = "UPDATE Users SET password = ? WHERE username = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, newPassword);
                stmt.setString(2, username);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Cập nhật mật khẩu thành công!");
                } else {
                    JOptionPane.showMessageDialog(null, "Cập nhật mật khẩu thất bại.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi thay đổi mật khẩu.");
            }
        }
    }
//
//    private void viewHistoryAction(ActionEvent e) {
//        int selectedRow = userTable.getSelectedRow();
//        if (selectedRow != -1) {
//            String username = (String) model.getValueAt(selectedRow, 0);
//            new LoginHistoryDialog(username).setVisible(true); // Giả sử bạn đã tạo một dialog để hiển thị lịch sử đăng nhập
//        }
//    }
//
//    private void viewFriendsAction(ActionEvent e) {
//        int selectedRow = userTable.getSelectedRow();
//        if (selectedRow != -1) {
//            String username = (String) model.getValueAt(selectedRow, 0);
//            new FriendsListDialog(username).setVisible(true); // Giả sử bạn đã tạo một dialog để hiển thị danh sách bạn bè
//        }
//    }
}
