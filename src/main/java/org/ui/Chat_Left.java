package org.ui;

import java.awt.Color;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
public class Chat_Left extends javax.swing.JLayeredPane {

    public Chat_Left() {
        initComponents();
        txt.setBackground(new Color(242, 242, 242));
    }

    public void setText(String text) {
        if (text.isEmpty()) {
            txt.hideText();
        } else {
            txt.setText(text);
        }
    }
    public void setTime() {
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String currentTime = now.format(formatter);
        txt.setTime(currentTime);
    }
    private void initComponents() {

        txt = new Chat_Item();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(txt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }
    private Chat_Item txt;
}