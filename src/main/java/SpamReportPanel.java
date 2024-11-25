import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpamReportPanel extends JPanel {
    private JTable spamReportTable;
    private DefaultTableModel model;
    private JTextField filterField;
    private JButton blockUserButton;

    public SpamReportPanel() {
        setLayout(new BorderLayout());

        String[] columns = { "Thời gian", "Tên đăng nhập", "Họ tên" };
        Object[][] data = {
                { "2024-11-15 08:45:00", "hollow_knight", "Hollow Knight" },
                { "2024-11-14 14:22:00", "silksong", "Silksong" },
                { "2024-11-13 19:05:00", "sealed_vessel", "Sealed Vessel" },
                { "2024-11-12 09:30:00", "isma_grove", "Isma" },
                { "2024-11-11 16:45:00", "dryya_stand", "Dryya" },
                { "2024-11-10 11:15:00", "hegemol_watch", "Hegemol" },
                { "2024-11-09 14:50:00", "ogrim_valor", "Orgim" },
                { "2024-11-08 10:25:00", "zemer_lament", "Zemer" },
                { "2024-11-07 18:10:00", "knight_ascent", "Knight" },
                { "2024-11-06 12:30:00", "hornet_trial", "Hornet" }
        };

        model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        spamReportTable = new JTable(model);
        spamReportTable.setAutoCreateRowSorter(true);

        add(new JScrollPane(spamReportTable), BorderLayout.CENTER);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Lọc theo tên đăng nhập: "));
        filterField = new JTextField(15);
        filterPanel.add(filterField);
        JButton filterButton = new JButton("Lọc");
        filterPanel.add(filterButton);
        add(filterPanel, BorderLayout.NORTH);

        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filterText = filterField.getText().toLowerCase();
                filterTable(filterText);
            }
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        blockUserButton = new JButton("Khóa tài khoản");
        bottomPanel.add(blockUserButton);
        add(bottomPanel, BorderLayout.SOUTH);

        blockUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = spamReportTable.getSelectedRow();
                if (selectedRow != -1) {
                    String username = (String) model.getValueAt(selectedRow, 1);
                    JOptionPane.showMessageDialog(null, "Tài khoản " + username + " đã bị khóa.");

                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn một báo cáo để khóa tài khoản.");
                }
            }
        });
    }

    private void filterTable(String query) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        spamReportTable.setRowSorter(sorter);
        if (query.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query, 1));
        }
    }
}
