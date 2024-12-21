package com.user.views;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Chat_Item extends JLayeredPane {

    String text;
    private JLabel userLabel;
    private JLabel txt;
    public Chat_Item() {
        initComponents();
    }

    public void setText(String text) {
        this.text = text;
        txt.setText(text);
    }

    public void setUser(String user) {
        userLabel.setText(user);
        userLabel.setVisible(true);
    }

    public void setTime(String time) {
        JLayeredPane layer = new JLayeredPane();
        layer.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        layer.setBorder(new EmptyBorder(0, 5, 10, 5));
        JLabel label = new JLabel(time);
        label.setForeground(new Color(110, 110, 110));
        label.setHorizontalTextPosition(JLabel.LEFT);
        layer.add(label);
        add(layer);
    }
    private void initComponents() {
        userLabel = new JLabel();
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userLabel.setForeground(new Color(67, 67, 67));
        userLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        userLabel.setVisible(false);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        userPanel.setOpaque(false);
        userPanel.add(userLabel);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(userPanel);

        txt = new JLabel(text);
        txt.setHorizontalAlignment(JLabel.RIGHT);
        txt.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        JLayeredPane layer = new JLayeredPane();
        layer.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        layer.add(txt);
        add(layer);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        super.paintComponent(g);
    }
}
