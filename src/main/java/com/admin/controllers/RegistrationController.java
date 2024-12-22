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

        registrationModel.setRowCount(0);
        for (Object[] registration : registrations) {
            registrationModel.addRow(registration);
        }
    }

    private void addEventListeners() {
        this.registrationPanel.filterButton.addActionListener(this::filterRegistrationsAction);
    }

    private void filterRegistrationsAction(ActionEvent e) {

        String keyword = this.registrationPanel.searchField.getText().trim();
        String email = this.registrationPanel.emailField.getText().trim();
        Date startDate = new Date(((java.util.Date) this.registrationPanel.startDateSpinner.getValue()).getTime());
        Date endDate = new Date(((java.util.Date) this.registrationPanel.endDateSpinner.getValue()).getTime());

        List<Object[]> filteredResults = RegistrationModel.filterRegistrations(keyword, email, startDate, endDate);

        registrationModel.setRowCount(0);
        for (Object[] row : filteredResults) {
            registrationModel.addRow(row);
        }
    }
}
