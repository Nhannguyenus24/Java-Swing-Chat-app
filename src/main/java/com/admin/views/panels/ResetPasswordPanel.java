package com.admin.views.panels;

import javax.swing.*;
import java.awt.*;

public class ResetPasswordPanel extends JPanel {
    public JPasswordField oldPasswordField;
    public JPasswordField newPasswordField;
    public JPasswordField confirmPasswordField;

    private static final Dimension FIELD_PREFERRED_SIZE = new Dimension(250, 25);

    public ResetPasswordPanel() {
        this.setLayout(new GridLayout(3, 2));
        oldPasswordField = createPasswordField();
        newPasswordField = createPasswordField();
        confirmPasswordField = createPasswordField();

        addLabelAndComponent("Mật khẩu cũ", oldPasswordField);
        addLabelAndComponent("Mật khẩu mới", newPasswordField);
        addLabelAndComponent("Xác nhận mật khẩu mới", confirmPasswordField);
    }

    private JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(FIELD_PREFERRED_SIZE);
        return passwordField;
    }

    private void addLabelAndComponent(String labelText, JComponent component) {
        this.add(new JLabel(labelText));
        this.add(component);
    }
}