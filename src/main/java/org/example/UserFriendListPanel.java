package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserFriendListPanel extends JPanel {
    private JTable userFriendTable;
    private DefaultTableModel model;
    private JTextField filterNameField;
    private JTextField filterFriendCountField;
    private JComboBox<String> comparisonBox;

    public UserFriendListPanel() {
        setLayout(new BorderLayout());

        String[] columns = { "Tên đăng nhập", "Số bạn bè trực tiếp", "Số bạn bè gián tiếp" };
        Object[][] data = {
                { "The Knight", 10, 30 },
                { "Hornet", 7, 25 },
                { "Quirrel", 4, 12 },
                { "Zote", 2, 8 },
                { "Mantis Lord", 6, 18 },
                { "Grimm", 3, 14 },
                { "Iselda", 5, 20 },
                { "Troupe Master Grimm", 8, 22 },
                { "Mothwing", 6, 17 },
                { "Monomon", 4, 13 }
        };

        model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        userFriendTable = new JTable(model);
        userFriendTable.setAutoCreateRowSorter(true);
        add(new JScrollPane(userFriendTable), BorderLayout.CENTER);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Lọc theo tên: "));
        filterNameField = new JTextField(15);
        filterPanel.add(filterNameField);

        JButton filterNameButton = new JButton("Lọc theo tên");
        filterPanel.add(filterNameButton);
        add(filterPanel, BorderLayout.NORTH);

        filterNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTableByName(filterNameField.getText());
            }
        });

        JPanel countPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        countPanel.add(new JLabel("Số bạn bè trực tiếp: "));
        comparisonBox = new JComboBox<>(new String[] { "=", "<", ">" });
        countPanel.add(comparisonBox);
        filterFriendCountField = new JTextField(5);
        countPanel.add(filterFriendCountField);

        JButton filterCountButton = new JButton("Lọc theo số lượng");
        countPanel.add(filterCountButton);
        add(countPanel, BorderLayout.SOUTH);

        filterCountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTableByFriendCount(comparisonBox.getSelectedItem().toString(), filterFriendCountField.getText());
            }
        });
    }

    private void filterTableByName(String query) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        userFriendTable.setRowSorter(sorter);
        if (query.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query, 0));
        }
    }

    private void filterTableByFriendCount(String comparison, String value) {
        try {
            int threshold = Integer.parseInt(value);
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            userFriendTable.setRowSorter(sorter);

            sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    int friendCount = Integer.parseInt(entry.getStringValue(1));
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
