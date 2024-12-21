package com.user.views;

import javax.swing.*;
import java.awt.*;
import com.user.Controllers.LogInController;
public class LoginPanel extends JFrame {

    public LoginPanel() {
        initComponents();
    }

    private void initComponents() {
        this.setTitle("Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(800, 500));
        this.setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Log In", SwingConstants.CENTER);
        titleLabel.setBackground(new java.awt.Color(255, 255, 255));
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setOpaque(true);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(300, 30));

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(300, 30));

        JButton loginButton = new JButton("Log in");
        loginButton.setPreferredSize(new Dimension(140, 40));
        loginButton.addActionListener(evt -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            LogInController.handleLogin(username, password, this);
        });


        JButton registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(140, 40));
        registerButton.addActionListener(evt -> {
            RegisterPanel registerPanel = new RegisterPanel();
            registerPanel.setVisible(true);
            registerPanel.setLocationRelativeTo(null);
            dispose();
        });

        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGap(250)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(usernameLabel)
                                                .addComponent(usernameField, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(passwordLabel)
                                                .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                        )
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(loginButton, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                                        .addGap(20)
                                        .addComponent(registerButton, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                                )
                        )
                        .addGap(100)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(100)
                        .addComponent(titleLabel)
                        .addGap(30)
                        .addComponent(usernameLabel)
                        .addComponent(usernameField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addGap(20)
                        .addComponent(passwordLabel)
                        .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addGap(30)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(loginButton)
                                .addComponent(registerButton)
                        )
                        .addGap(100)
        );
        this.add(mainPanel);
        this.setSize(800, 500);
        this.setLocationRelativeTo(null);
    }
}
