package com.user.views;

import java.awt.Color;
import java.awt.Font;

public class Chat_Title extends javax.swing.JPanel {

    private javax.swing.JLayeredPane layer;
    private javax.swing.JLabel lbName;

    public Chat_Title(String title) {
        initComponents();
        lbName.setText(title);
    }


//    private void statusActive() {
//        lbStatus.setText("Active now");
//        lbStatus.setForeground(new java.awt.Color(40, 147, 59));
//    }
//
//    private void setStatusText(String text) {
//        lbStatus.setText(text);
//        lbStatus.setForeground(new Color(160, 160, 160));
//    }
    private void initComponents() {

        layer = new javax.swing.JLayeredPane();
        lbName = new javax.swing.JLabel();
//        lbStatus = new javax.swing.JLabel();

        setBackground(new java.awt.Color(229, 229, 229));

        layer.setLayout(new java.awt.GridLayout(0, 1));

        lbName.setFont(new java.awt.Font("sanserif", Font.BOLD, 14));
        lbName.setForeground(new java.awt.Color(66, 66, 66));
        lbName.setText("Name");
        layer.add(lbName);

//        lbStatus.setForeground(new java.awt.Color(40, 147, 59));
//        lbStatus.setText("Active now");
//        layer.add(lbStatus);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(layer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(406, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(layer, javax.swing.GroupLayout.PREFERRED_SIZE, 34, Short.MAX_VALUE)
                                .addGap(3, 3, 3))
        );
    }
}