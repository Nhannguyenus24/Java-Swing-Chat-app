package com.user.views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.List;

import com.user.models.UserModel;
import com.Main;

public class SettingPanel extends JFrame {
    UserModel user;
    private final JPanel mainPanel;
    private final CardLayout cardLayout;

    private final Color defaultColor = Color.LIGHT_GRAY;
    private final Color selectedColor = new Color(100, 150, 180);
    private JButton selectedButton;

    public SettingPanel(UserModel user) {
        this.user = user;
        this.setTitle("User Management");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 500);
        this.setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel userInfoPanel = createUserInfoPanel();
        JPanel resetPasswordPanel = createResetPasswordPanel();
        JPanel generatePasswordPanel = createGeneratePasswordPanel(user.email);
        JPanel blockListPanel = createBlockListPanel();

        mainPanel.add(userInfoPanel, "UserInfo");
        mainPanel.add(resetPasswordPanel, "ResetPassword");
        mainPanel.add(generatePasswordPanel, "GeneratePassword");
        mainPanel.add(blockListPanel, "BlockList");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton userInfoButton = createNavButton("User Info");
        JButton resetPasswordButton = createNavButton("Reset Password");
        JButton generatePasswordButton = createNavButton("Generate Password");
        JButton blockListButton = createNavButton("Block List");

        selectButton(userInfoButton);

        userInfoButton.addActionListener((e) -> switchPanel(userInfoButton, "UserInfo"));
        resetPasswordButton.addActionListener((e) -> switchPanel(resetPasswordButton, "ResetPassword"));
        generatePasswordButton.addActionListener((e) -> switchPanel(generatePasswordButton, "GeneratePassword"));
        blockListButton.addActionListener((e) -> switchPanel(blockListButton, "BlockList"));

        buttonPanel.add(userInfoButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(resetPasswordButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(generatePasswordButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(blockListButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        JButton exitButton = createNavButton("Back to chat");
        exitButton.addActionListener(e -> {
            ChatUI chat = new ChatUI(user);
            chat.setVisible(true);
            chat.setLocationRelativeTo(null);
            this.dispose();
        });
        buttonPanel.add(exitButton);

        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        if (user.isAdmin) {
            JButton adminButton = createNavButton("Admin mode");
            adminButton.addActionListener(e -> this.dispose());
            buttonPanel.add(adminButton);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JButton logoutButton = createNavButton("Log out");
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to log out?",
                    "Log Out", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Main.main(new String[0]);
                this.dispose();
            }
        });
        buttonPanel.add(logoutButton);

        this.add(buttonPanel, BorderLayout.WEST);
        this.add(mainPanel, BorderLayout.CENTER);

        this.setVisible(true);
    }

    private void showPopupMenu(JList<String> list, int x, int y, DefaultListModel<String> sourceModel, List<UserModel> blockers) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem moveOption = new JMenuItem("unblock");
        moveOption.addActionListener(e -> {
            int index = list.getSelectedIndex();
            if (index != -1) {
                sourceModel.remove(index);
                user.blockFriend(blockers.get(index).userID, false);
            }
        });
        popupMenu.add(moveOption);
        popupMenu.show(list, x, y);
    }

    private JPanel createBlockListPanel() {
        JPanel blockListPanel = new JPanel();
        blockListPanel.setBackground(Color.WHITE);
        List<UserModel> blockList = user.getListBlock();
        blockListPanel.setLayout(new BoxLayout(blockListPanel, BoxLayout.Y_AXIS)); // Sửa mainPanel thành blockListPanel
        blockListPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        DefaultListModel<String> rightListModel = new DefaultListModel<>();
        JList<String> rightList = new JList<>(rightListModel);

        for (UserModel friend : blockList) {
            rightListModel.addElement(friend.userName);
        }

        JScrollPane rightScrollPane = new JScrollPane(rightList);
        rightScrollPane.setBorder(BorderFactory.createTitledBorder("Block list"));
        rightScrollPane.setBackground(new Color(242, 242, 242));
        rightScrollPane.getViewport().setBackground(new Color(242, 242, 242));
        rightScrollPane.setVerticalScrollBar(new GroupFrame.ScrollBar());
        rightScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        blockListPanel.add(rightScrollPane);

        rightList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int index = rightList.locationToIndex(e.getPoint());
                    if (index != -1) {
                        rightList.setSelectedIndex(index);
                        showPopupMenu(rightList, e.getX(), e.getY(), rightListModel, blockList);
                    }
                }
            }
        });
        return blockListPanel;
    }

    private JButton createNavButton(String text) {
        FlatButton button = new FlatButton(text);
        button.setPreferredSize(new Dimension(150, 40));
        button.setMaximumSize(new Dimension(150, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(defaultColor);
        return button;
    }

    private void switchPanel(JButton button, String panelName) {
        cardLayout.show(mainPanel, panelName);
        selectButton(button);
    }

    private void selectButton(JButton button) {
        if (selectedButton != null) {
            selectedButton.setBackground(defaultColor);
        }
        button.setBackground(selectedColor);
        selectedButton = button;
    }
    private JPanel createUserInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setAlignmentY(Component.TOP_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        JTextField usernameField = new JTextField(user.userName);
        usernameField.setPreferredSize(new Dimension(400, 30));
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        panel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        JTextField fullNameField = new JTextField(user.fullname);
        fullNameField.setPreferredSize(new Dimension(400, 30));
        panel.add(fullNameField, gbc);

        gbc.gridx = 0;
        panel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        JTextField addressField = new JTextField(user.address);
        addressField.setPreferredSize(new Dimension(400, 30));
        panel.add(addressField, gbc);

        gbc.gridx = 0;
        panel.add(new JLabel("Date of Birth:"), gbc);
        gbc.gridx = 1;
        panel.add(createDateFields(user.dob.getDayOfMonth(), user.dob.getMonthValue(), user.dob.getYear()), gbc);

        gbc.gridx = 0;
        panel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        genderBox.setPreferredSize(new Dimension(400, 30));
        panel.add(genderBox, gbc);

        gbc.gridx = 0;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        JTextField emailField = new JTextField(user.email);
        emailField.setPreferredSize(new Dimension(400, 30));
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton updateButton = new JButton("Update");
        updateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            updateButton.addActionListener(e -> {
                try {
                    String username = usernameField.getText();
                    String fullName = fullNameField.getText();
                    String address = addressField.getText();
                    int day = (Integer) ((JSpinner) ((JPanel) panel.getComponent(7)).getComponent(0)).getValue();
                    int month = (Integer) ((JSpinner) ((JPanel) panel.getComponent(7)).getComponent(2)).getValue();
                    int year = (Integer) ((JSpinner) ((JPanel) panel.getComponent(7)).getComponent(4)).getValue();
                    String dob = String.format("%04d-%02d-%02d", year, month, day);
                    String gender = (String) genderBox.getSelectedItem();
                    String email = emailField.getText();

                    user.updateUserInfo(user.userID, username, fullName, address, dob, gender, email);

                    JOptionPane.showMessageDialog(panel, "User information updated successfully.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Failed to update user information: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        panel.add(updateButton, gbc);

        return panel;
    }

    public JPanel createDateFields(int day, int month, int year) {
        JPanel datePanel = new JPanel();
        datePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        datePanel.setBackground(Color.WHITE);
        JSpinner daySpinner = new JSpinner(new SpinnerNumberModel(day, 1, 31, 1));
        JSpinner monthSpinner = new JSpinner(new SpinnerNumberModel(month, 1, 12, 1));
        JSpinner yearSpinner = new JSpinner(new SpinnerNumberModel(year, 1900, Calendar.getInstance().get(Calendar.YEAR), 1));

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
        datePanel.add(new JLabel(" / "));
        datePanel.add(monthSpinner);
        datePanel.add(new JLabel(" / "));
        datePanel.add(yearSpinner);

        return datePanel;
    }
    private void updateDaySpinner(JSpinner daySpinner, int month, int year) {
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
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setAlignmentY(Component.TOP_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        panel.add(new JLabel("Old Password:"), gbc);
        gbc.gridx = 1;
        JPasswordField oldPasswordField = createPasswordField();
        panel.add(oldPasswordField, gbc);

        gbc.gridy = 1;
        panel.add(Box.createVerticalStrut(10), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("New Password:"), gbc);
        gbc.gridx = 1;
        JPasswordField newPasswordField = createPasswordField();
        panel.add(newPasswordField, gbc);

        gbc.gridy = 3;
        panel.add(Box.createVerticalStrut(10), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Confirm:"), gbc);
        gbc.gridx = 1;
        JPasswordField confirmPasswordField = createPasswordField();
        panel.add(confirmPasswordField, gbc);

        gbc.gridy = 5;
        panel.add(Box.createVerticalStrut(10), gbc);

        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton resetButton = new JButton("Update Password");
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetButton.addActionListener(e -> {
            String oldPassword = new String(oldPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(panel, "New password and confirm password do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = user.changePassword(user.userID, oldPassword, newPassword);
            if (success)
                JOptionPane.showMessageDialog(panel, "Password has been updated successfully.");
            else
                JOptionPane.showMessageDialog(panel, "Old password is incorrect.");
        });

        panel.add(resetButton, gbc);

        return panel;
    }


    private JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(400, 30));
        return passwordField;
    }

    private JPanel createGeneratePasswordPanel(String email) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel instructionLabel1 = new JLabel("Click the button below to generate a new password.");
        JLabel instructionLabel2 = new JLabel("The password will be sent to your email:");

        JTextField emailField = new JTextField(email);
        emailField.setPreferredSize(new Dimension(400, 30));
        emailField.setEditable(false);
        emailField.setBackground(Color.LIGHT_GRAY);
        emailField.setFocusable(false);
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

        JButton generateButton = new JButton("Generate New Password");
        generateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        generateButton.addActionListener(e -> {
            user.createRandomPassword();
            JOptionPane.showMessageDialog(this, "A new password has been sent to your email.");
            }
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
