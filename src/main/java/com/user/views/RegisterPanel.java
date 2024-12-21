package com.user.views;

import javax.swing.*;
import java.awt.Font;
import com.user.Controllers.RegisterController;

public class RegisterPanel extends JFrame {
    public RegisterPanel() {
        initComponents();
    }

    private void initComponents() {

        JPanel jPanel1 = new JPanel();
        JPanel jPanel2 = new JPanel();
        JPanel jPanel3 = new JPanel();
        JLabel jLabel1 = new JLabel();
        JLabel jLabel4 = new JLabel();
        JLabel jLabel5 = new JLabel();
        JLabel jLabelFullName = new JLabel();
        JTextField jTextFieldFullName = new JTextField();
        JLabel jLabelAddress = new JLabel();
        JTextField jTextFieldAddress = new JTextField();
        JLabel jLabelDob = new JLabel();
        JTextField jTextFieldDob = new JTextField();
        JLabel jLabelGender = new JLabel();
        JComboBox<String> jComboBoxGender = new JComboBox<>();
        JTextField jTextUsername = new JTextField();
        JLabel jLabel6 = new JLabel();
        JTextField jTextEmail = new JTextField();
        JLabel jLabel7 = new JLabel();
        JPasswordField jPasswordField1 = new JPasswordField();
        JButton jButton1 = new JButton();
        JButton jButton2 = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Register");
        setPreferredSize(new java.awt.Dimension(800, 500));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("sanserif", Font. BOLD, 24));
        jLabel1.setText("Personal Info");

        jLabelFullName.setText("Full Name");

        jLabelAddress.setText("Address");

        jLabelDob.setText("Date of Birth");

        jLabelGender.setText("Gender");

        jComboBoxGender.setModel(new DefaultComboBoxModel<>(new String[] { "Male", "Female", "Other" }));

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabelFullName)
                                        .addComponent(jTextFieldFullName, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabelAddress)
                                        .addComponent(jTextFieldAddress, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabelDob)
                                        .addComponent(jTextFieldDob, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabelGender)
                                        .addComponent(jComboBoxGender, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel1)
                                .addGap(30, 30, 30)
                                .addComponent(jLabelFullName)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldFullName, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jLabelAddress)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldAddress, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jLabelDob)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldDob, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jLabelGender)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxGender, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(90, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 400, 500);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("sanserif", Font.BOLD, 24));
        jLabel4.setText("Account");

        jLabel5.setText("Username");


        jLabel6.setText("Email");


        jLabel7.setText("Password");


        jButton1.setText("Register");
        jButton1.addActionListener(evt -> {
            String fullName = jTextFieldFullName.getText();
            String address = jTextFieldAddress.getText();
            String dob = jTextFieldDob.getText();
            String gender = (String) jComboBoxGender.getSelectedItem();
            String username = jTextUsername.getText();
            String email = jTextEmail.getText();
            String password = new String(jPasswordField1.getPassword());
            RegisterController.handleRegister(fullName, address, dob, gender, username, email, password, this);
        });

        jButton2.setText("Back to Login");
        jButton2.addActionListener(evt -> jButton2ActionPerformed());

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(145, 145, 145)
                                                .addComponent(jLabel4))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(44, 44, 44)
                                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel5)
                                                        .addComponent(jTextUsername, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel6)
                                                        .addComponent(jTextEmail, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel7)
                                                        .addComponent(jPasswordField1, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                                .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)))))
                                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel4)
                                .addGap(30, 30, 30)
                                .addComponent(jLabel5)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextUsername, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jLabel6)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextEmail, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jLabel7)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPasswordField1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(91, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3);
        jPanel3.setBounds(400, 0, 400, 500);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void jButton2ActionPerformed() {
        LoginPanel loginFrame = new LoginPanel();
        loginFrame.setVisible(true);
        loginFrame.pack();
        loginFrame.setLocationRelativeTo(null);
        this.dispose();
    }
}
