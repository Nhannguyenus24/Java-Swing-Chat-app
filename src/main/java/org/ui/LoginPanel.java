package org.ui;

import javax.swing.*;
import java.awt.*;
public class LoginPanel extends javax.swing.JFrame {

    public LoginPanel() {
        initComponents();
    }

    private void initComponents() {
        this.setTitle("Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(800, 500));
        this.setResizable(false);

        javax.swing.JPanel mainPanel = new javax.swing.JPanel();
        mainPanel.setBackground(Color.WHITE);

        javax.swing.JLabel titleLabel = new javax.swing.JLabel("Log In", SwingConstants.CENTER);
        titleLabel.setBackground(new java.awt.Color(255, 255, 255));
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setOpaque(true);

        javax.swing.JLabel emailLabel = new javax.swing.JLabel("Username");
        emailLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        javax.swing.JTextField emailField = new javax.swing.JTextField();
        emailField.setPreferredSize(new Dimension(300, 30));

        javax.swing.JLabel passwordLabel = new javax.swing.JLabel("Password");
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        javax.swing.JPasswordField passwordField = new javax.swing.JPasswordField();
        passwordField.setPreferredSize(new Dimension(300, 30));

        javax.swing.JButton loginButton = new javax.swing.JButton("Log in");
        loginButton.setPreferredSize(new Dimension(140, 40));
        loginButton.addActionListener(evt -> {
            ChatUI chat = new ChatUI();
            chat.setVisible(true);
            chat.setLocationRelativeTo(null);
            dispose();
        });

        javax.swing.JButton registerButton = new javax.swing.JButton("Register");
        registerButton.setPreferredSize(new Dimension(140, 40));
        registerButton.addActionListener(evt -> {
            RegisterPanel registerPanel = new RegisterPanel();
            registerPanel.setVisible(true);
            registerPanel.setLocationRelativeTo(null);
            dispose();
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGap(250)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(emailLabel)
                                                .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(passwordLabel)
                                                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        )
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(20)
                                        .addComponent(registerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                )
                        )
                        .addGap(100)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(100)
                        .addComponent(titleLabel)
                        .addGap(30)
                        .addComponent(emailLabel)
                        .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20)
                        .addComponent(passwordLabel)
                        .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
