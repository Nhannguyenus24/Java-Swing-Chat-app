package com.user.views;

import com.user.models.UserModel;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu_Right extends javax.swing.JPanel {
    UserModel user;
    Home home;
    public Menu_Right(UserModel user, Home home) {
        this.user = user;
        this.home = home;
        if (home.init) {
            initComponents();
        }
        else {
            setBackground(new java.awt.Color(249, 249, 249));
        }
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

        JButton searchButton = new JButton("Search");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(searchButton, gbc);

        JLabel spamLabel = new JLabel("Report user spam:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(spamLabel, gbc);

        JButton spamButton = new JButton("Spam");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(spamButton, gbc);

        JLabel deleteLabel = new JLabel("Delete All Messages:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(deleteLabel, gbc);

        JButton deleteAllButton = new JButton("Delete All");
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(deleteAllButton, gbc);
        if (home.chat.chat.is_group && user.isAdminGroup(home.chat.chat.chat_id)) {
            JLabel groupLabel = new JLabel("Setting group:");
            gbc.gridx = 0;
            gbc.gridy = 7;
            add(groupLabel, gbc);

            JButton groupButton = new JButton("Setting");
            gbc.gridx = 0;
            gbc.gridy = 8;
            add(groupButton, gbc);
            groupButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    GroupFrame group = new GroupFrame(user, home.chat.chatBody.chat.chat_id, home.chat.chatBody.chat.chat_name);
                    group.setVisible(true);
                    group.setLocationRelativeTo(null);
                    Window window = SwingUtilities.getWindowAncestor(groupButton);
                    if (window != null) {
                        window.dispose();
                    }
                }
            });
        }
        deleteAllButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete all messages?\nThis action cannot be undone.",
                    "Delete All", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                home.chat.chat.deleteAllMessage();
                home.updateChat(home.chat.chat.chat_id);
                home.chat.chatBody.removeAll();
                JOptionPane.showMessageDialog(null, "All messages have been deleted.");
            }
        });
        searchButton.addActionListener(e -> {
            String text = searchField.getText();
            if(text.trim().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please enter a valid message.");
            }
            else{
                home.chat.chatBody.moveToMessage(text.trim());
            }
        });
    }
}
