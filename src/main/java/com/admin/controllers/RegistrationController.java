package com.admin.controllers;

import com.admin.models.RegistrationModel;
import com.admin.views.panels.RegistrationPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.util.List;

public class RegistrationController {
    public RegistrationPanel registrationPanel;
    public List<Object[]> registrations;
    public DefaultTableModel registrationModel;

    public RegistrationController(RegistrationPanel panel) {
        this.registrationPanel = panel;
        this.loadRegistrationData();
        this.addEventListeners();
    }

    private void loadRegistrationData() {
        registrations = RegistrationModel.getAllRegistrations();
        registrationModel = this.registrationPanel.registrationTableModel;

        registrationModel.setRowCount(0); // Xóa dữ liệu cũ
        for (Object[] registration : registrations) {
            registrationModel.addRow(registration);
        }
    }

    private void addEventListeners() {
        this.registrationPanel.filterButton.addActionListener(this::filterRegistrationsAction);
    }

    private void filterRegistrationsAction(ActionEvent e) {
        // Lấy thông tin từ giao diện
        String keyword = this.registrationPanel.searchField.getText().trim();
        Date startDate = new Date(((java.util.Date) this.registrationPanel.startDateSpinner.getValue()).getTime());
        Date endDate = new Date(((java.util.Date) this.registrationPanel.endDateSpinner.getValue()).getTime());

        // Gọi hàm từ model để lọc dữ liệu
        List<Object[]> filteredResults = RegistrationModel.filterRegistrations(keyword, startDate, endDate);

        // Cập nhật bảng hiển thị
        registrationModel.setRowCount(0); // Xóa dữ liệu cũ
        for (Object[] row : filteredResults) {
            registrationModel.addRow(row);
        }
    }
}
