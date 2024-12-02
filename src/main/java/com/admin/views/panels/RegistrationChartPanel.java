package com.admin.views.panels;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class RegistrationChartPanel extends JPanel {
    public JComboBox<Integer> yearComboBox;
    private JPanel chartContainer;

    public RegistrationChartPanel() {
        this.setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel(new FlowLayout());
        yearComboBox = new JComboBox<>();

        // Thêm các năm vào combobox
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int year = currentYear; year >= 2000; year--) {
            yearComboBox.addItem(year);
        }

        controlPanel.add(new JLabel("Chọn năm:"));
        controlPanel.add(yearComboBox);

        chartContainer = new JPanel(new BorderLayout());
        chartContainer.setPreferredSize(new Dimension(800, 600));

        this.add(controlPanel, BorderLayout.NORTH);
        this.add(chartContainer, BorderLayout.CENTER);
    }

    public void updateChart(JFreeChart chart) {
        chartContainer.removeAll();
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(false); // Tắt zoom bằng chuột
        chartPanel.setMouseZoomable(false); // Tắt kéo thả
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        chartContainer.add(chartPanel, BorderLayout.CENTER);
        chartContainer.revalidate();
        chartContainer.repaint();
    }
}
