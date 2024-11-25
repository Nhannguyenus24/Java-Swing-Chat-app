import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;

public class GroupChatPanel extends JPanel {
    private JTable groupChatTable;
    private JTextField searchField;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    public GroupChatPanel() {
        setLayout(new BorderLayout());

        JPanel searchPanel = createSearchPanel();
        add(searchPanel, BorderLayout.NORTH);

        model = createTableModel();
        groupChatTable = new JTable(model);
        groupChatTable.setAutoCreateRowSorter(true);

        sorter = new TableRowSorter<>(model);
        groupChatTable.setRowSorter(sorter);

        add(new JScrollPane(groupChatTable), BorderLayout.CENTER);

        JPanel actionPanel = createActionPanel();
        add(actionPanel, BorderLayout.SOUTH);

        loadGroupChatDataFromDatabase(); // Load group chat data from the database
    }

    // Hàm tạo DefaultTableModel
    private DefaultTableModel createTableModel() {
        String[] columns = { "Tên nhóm", "Thời gian tạo" };
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Cột không thể chỉnh sửa
            }
        };
    }

    // Hàm tạo panel tìm kiếm
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel("Tìm nhóm:"));
        searchField = new JTextField(20);
        panel.add(searchField);

        JButton searchButton = new JButton("Tìm kiếm");
        searchButton.addActionListener(e -> filterTable(searchField.getText().toLowerCase()));
        panel.add(searchButton);

        return panel;
    }

    // Hàm tạo panel hành động
    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton viewMembersButton = new JButton("Xem thành viên");
        JButton viewAdminsButton = new JButton("Xem admin");

        viewMembersButton.addActionListener(this::viewMembersAction);
        viewAdminsButton.addActionListener(this::viewAdminsAction);

        panel.add(viewMembersButton);
        panel.add(viewAdminsButton);
        return panel;
    }

    // Hàm lọc bảng theo từ khóa tìm kiếm
    private void filterTable(String query) {
        if (query.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
        }
    }

    // Hàm lấy dữ liệu nhóm từ cơ sở dữ liệu
    private void loadGroupChatDataFromDatabase() {
        String sql = "SELECT group_name, created_at FROM `groups` ORDER BY created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            model.setRowCount(0); // Clear existing rows

            while (rs.next()) {
                String groupName = rs.getString("group_name");
                String createdAt = rs.getString("created_at");

                model.addRow(new Object[]{groupName, createdAt});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu nhóm từ cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Hàm xem thành viên của nhóm
    private void viewMembersAction(ActionEvent e) {
        int selectedRow = groupChatTable.getSelectedRow();
        if (selectedRow != -1) {
            String groupName = (String) model.getValueAt(selectedRow, 0);
            JOptionPane.showMessageDialog(null, "Danh sách thành viên của " + groupName);
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một nhóm để xem thành viên.");
        }
    }

    // Hàm xem admin của nhóm
    private void viewAdminsAction(ActionEvent e) {
        int selectedRow = groupChatTable.getSelectedRow();
        if (selectedRow != -1) {
            String groupName = (String) model.getValueAt(selectedRow, 0);
            JOptionPane.showMessageDialog(null, "Danh sách admin của " + groupName);
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một nhóm để xem admin.");
        }
    }
}
