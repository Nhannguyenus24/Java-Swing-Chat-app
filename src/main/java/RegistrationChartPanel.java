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

public class RegistrationChartPanel extends JPanel {
    private JComboBox<String> yearSelector;
    private DefaultCategoryDataset dataset;
    private Connection conn;

    public RegistrationChartPanel() {
        setLayout(new BorderLayout());

        // Panel chọn năm và nút hiển thị biểu đồ
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Chọn năm: "));

        // Cấu hình JComboBox cho năm
        yearSelector = new JComboBox<>(new String[] { "2023", "2024" }); // Giả sử bạn có dữ liệu cho các năm này
        topPanel.add(yearSelector);
        JButton showChartButton = new JButton("Hiển thị biểu đồ");
        topPanel.add(showChartButton);
        add(topPanel, BorderLayout.NORTH);

        // Lắng nghe sự kiện nút bấm
        showChartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedYear = (String) yearSelector.getSelectedItem();
                updateChart(selectedYear);  // Cập nhật biểu đồ theo năm được chọn
            }
        });

        // Cấu hình biểu đồ
        dataset = new DefaultCategoryDataset();
        JFreeChart chart = ChartFactory.createBarChart(
                "Số lượng người đăng ký mới",   // Tiêu đề
                "Tháng",                        // Trục hoành (x)
                "Số lượng",                     // Trục tung (y)
                dataset,
                PlotOrientation.VERTICAL,
                true,                           // Có hiển thị chú thích
                true,                           // Có hiển thị tooltip
                false                           // Không có URL
        );
        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);

        // Cập nhật biểu đồ cho năm mặc định (ví dụ năm 2023)
        updateChart((String) yearSelector.getSelectedItem());
    }

    // Phương thức cập nhật biểu đồ từ cơ sở dữ liệu theo năm
    private void updateChart(String year) {
        dataset.clear();  // Xóa dữ liệu cũ trong biểu đồ
        String sql = """
            SELECT 
                EXTRACT(MONTH FROM registration_time) AS month, 
                COUNT(*) AS registration_count
            FROM user_registrations
            WHERE EXTRACT(YEAR FROM registration_time) = ?
            GROUP BY EXTRACT(MONTH FROM registration_time)
            ORDER BY month
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, Integer.parseInt(year));  // Set giá trị năm vào câu truy vấn
            ResultSet rs = stmt.executeQuery();

            // Thêm dữ liệu vào biểu đồ
            while (rs.next()) {
                int month = rs.getInt("month");
                int registrationCount = rs.getInt("registration_count");
                String monthName = getMonthName(month);
                dataset.addValue(registrationCount, "Số lượng", monthName);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Phương thức chuyển số tháng thành tên tháng
    private String getMonthName(int month) {
        String[] months = { "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6",
                "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12" };
        return months[month - 1];
    }
}
