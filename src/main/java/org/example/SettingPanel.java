package org.example;
import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class SettingPanel extends javax.swing.JFrame {
    private javax.swing.JPanel mainPanel;
    private CardLayout cardLayout;

    private Color defaultColor = Color.LIGHT_GRAY;
    private Color selectedColor = new Color(100, 150, 180);
    private javax.swing.JButton selectedButton;

    public SettingPanel() {
        this.setTitle("User Management");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 500);
        this.setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new javax.swing.JPanel(cardLayout);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        javax.swing.JPanel userInfoPanel = createUserInfoPanel();
        javax.swing.JPanel resetPasswordPanel = createResetPasswordPanel();
        javax.swing.JPanel generatePasswordPanel = createGeneratePasswordPanel("example@vn.com");

        mainPanel.add(userInfoPanel, "UserInfo");
        mainPanel.add(resetPasswordPanel, "ResetPassword");
        mainPanel.add(generatePasswordPanel, "GeneratePassword");

        javax.swing.JPanel buttonPanel = new javax.swing.JPanel();
        buttonPanel.setLayout(new javax.swing.BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        javax.swing.JButton userInfoButton = createNavButton("User Info");
        javax.swing.JButton resetPasswordButton = createNavButton("Reset Password");
        javax.swing.JButton generatePasswordButton = createNavButton("Generate Password");

        selectButton(userInfoButton);

        userInfoButton.addActionListener((e) -> switchPanel(userInfoButton, "UserInfo"));
        resetPasswordButton.addActionListener((e) -> switchPanel(resetPasswordButton, "ResetPassword"));
        generatePasswordButton.addActionListener((e) -> switchPanel(generatePasswordButton, "GeneratePassword"));

        buttonPanel.add(userInfoButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(resetPasswordButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(generatePasswordButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        javax.swing.JButton exitButton = createNavButton("Back to chat");
        exitButton.addActionListener(e -> {
            ChatUI chat = new ChatUI();
            chat.setVisible(true);
            chat.setLocationRelativeTo(null);
            this.dispose();
        });
        buttonPanel.add(exitButton);

        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        javax.swing.JButton adminButton = createNavButton("Admin mode");
        adminButton.addActionListener(e -> {
            AdminDashboard admin = new AdminDashboard();
            admin.setVisible(true);
            admin.setLocationRelativeTo(null);
            this.dispose();
        });
        buttonPanel.add(adminButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        javax.swing.JButton logoutButton = createNavButton("Log out");
        logoutButton.addActionListener(e -> {
            int confirm = javax.swing.JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to log out?",
                    "Log Out", JOptionPane.YES_NO_OPTION);
            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                this.dispose();
            }
        });
        buttonPanel.add(logoutButton);

        this.add(buttonPanel, BorderLayout.WEST);
        this.add(mainPanel, BorderLayout.CENTER);

        this.setVisible(true);
    }

    private JButton createNavButton(String text) {
        FlatButton button = new FlatButton(text);
        button.setPreferredSize(new Dimension(150, 40));
        button.setMaximumSize(new Dimension(150, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(defaultColor);
        return button;
    }

    private void switchPanel(javax.swing.JButton button, String panelName) {
        cardLayout.show(mainPanel, panelName);
        selectButton(button);
    }

    private void selectButton(javax.swing.JButton button) {
        if (selectedButton != null) {
            selectedButton.setBackground(defaultColor);
        }
        button.setBackground(selectedColor);
        selectedButton = button;
    }
    private JPanel createUserInfoPanel() {
        javax.swing.JPanel panel = new javax.swing.JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setAlignmentY(Component.TOP_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        panel.add(new javax.swing.JLabel("Username:"), gbc);
        gbc.gridx = 1;
        javax.swing.JTextField usernameField = new javax.swing.JTextField(20);
        usernameField.setPreferredSize(new Dimension(400, 30));
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        panel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        javax.swing.JTextField fullNameField = new javax.swing.JTextField(20);
        fullNameField.setPreferredSize(new Dimension(400, 30));
        panel.add(fullNameField, gbc);

        gbc.gridx = 0;
        panel.add(new javax.swing.JLabel("Address:"), gbc);
        gbc.gridx = 1;
        javax.swing.JTextField addressField = new javax.swing.JTextField(20);
        addressField.setPreferredSize(new Dimension(400, 30));
        panel.add(addressField, gbc);

        gbc.gridx = 0;
        panel.add(new javax.swing.JLabel("Date of Birth:"), gbc);
        gbc.gridx = 1;
        panel.add(createDateFields(0, 0, 0), gbc);

        gbc.gridx = 0;
        panel.add(new javax.swing.JLabel("Gender:"), gbc);
        gbc.gridx = 1;
        javax.swing.JComboBox<String> genderBox = new javax.swing.JComboBox<>(new String[]{"Male", "Female", "Other"});
        genderBox.setPreferredSize(new Dimension(400, 30));
        panel.add(genderBox, gbc);

        gbc.gridx = 0;
        panel.add(new javax.swing.JLabel("Email:"), gbc);
        gbc.gridx = 1;
        javax.swing.JTextField emailField = new javax.swing.JTextField(20);
        emailField.setPreferredSize(new Dimension(400, 30));
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridwidth = 2;
        javax.swing.JButton updateButton = new javax.swing.JButton("Update");
        updateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateButton.addActionListener(e -> JOptionPane.showMessageDialog(panel, "User information updated successfully."));
        panel.add(updateButton, gbc);

        return panel;
    }

    public JPanel createDateFields(int day, int month, int year) {
        javax.swing.JPanel datePanel = new javax.swing.JPanel();
        datePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        datePanel.setBackground(Color.WHITE);

        if (day == 0 && month == 0 && year == 0) {
            Calendar currentDate = Calendar.getInstance();
            day = currentDate.get(Calendar.DAY_OF_MONTH);
            month = currentDate.get(Calendar.MONTH) + 1;
            year = currentDate.get(Calendar.YEAR);
        }

        javax.swing.JSpinner daySpinner = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(day, 1, 31, 1));
        javax.swing.JSpinner monthSpinner = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(month, 1, 12, 1));
        javax.swing.JSpinner yearSpinner = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(year, 1900, Calendar.getInstance().get(Calendar.YEAR), 1));

        daySpinner.setPreferredSize(new Dimension(50, 30));
        monthSpinner.setPreferredSize(new Dimension(50, 30));
        yearSpinner.setPreferredSize(new Dimension(70, 30));

        JSpinner.DefaultEditor dayEditor = (JSpinner.DefaultEditor) daySpinner.getEditor();
        dayEditor.getTextField().setEditable(false);
        dayEditor.getTextField().setFocusable(false);

        JSpinner.DefaultEditor monthEditor = (JSpinner.DefaultEditor) monthSpinner.getEditor();
        monthEditor.getTextField().setEditable(false);
        monthEditor.getTextField().setFocusable(false);

        JSpinner.DefaultEditor yearEditor = (JSpinner.DefaultEditor) yearSpinner.getEditor();
        yearEditor.getTextField().setEditable(false);
        yearEditor.getTextField().setFocusable(false);

        monthSpinner.addChangeListener(e -> updateDaySpinner(daySpinner, (Integer) monthSpinner.getValue(), (Integer) yearSpinner.getValue()));
        yearSpinner.addChangeListener(e -> updateDaySpinner(daySpinner, (Integer) monthSpinner.getValue(), (Integer) yearSpinner.getValue()));

        updateDaySpinner(daySpinner, (Integer) monthSpinner.getValue(), (Integer) yearSpinner.getValue());

        datePanel.add(daySpinner);
        datePanel.add(new javax.swing.JLabel(" / "));
        datePanel.add(monthSpinner);
        datePanel.add(new javax.swing.JLabel(" / "));
        datePanel.add(yearSpinner);

        return datePanel;
    }
    private void updateDaySpinner(javax.swing.JSpinner daySpinner, int month, int year) {
        int maxDays = getMaxDaysInMonth(month, year);
        ((SpinnerNumberModel) daySpinner.getModel()).setMaximum(maxDays);

        int currentDay = (Integer) daySpinner.getValue();
        if (currentDay > maxDays) {
            daySpinner.setValue(maxDays);
        }
    }
    private int getMaxDaysInMonth(int month, int year) {
        return switch (month) {
            case 4, 6, 9, 11 -> 30;
            case 2 -> isLeapYear(year) ? 29 : 28;
            default -> 31;
        };

    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    private JPanel createResetPasswordPanel() {
        javax.swing.JPanel panel = new javax.swing.JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setAlignmentY(Component.TOP_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        panel.add(new javax.swing.JLabel("Old Password:"), gbc);
        gbc.gridx = 1;
        panel.add(createPasswordField(), gbc);

        gbc.gridy = 1;
        panel.add(Box.createVerticalStrut(10), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new javax.swing.JLabel("New Password:"), gbc);
        gbc.gridx = 1;
        panel.add(createPasswordField(), gbc);

        gbc.gridy = 3;
        panel.add(Box.createVerticalStrut(10), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new javax.swing.JLabel("Confirm:"), gbc);
        gbc.gridx = 1;
        panel.add(createPasswordField(), gbc);

        gbc.gridy = 5;
        panel.add(Box.createVerticalStrut(10), gbc);

        gbc.gridx = 0;
        gbc.gridwidth = 2;
        javax.swing.JButton resetButton = new javax.swing.JButton("Update Password");
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetButton.addActionListener(e -> JOptionPane.showMessageDialog(panel, "Password has been updated successfully."));
        panel.add(resetButton, gbc);

        return panel;
    }


    private JPasswordField createPasswordField() {
        javax.swing.JPasswordField passwordField = new javax.swing.JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(400, 30));
        return passwordField;
    }

    private JPanel createGeneratePasswordPanel(String email) {
        javax.swing.JPanel panel = new javax.swing.JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        javax.swing.JLabel instructionLabel1 = new javax.swing.JLabel("Click the button below to generate a new password.");
        javax.swing.JLabel instructionLabel2 = new javax.swing.JLabel("The password will be sent to your email:");

        javax.swing.JTextField emailField = new javax.swing.JTextField(email);
        emailField.setPreferredSize(new Dimension(400, 30));
        emailField.setEditable(false);
        emailField.setBackground(Color.LIGHT_GRAY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(instructionLabel1, gbc);

        gbc.gridy = 1;
        panel.add(instructionLabel2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(emailField, gbc);

        gbc.gridy = 3;
        panel.add(Box.createVerticalStrut(10), gbc);

        javax.swing.JButton generateButton = new javax.swing.JButton("Generate New Password");
        generateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        generateButton.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "A new password has been sent to your email.")
        );

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(generateButton, gbc);

        return panel;
    }

    class FlatButton extends JButton {
        public FlatButton(String text) {
            super(text);
            setBorder(null);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setOpaque(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isPressed()) {
                g.setColor(getBackground().darker());
                g.fillRect(0, 0, getWidth(), getHeight());
            } else {
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());
            }
            super.paintComponent(g);
        }
    }
}
