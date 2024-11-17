package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    }

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

    private DefaultTableModel createTableModel() {
        String[] columns = { "Tên nhóm", "Thời gian tạo" };
        Object[][] data = {
                { "Hollow Knight", "2024-11-01 10:00:00" },
                { "Silksong", "2024-10-20 14:30:00" },
                { "The Great Five Knight", "2024-11-12 09:00:00" },
                { "Isma's Grove", "2024-11-02 10:30:00" },
                { "Dryya's Stand", "2024-11-04 14:00:00" },
                { "Hegemol's Watch", "2024-11-06 16:00:00" },
                { "Ogrim's Valor", "2024-11-10 19:15:00" },
                { "Ze'mer's Lament", "2024-11-14 12:30:00" },
                { "Knight's Ascent", "2024-11-08 15:20:00" },
                { "Hornet's Trial", "2024-11-05 18:45:00" },
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
        JButton viewMembersButton = new JButton("Xem thành viên");
        JButton viewAdminsButton = new JButton("Xem admin");

        viewMembersButton.addActionListener(this::viewMembersAction);
        viewAdminsButton.addActionListener(this::viewAdminsAction);

        panel.add(viewMembersButton);
        panel.add(viewAdminsButton);
        return panel;
    }

    private void filterTable(String query) {
        if (query.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
        }
    }

    private void viewMembersAction(ActionEvent e) {
        int selectedRow = groupChatTable.getSelectedRow();
        if (selectedRow != -1) {
            String groupName = (String) model.getValueAt(selectedRow, 0);
            JOptionPane.showMessageDialog(null, "Danh sách thành viên của " + groupName);
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một nhóm để xem thành viên.");
        }
    }

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
