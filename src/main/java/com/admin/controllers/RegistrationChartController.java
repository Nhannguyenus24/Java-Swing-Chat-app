package com.admin.controllers;

import com.admin.models.RegistrationModel;
import com.admin.views.panels.RegistrationChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.Map;

public class RegistrationChartController {
    private RegistrationChartPanel registrationChartPanel;

    public RegistrationChartController(RegistrationChartPanel panel) {
        this.registrationChartPanel = panel;
        this.loadInitialChartData();
        this.addEventListeners();
    }

    // Load dữ liệu ban đầu cho biểu đồ
    private void loadInitialChartData() {
        int initialYear = (int) registrationChartPanel.yearComboBox.getSelectedItem();
        this.updateChartData(initialYear);
    }

    // Thêm sự kiện lắng nghe
    private void addEventListeners() {
        registrationChartPanel.yearComboBox.addItemListener(this::updateChartAction);
    }

    // Xử lý sự kiện thay đổi năm
    private void updateChartAction(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            int selectedYear = (int) registrationChartPanel.yearComboBox.getSelectedItem();
            this.updateChartData(selectedYear);
        }
    }

    // Cập nhật dữ liệu biểu đồ
    private void updateChartData(int year) {
        Map<Integer, Integer> data = RegistrationModel.getMonthlyRegistrationsByYear(year);

        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(registrationChartPanel, "Không có dữ liệu đăng ký cho năm " + year);
            registrationChartPanel.updateChart(null); // Xóa biểu đồ nếu không có dữ liệu
            return;
        }

        JFreeChart chart = createChart(data, year);
        registrationChartPanel.updateChart(chart);
    }

    // Tạo biểu đồ từ dữ liệu
    private JFreeChart createChart(Map<Integer, Integer> data, int year) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Thêm dữ liệu vào dataset
        for (int month = 1; month <= 12; month++) {
            int registrations = data.getOrDefault(month, 0);
            dataset.addValue(registrations, "Số lượng", String.valueOf(month));
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Số lượng đăng ký mới theo tháng (" + year + ")",
                "Tháng",
                "Số lượng",
                dataset);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setTickUnit(new org.jfree.chart.axis.NumberTickUnit(1));
        return chart;
    }
}
