package com.admin.controllers;

import com.admin.models.UserActivityModel;
import com.admin.views.panels.UserActivityChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.Map;

public class UserActivityChartController {
    private UserActivityChartPanel userActivityChartPanel;

    public UserActivityChartController(UserActivityChartPanel panel) {
        this.userActivityChartPanel = panel;
        this.loadInitialChartData();
        this.addEventListeners();
    }

    private void loadInitialChartData() {
        int initialYear = (int) userActivityChartPanel.yearComboBox.getSelectedItem();
        this.updateChartData(initialYear);
    }

    private void addEventListeners() {
        userActivityChartPanel.yearComboBox.addItemListener(this::updateChartAction);
    }

    private void updateChartAction(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            int selectedYear = (int) userActivityChartPanel.yearComboBox.getSelectedItem();
            this.updateChartData(selectedYear);
        }
    }

    private void updateChartData(int year) {
        Map<Integer, Integer> data = UserActivityModel.getMonthlyUserActivityByYear(year);

        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(userActivityChartPanel, "Không có dữ liệu hoạt động cho năm " + year);
            userActivityChartPanel.updateChart(null);
            return;
        }

        JFreeChart chart = createChart(data, year);
        userActivityChartPanel.updateChart(chart);
    }

    private JFreeChart createChart(Map<Integer, Integer> data, int year) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int month = 1; month <= 12; month++) {
            int userCount = data.getOrDefault(month, 0);
            dataset.addValue(userCount, "Số lượng người", String.valueOf(month));
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Số lượng người hoạt động theo tháng (" + year + ")",
                "Tháng",
                "Số lượng người",
                dataset);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setTickUnit(new org.jfree.chart.axis.NumberTickUnit(1));
        return chart;
    }
}
