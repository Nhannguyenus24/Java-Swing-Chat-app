import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActiveUserPanel extends JPanel {
    private JTable activeUserTable;
    private DefaultTableModel model;
    private JTextField filterNameField;
    private JTextField filterActivityCountField;
    private JComboBox<String> comparisonBox;
    private JTextField startDateField;
    private JTextField endDateField;
    private SimpleDateFormat dateFormat;

    public ActiveUserPanel() {
        setLayout(new BorderLayout());

        String[] columns = { "Tên đăng nhập", "Hoạt động", "Số nhóm tham gia", "Thời gian hoạt động" };
        Object[][] data = {
                { "knight", "The Dream Nail", 10, "2024-11-01 14:30" },
                { "hornet", "Needle Strike", 8, "2024-11-10 09:15" },
                { "shroomy", "Spore Shot", 5, "2024-11-12 17:45" },
                { "grimm", "Nightmare Flames", 7, "2024-11-03 11:00" },
                { "quirrel", "Lake Watch", 4, "2024-11-05 13:15" },
                { "zote", "Hopeless Heroics", 3, "2024-11-07 18:00" },
                { "seer", "Whispering Roots", 6, "2024-11-08 20:30" },
                { "cloth", "Brave Sacrifice", 5, "2024-11-09 15:45" },
                { "tiso", "Arena Challenge", 4, "2024-11-11 12:15" },
                { "nailsmith", "Forging Mastery", 9, "2024-11-13 17:50" }
        };

        model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        activeUserTable = new JTable(model);
        activeUserTable.setAutoCreateRowSorter(true);
        add(new JScrollPane(activeUserTable), BorderLayout.CENTER);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        JPanel filterPanel = new JPanel(new GridLayout(1, 2));

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.add(new JLabel("Lọc theo tên: "));
        filterNameField = new JTextField(15);
        namePanel.add(filterNameField);
        JButton filterNameButton = new JButton("Lọc theo tên");
        namePanel.add(filterNameButton);
        filterPanel.add(namePanel);

        JPanel countPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        countPanel.add(new JLabel("Số hoạt động: "));
        comparisonBox = new JComboBox<>(new String[] { "=", "<", ">" });
        countPanel.add(comparisonBox);
        filterActivityCountField = new JTextField(5);
        countPanel.add(filterActivityCountField);
        JButton filterCountButton = new JButton("Lọc theo số lượng");
        countPanel.add(filterCountButton);
        filterPanel.add(countPanel);

        add(filterPanel, BorderLayout.NORTH);

        filterNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTableByName(filterNameField.getText());
            }
        });

        filterCountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTableByActivityCount(comparisonBox.getSelectedItem().toString(),
                        filterActivityCountField.getText());
            }
        });

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.add(new JLabel("Từ ngày (yyyy-MM-dd HH:mm): "));
        startDateField = new JTextField(15);
        datePanel.add(startDateField);
        datePanel.add(new JLabel("Đến ngày (yyyy-MM-dd HH:mm): "));
        endDateField = new JTextField(15);
        datePanel.add(endDateField);
        JButton filterDateButton = new JButton("Lọc theo thời gian");
        datePanel.add(filterDateButton);
        add(datePanel, BorderLayout.SOUTH);

        filterDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTableByDateRange(startDateField.getText(), endDateField.getText());
            }
        });
    }

    private void filterTableByName(String query) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        activeUserTable.setRowSorter(sorter);
        if (query.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query, 0));
        }
    }

    private void filterTableByDateRange(String start, String end) {
        try {
            Date startDate = start.isEmpty() ? null : dateFormat.parse(start);
            Date endDate = end.isEmpty() ? null : dateFormat.parse(end);
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            activeUserTable.setRowSorter(sorter);

            sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    try {
                        Date entryDate = dateFormat.parse(entry.getStringValue(3));
                        if (startDate != null && entryDate.before(startDate)) {
                            return false;
                        }
                        if (endDate != null && entryDate.after(endDate)) {
                            return false;
                        }
                        return true;
                    } catch (ParseException ex) {
                        return false;
                    }
                }
            });
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày hợp lệ.", "Lỗi định dạng",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filterTableByActivityCount(String comparison, String value) {
        try {
            int threshold = Integer.parseInt(value);
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            activeUserTable.setRowSorter(sorter);

            sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    int activityCount = Integer.parseInt(entry.getStringValue(1));
                    switch (comparison) {
                        case "=":
                            return activityCount == threshold;
                        case "<":
                            return activityCount < threshold;
                        case ">":
                            return activityCount > threshold;
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
