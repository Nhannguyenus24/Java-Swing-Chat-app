package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewUserRegistrationPanel extends JPanel {
    private JTable newUserTable;
    private DefaultTableModel model;
    private JTextField filterField;
    private JTextField startDateField;
    private JTextField endDateField;

    public NewUserRegistrationPanel() {
        setLayout(new BorderLayout());

        String[] columns = { "Tên đăng nhập", "Họ tên", "Thời gian đăng ký" };
        Object[][] data = {
                { "knight", "The Knight", "2024-11-10 14:30:00" },
                { "hornet", "Hornet", "2024-11-12 09:20:00" },
                { "grimm", "Grimm", "2024-11-14 16:45:00" },
                { "zote", "Zote the Mighty", "2024-11-15 10:10:00" },
                { "mantis", "Mantis Lord", "2024-11-16 11:05:00" },
                { "godseeker", "Godseeker", "2024-11-16 17:25:00" },
                { "bretta", "Bretta", "2024-11-17 08:30:00" },
                { "seahorse", "Seahorse", "2024-11-18 10:00:00" },
                { "cornifer", "Cornifer", "2024-11-18 13:15:00" },
                { "traitor", "Traitor Lord", "2024-11-19 12:00:00" }
        };

        model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        newUserTable = new JTable(model);
        newUserTable.setAutoCreateRowSorter(true);
        add(new JScrollPane(newUserTable), BorderLayout.CENTER);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Lọc theo tên: "));
        filterField = new JTextField(15);
        filterPanel.add(filterField);

        JButton filterButton = new JButton("Lọc");
        filterPanel.add(filterButton);
        add(filterPanel, BorderLayout.NORTH);

        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                filterTable(filterField.getText());
            }
        });

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.add(new JLabel("Từ ngày (yyyy-MM-dd): "));
        startDateField = new JTextField(10);
        datePanel.add(startDateField);
        datePanel.add(new JLabel("Đến ngày (yyyy-MM-dd): "));
        endDateField = new JTextField(10);
        datePanel.add(endDateField);
        JButton filterDateButton = new JButton("Lọc theo ngày");
        datePanel.add(filterDateButton);
        add(datePanel, BorderLayout.SOUTH);

        filterDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterByDateRange(startDateField.getText(), endDateField.getText());
            }
        });
    }

    private void filterTable(String query) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        newUserTable.setRowSorter(sorter);
        if (query.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query, 0));
        }
    }

    private void filterByDateRange(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        newUserTable.setRowSorter(sorter);

        try {
            Date start = startDate.isEmpty() ? null : sdf.parse(startDate);
            Date end = endDate.isEmpty() ? null : sdf.parse(endDate);

            sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    try {
                        Date entryDate = sdf.parse(entry.getStringValue(2).split(" ")[0]);
                        if ((start == null || !entryDate.before(start)) && (end == null || !entryDate.after(end))) {
                            return true;
                        }
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    return false;
                }
            });
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng ngày (yyyy-MM-dd).", "Lỗi định dạng",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
