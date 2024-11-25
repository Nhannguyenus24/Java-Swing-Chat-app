import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;

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
        String[] columns = { "Tên đăng nhập", "Họ tên", "Địa chỉ", "Ngày sinh", "Giới tính", "Email", "Trạng thái" };
        Object[][] data = {

                { "knight", "The Knight", "The Forgotten Crossroads", "01/01/2000", "Nam", "knight@example.com",
                        "Active" },
                { "hornet", "Hornet", "Deepnest", "02/02/1992", "Nữ", "hornet@example.com", "Locked" },
                { "qun", "Quirrel", "Greenpath", "03/03/1985", "Nam", "quirrel@example.com", "Active" },
                { "zelda", "Zote the Mighty", "Kingdom's Edge", "04/04/1978", "Nam", "zote@example.com", "Locked" },
                { "grimm", "Grimm", "Grimm's Troop", "01/01/1995", "Nam", "grimm@example.com", "Active" },
                { "sly", "Sly", "Dirtmouth", "05/05/1993", "Nam", "sly@example.com", "Active" },

                { "hollow", "Hornet (Silksong)", "The Mantis Village", "10/10/1998", "Nữ",
                        "hornet_silksong@example.com", "Active" },
                { "lyra", "Lyra", "The Kingdom of Silk", "11/11/1994", "Nữ", "lyra@example.com", "Locked" },
                { "veiled", "The Veiled Queen", "Veiled Kingdom", "12/12/1987", "Nữ", "veiled_queen@example.com",
                        "Active" },
                { "vessel", "Vessel", "Unknown", "01/01/2001", "Nam", "vessel@example.com", "Locked" },
                { "lore", "Lora the Wanderer", "Unknown Lands", "06/06/1983", "Nữ", "lora@example.com", "Active" },
                { "silk", "Silkweaver", "Silken Chamber", "07/07/1996", "Nam", "silkweaver@example.com", "Active" }
        };

        return new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
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
        viewHistoryButton.addActionListener(this::viewHistoryAction);
        viewFriendsButton.addActionListener(this::viewFriendsAction);

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
        JOptionPane.showMessageDialog(null, "Thêm người dùng mới.");
    }

    private void updateUserAction(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Cập nhật thông tin người dùng.");
    }

    private void deleteUserAction(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Xoá người dùng.");
    }

    private void lockUserAction(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Khoá tài khoản người dùng.");
    }

    private void unlockUserAction(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Mở khoá tài khoản người dùng.");
    }

    private void resetPasswordAction(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Cập nhật mật khẩu.");
    }

    private void viewHistoryAction(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Xem lịch sử đăng nhập.");
    }

    private void viewFriendsAction(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Xem danh sách bạn bè.");
    }
}
