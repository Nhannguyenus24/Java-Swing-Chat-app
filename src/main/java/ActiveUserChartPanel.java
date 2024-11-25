import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;

import java.util.HashMap;
import java.util.Map;

public class ActiveUserChartPanel extends JPanel {
    private JComboBox<String> yearSelector;
    private DefaultCategoryDataset dataset;
    private Map<String, int[]> yearlyData;

    public ActiveUserChartPanel() {
        setLayout(new BorderLayout());

        yearlyData = new HashMap<>();
        yearlyData.put("2023", new int[] { 5, 8, 12, 15, 18, 20, 25, 30, 28, 22, 17, 10 });
        yearlyData.put("2024", new int[] { 8, 15, 20, 22, 25, 28, 30, 32, 35, 30, 25, 18 });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Chọn năm: "));

        yearSelector = new JComboBox<>(yearlyData.keySet().toArray(new String[0]));
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

    private void updateChart(String year) {
        dataset.clear();
        if (yearlyData.containsKey(year)) {
            int[] data = yearlyData.get(year);
            String[] months = { "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6",
                    "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12" };
            for (int i = 0; i < months.length; i++) {
                dataset.addValue(data[i], months[i], year);
            }
        }
    }
}
