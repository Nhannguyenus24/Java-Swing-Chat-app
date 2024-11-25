import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ActiveUserChartPanel extends JPanel {
    private JComboBox<String> yearSelector;
    private DefaultCategoryDataset dataset;
    private Map<String, int[]> yearlyData;

    public ActiveUserChartPanel() {
        setLayout(new BorderLayout());

        yearlyData = new HashMap<>();

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Chọn năm: "));

        yearSelector = new JComboBox<>(getAvailableYears());
        topPanel.add(yearSelector);
        JButton showChartButton = new JButton("Hiển thị biểu đồ");
        topPanel.add(showChartButton);
        add(topPanel, BorderLayout.NORTH);

        showChartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedYear = (String) yearSelector.getSelectedItem();
                updateChart(selectedYear);
            }
        });

        dataset = new DefaultCategoryDataset();
        JFreeChart chart = ChartFactory.createBarChart(
                "Số lượng người hoạt động theo tháng", "Tháng", "Số lượng",
                dataset, PlotOrientation.VERTICAL, true, true, false);
        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);

        updateChart((String) yearSelector.getSelectedItem());
    }

    // Hàm lấy các năm có dữ liệu từ cơ sở dữ liệu
    private String[] getAvailableYears() {
        // Kết nối cơ sở dữ liệu để lấy các năm có dữ liệu
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT YEAR(activity_date) FROM user_activity");
             ResultSet rs = stmt.executeQuery()) {

            // Lưu các năm vào một danh sách
            Map<String, Integer> years = new HashMap<>();
            while (rs.next()) {
                years.put(rs.getString(1), 1);
            }
            return years.keySet().toArray(new String[0]);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi truy vấn dữ liệu từ cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return new String[0];
        }
    }

    // Hàm lấy dữ liệu cho biểu đồ từ cơ sở dữ liệu
    private void updateChart(String year) {
        dataset.clear();
        int[] monthlyData = getMonthlyData(year);
        String[] months = { "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6",
                "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12" };

        for (int i = 0; i < months.length; i++) {
            dataset.addValue(monthlyData[i], months[i], year);
        }
    }

    // Hàm lấy số lượng người dùng hoạt động mỗi tháng trong năm từ cơ sở dữ liệu
    private int[] getMonthlyData(String year) {
        int[] data = new int[12];
        String sql = "SELECT MONTH(activity_date), COUNT(*) FROM user_activity " +
                "WHERE YEAR(activity_date) = ? GROUP BY MONTH(activity_date) ORDER BY MONTH(activity_date)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, year);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int month = rs.getInt(1) - 1;  // Tháng trong cơ sở dữ liệu bắt đầu từ 1, trong mảng bắt đầu từ 0
                    data[month] = rs.getInt(2);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi truy vấn dữ liệu từ cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return data;
    }
}
