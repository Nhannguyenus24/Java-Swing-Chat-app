package org.example;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Menu_Right extends javax.swing.JPanel {

    public Menu_Right() {
        initComponents();
    }

    private void initComponents() {
        setBackground(new java.awt.Color(249, 249, 249));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel searchLabel = new JLabel("Search Message:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(searchLabel, gbc);

        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(180, 30));
        searchField.setBorder(new LineBorder(new Color(229, 229, 229), 3));
        searchField.setFocusTraversalKeysEnabled(false);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(searchField, gbc);

        JLabel deleteLabel = new JLabel("Delete All Messages:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(deleteLabel, gbc);

        JButton deleteAllButton = new JButton("Delete All");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(deleteAllButton, gbc);

        deleteAllButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete all messages?\nThis action cannot be undone.",
                    "Delete All", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "All messages have been deleted.");
            }
        });
    }
}
