package com.admin.views.panels;

import javax.swing.*;
import java.awt.*;

public class UpdateUserPanel extends JPanel {
    public JTextField fullNameField;
    public JTextField addressField;
    public JTextField dobField;
    public JComboBox<String> genderComboBox;
    public JComboBox<String> statusComboBox;
    public JTextField emailField;

    private static final String[] GENDER_OPTIONS = { "Male", "Female", "Non-binary" };
    private static final Dimension FIELD_PREFERRED_SIZE = new Dimension(250, 25);

    public UpdateUserPanel(String currentFullName, String currentAddress, String currentDob,
            String currentGender, String currentEmail) {

        this.setLayout(new GridLayout(5, 2));
        fullNameField = createTextField(currentFullName);
        addressField = createTextField(currentAddress);
        dobField = createTextField(currentDob);
        genderComboBox = createComboBox(GENDER_OPTIONS, currentGender);
        emailField = createTextField(currentEmail);

        addLabelAndComponent("Full name:", fullNameField);
        addLabelAndComponent("Address:", addressField);
        addLabelAndComponent("Date of birth:", dobField);
        addLabelAndComponent("Gender:", genderComboBox);
        addLabelAndComponent("Email:", emailField);
    }

    private JTextField createTextField(String value) {
        JTextField textField = new JTextField(value);
        textField.setPreferredSize(FIELD_PREFERRED_SIZE);
        return textField;
    }

    private JComboBox<String> createComboBox(String[] options, String value) {
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setPreferredSize(FIELD_PREFERRED_SIZE);
        comboBox.setSelectedItem(value);
        return comboBox;
    }

    private void addLabelAndComponent(String labelText, JComponent component) {
        this.add(new JLabel(labelText));
        this.add(component);
    }

}
