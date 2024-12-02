package com.admin.controllers;

import com.admin.models.SpamReportModel;
import com.admin.views.panels.SpamReportsPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.util.List;

public class SpamReportController {
    public SpamReportsPanel panel;
    public DefaultTableModel reportTableModel;
    public List<Object[]> reports;

    public SpamReportController(SpamReportsPanel panel) {
        this.panel = panel;
        this.reportTableModel = panel.reportsTableModel;

        this.loadReportData();
        this.addEventListeners();
    }

    private void loadReportData() {
        reports = SpamReportModel.getAllReports();
        updateReportTable(reports);
    }

    private void addEventListeners() {
        this.panel.filterButton.addActionListener(this::filterReportsAction);
        this.panel.blockUserButton.addActionListener(this::blockUserAction);
    }

    private void filterReportsAction(ActionEvent e) {
        String username = panel.searchField.getText().trim();
        Date startDate = new Date(((java.util.Date) this.panel.startDateSpinner.getValue()).getTime());
        Date endDate = new Date(((java.util.Date) this.panel.endDateSpinner.getValue()).getTime());

        List<Object[]> filteredReports = SpamReportModel.filterReports(username, startDate, endDate);
        updateReportTable(filteredReports);
    }

    private void blockUserAction(ActionEvent e) {
        int viewRow = this.panel.reportsTable.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một người dùng để khóa!");
            return;
        }
        int modelRow = this.panel.reportsTable.convertRowIndexToModel(viewRow);

        String username = (String) panel.reportsTableModel.getValueAt(modelRow, 0);
        boolean success = SpamReportModel.blockUser(username);
        if (success) {
            JOptionPane.showMessageDialog(panel, "Tài khoản đã bị khóa thành công.");
            loadReportData();
        } else {
            JOptionPane.showMessageDialog(panel, "Khóa tài khoản thất bại!");
        }
    }

    private void updateReportTable(List<Object[]> reports) {
        reportTableModel.setRowCount(0);
        for (Object[] report : reports) {
            reportTableModel.addRow(report);
        }
    }

}
