package org.example;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import net.miginfocom.swing.MigLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Color;

public class Menu_Left extends javax.swing.JPanel {
    private List<Model_User_Account> userAccount;
    private String currentTab = "friend";

    public Menu_Left() {
        initComponents();
        init();
    }
    private void init() {
        sp.setVerticalScrollBar(new ScrollBar());
        menuList.setLayout(new MigLayout("fillx", "0[]0", "0[]0"));
        userAccount = new ArrayList<>();

        showMessage();
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterUsers();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterUsers();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterUsers();
            }
        });
    }
    private void filterUsers() {
        String searchText = searchField.getText().toLowerCase();

        menuList.removeAll();
        for (Model_User_Account user : userAccount) {
            if (user.getUserName().toLowerCase().contains(searchText)) {
                menuList.add(new Item_People(user, currentTab), "wrap");
            }
        }
        refreshMenuList();
    }

    private void showMessage() {
        userAccount.clear();
        menuList.removeAll();
        if (userAccount.isEmpty()) {
            userAccount.add(new Model_User_Account(1, "Alice", "Female", true));
            userAccount.add(new Model_User_Account(2, "Bob", "Male", false));
            userAccount.add(new Model_User_Account(3, "Charlie", "Male", true));
            userAccount.add(new Model_User_Account(4, "Diana", "Female", true));
            userAccount.add(new Model_User_Account(5, "Eve", "Female", false));
            userAccount.add(new Model_User_Account(6, "Frank", "Male", true));
            userAccount.add(new Model_User_Account(7, "Grace", "Female", false));
            userAccount.add(new Model_User_Account(8, "Hank", "Male", true));
            userAccount.add(new Model_User_Account(9, "Ivy", "Female", true));
            userAccount.add(new Model_User_Account(10, "Jack", "Male", false));
        }
        for (Model_User_Account d : userAccount) {
            menuList.add(new Item_People(d , "friend"), "wrap");
        }
        refreshMenuList();
    }
    private void showGroup() {
        userAccount.clear();
        menuList.removeAll();
        if (userAccount.isEmpty()) {
            userAccount.add(new Model_User_Account(1, "Liam", "Male", true));
            userAccount.add(new Model_User_Account(2, "Emma", "Female", false));
            userAccount.add(new Model_User_Account(3, "Noah", "Male", true));
            userAccount.add(new Model_User_Account(4, "Olivia", "Female", true));
            userAccount.add(new Model_User_Account(5, "William", "Male", false));
            userAccount.add(new Model_User_Account(6, "Ava", "Female", true));
            userAccount.add(new Model_User_Account(7, "James", "Male", false));
            userAccount.add(new Model_User_Account(8, "Sophia", "Female", true));
            userAccount.add(new Model_User_Account(9, "Benjamin", "Male", false));
            userAccount.add(new Model_User_Account(10, "Isabella", "Female", true));
        }
        for (Model_User_Account d : userAccount) {
            menuList.add(new Item_People(d, "group"), "wrap");
        }
        refreshMenuList();
    }
    private void showNotification() {
        userAccount.clear();
        menuList.removeAll();
        if (userAccount.isEmpty()) {
            userAccount.add(new Model_User_Account(11, "Elijah", "Male", true));
            userAccount.add(new Model_User_Account(12, "Charlotte", "Female", false));
            userAccount.add(new Model_User_Account(13, "Lucas", "Male", true));
            userAccount.add(new Model_User_Account(14, "Amelia", "Female", true));
            userAccount.add(new Model_User_Account(15, "Mason", "Male", false));
            userAccount.add(new Model_User_Account(16, "Mia", "Female", true));
            userAccount.add(new Model_User_Account(17, "Ethan", "Male", false));
            userAccount.add(new Model_User_Account(18, "Harper", "Female", true));
            userAccount.add(new Model_User_Account(19, "Logan", "Male", false));
            userAccount.add(new Model_User_Account(20, "Ella", "Female", true));
        }
        for (Model_User_Account d : userAccount) {
            menuList.add(new Item_People(d, "notification"), "wrap");
        }
        refreshMenuList();
    }
    private void showPublic() {
        userAccount.clear();
        menuList.removeAll();
        if (userAccount.isEmpty()) {
            userAccount.add(new Model_User_Account(21, "Alexander", "Male", true));
            userAccount.add(new Model_User_Account(22, "Avery", "Female", false));
            userAccount.add(new Model_User_Account(23, "Henry", "Male", true));
            userAccount.add(new Model_User_Account(24, "Scarlett", "Female", true));
            userAccount.add(new Model_User_Account(25, "Jackson", "Male", false));
            userAccount.add(new Model_User_Account(26, "Victoria", "Female", true));
            userAccount.add(new Model_User_Account(27, "Sebastian", "Male", false));
            userAccount.add(new Model_User_Account(28, "Luna", "Female", true));
            userAccount.add(new Model_User_Account(29, "David", "Male", true));
            userAccount.add(new Model_User_Account(30, "Aria", "Female", false));
        }
        for (Model_User_Account d : userAccount) {
            menuList.add(new Item_People(d, "public"), "wrap");
        }
        refreshMenuList();
    }
    private void refreshMenuList() {
        menuList.repaint();
        menuList.revalidate();
    }
    private void initComponents() {
        menu = new javax.swing.JLayeredPane();
        menuMessage = new MenuButton();
        menuGroup = new MenuButton();
        menuBox = new MenuButton();
        menuPublic = new MenuButton();
        sp = new javax.swing.JScrollPane();
        menuList = new javax.swing.JLayeredPane();

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        topPanel.setPreferredSize(new Dimension(210, 40));

        JLabel settingsLabel = new JLabel("Settings");
        settingsLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel settingsIcon = new JLabel(new ImageIcon(getClass().getResource("/icon/setting.png")));
        settingsIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                SettingPanel settingsPage = new SettingPanel();
                settingsPage.setVisible(true);
                settingsPage.setLocationRelativeTo(null);
                Window window = SwingUtilities.getWindowAncestor(settingsIcon);
                if (window != null) {
                    window.dispose();
                }
            }
        });
        topPanel.add(settingsLabel, BorderLayout.WEST);
        topPanel.add(settingsIcon, BorderLayout.EAST);


        searchField = new javax.swing.JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setBorder(new LineBorder(new Color(229, 229, 229), 3));
        searchField.setFocusTraversalKeysEnabled(false);
        setBackground(new java.awt.Color(242, 242, 242));
        menu.setBackground(new java.awt.Color(229, 229, 229));
        menu.setOpaque(true);
        menu.setLayout(new java.awt.GridLayout(1, 4));
        menuMessage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/message.png")));
        menuMessage.setSelected(true);
        menuMessage.addActionListener(evt -> {
            menuMessageActionPerformed();
            searchField.setText("");
        });

        menu.add(menuMessage);
        menuGroup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/group.png")));
        menuGroup.addActionListener(evt -> {
            menuGroupActionPerformed();
            searchField.setText("");
        });

        menu.add(menuGroup);

        menuBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/notification.png")));
        menuBox.addActionListener(evt -> {
            menuNotificationActionPerformed();
            searchField.setText("");
        });

        menu.add(menuBox);
        menuPublic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/public.png")));
        menuPublic.addActionListener(evt -> {
            menuPublicActionPerformed();
            searchField.setText("");
        });

        menu.add(menuPublic);
        sp.setBackground(new java.awt.Color(242, 242, 242));
        sp.setBorder(null);
        sp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        menuList.setBackground(new java.awt.Color(242, 242, 242));
        menuList.setOpaque(true);

        javax.swing.GroupLayout menuListLayout = new javax.swing.GroupLayout(menuList);
        menuList.setLayout(menuListLayout);
        menuListLayout.setHorizontalGroup(
                menuListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        menuListLayout.setVerticalGroup(
                menuListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 576, Short.MAX_VALUE)
        );

        sp.setViewportView(menuList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)

                        .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(sp)
                                .addContainerGap())
                        .addComponent(topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE) // Add topPanel here
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sp)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                )
        );
    }
    private void menuMessageActionPerformed() {
        if (!menuMessage.isSelected()) {
            currentTab = "friend";
            menuMessage.setSelected(true);
            menuGroup.setSelected(false);
            menuBox.setSelected(false);
            menuPublic.setSelected(false);
            showMessage();
        }
    }

    private void menuGroupActionPerformed() {
        if (!menuGroup.isSelected()) {
            currentTab = "group";
            menuMessage.setSelected(false);
            menuGroup.setSelected(true);
            menuPublic.setSelected(false);
            menuBox.setSelected(false);
            showGroup();
        }
    }

    private void menuNotificationActionPerformed() {
        if (!menuBox.isSelected()) {
            currentTab = "notification";
            menuMessage.setSelected(false);
            menuGroup.setSelected(false);
            menuPublic.setSelected(false);
            menuBox.setSelected(true);
            showNotification();
        }
    }

    private void menuPublicActionPerformed() {
        if (!menuPublic.isSelected()) {
            currentTab = "public";
            menuMessage.setSelected(false);
            menuGroup.setSelected(false);
            menuBox.setSelected(false);
            menuPublic.setSelected(true);
            showPublic();
        }
    }

    private javax.swing.JLayeredPane menu;
    private MenuButton menuBox;
    private MenuButton menuPublic;
    private MenuButton menuGroup;
    private javax.swing.JLayeredPane menuList;
    private MenuButton menuMessage;
    private javax.swing.JScrollPane sp;
    private javax.swing.JTextField searchField;

    public static class MenuButton extends JButton {
        private boolean selected = false;
        public void setSelected(boolean value) {
            selected = value;
            repaint();
        }
        public MenuButton() {
            setContentAreaFilled(false);
            setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (selected) {
                g.setColor(new Color(67, 67, 67));
                g.fillRect(0, getHeight() - 3, getWidth(), getHeight());
            }
        }

    }
}