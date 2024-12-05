package com.admin.views.panels;

import javax.swing.*;
import java.awt.*;

public class AddUserPanel extends JPanel {
    public JTextField usernameField;
    public JTextField fullNameField;
    public JTextField addressField;
    public JTextField dobField;
    public JComboBox<String> genderComboBox;
    public JComboBox<String> statusComboBox;
    public JTextField emailField;
    public JPasswordField passwordField;

    private static final String[] GENDER_OPTIONS = { "male", "female", "other" };
    private static final String[] STATUS_OPTIONS = { "online", "offline", "locked" };
    private static final Dimension FIELD_PREFERRED_SIZE = new Dimension(250, 25);

    public AddUserPanel() {
        this.setLayout(new GridLayout(8, 2));

        usernameField = createTextField();
        fullNameField = createTextField();
        addressField = createTextField();
        dobField = createTextField();
        genderComboBox = createComboBox(GENDER_OPTIONS);
        statusComboBox = createComboBox(STATUS_OPTIONS);
        emailField = createTextField();
        passwordField = new JPasswordField();

        addLabelAndComponent("Tên đăng nhập:", usernameField);
        addLabelAndComponent("Họ tên:", fullNameField);
        addLabelAndComponent("Địa chỉ:", addressField);
        addLabelAndComponent("Ngày sinh:", dobField);
        addLabelAndComponent("Giới tính:", genderComboBox);
        addLabelAndComponent("Trạng thái:", statusComboBox);
        addLabelAndComponent("Email:", emailField);
        addLabelAndComponent("Mật khẩu:", passwordField);
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(FIELD_PREFERRED_SIZE);
        return textField;
    }

    private JComboBox<String> createComboBox(String[] options) {
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setPreferredSize(FIELD_PREFERRED_SIZE);
        return comboBox;
    }

    private void addLabelAndComponent(String labelText, JComponent component) {
        this.add(new JLabel(labelText));
        this.add(component);
    }
}
