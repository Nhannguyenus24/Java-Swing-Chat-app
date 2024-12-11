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
            JOptionPane.showMessageDialog(userActivityChartPanel, "No activity data for the year " + year);
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
            dataset.addValue(userCount, "Number of people", String.valueOf(month));
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Number of active users per month (" + year + ")",
                "Month",
                "Number of people",
                dataset);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setTickUnit(new org.jfree.chart.axis.NumberTickUnit(1));
        return chart;
    }
}
