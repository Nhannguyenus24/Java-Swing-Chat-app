package com.user.views;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import net.miginfocom.swing.MigLayout;
import com.user.models.UserModel;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Color;
import com.user.events.EventChat;

public class Menu_Left extends JPanel {
    private List<UserModel> userAccount;
    private String currentTab = "friend";
    public UserModel currentAccount;
    private final Home home;
    private MenuButton menuBox;
    private MenuButton menuPublic;
    private MenuButton menuGroup;
    private JLayeredPane menuList;
    private MenuButton menuMessage;
    private JScrollPane sp;
    private JTextField searchField;
    public Menu_Left(UserModel user, Home home) {
        this.currentAccount = user;
        this.home = home;
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
        for (UserModel user : userAccount) {
            if (user.userName.toLowerCase().contains(searchText)) {
                menuList.add(new Item_People(currentAccount, user, currentTab, new EventChat() {
                    @Override
                    public void onItemClicked(UserModel user) {
                        home.updateChat(user);
                    }
                    @Override
                    public void onItemClicked(Integer groupid){
                        home.updateChat(groupid);
                    }
                }), "wrap");
            }
        }
        refreshMenuList();
    }

    private void showMessage() {
        userAccount.clear();
        menuList.removeAll();
        userAccount = currentAccount.getFriends();
        for (UserModel d : userAccount) {
            menuList.add(new Item_People(currentAccount, d , "friend", new EventChat() {
                @Override
                public void onItemClicked(UserModel user) {
                    home.updateChat(user);
                }
                @Override
                public void onItemClicked(Integer groupid){
                    home.updateChat(groupid);
                }
            }), "wrap");
        }
        refreshMenuList();
    }
    private void showGroup() {
        userAccount.clear();
        menuList.removeAll();
        List<String> groupNames = currentAccount.getGroupName();
        List<Integer> groupIds = currentAccount.getGroupId();
        for (int i = 0; i < groupNames.size(); i++) {
            menuList.add(new Item_People(currentAccount, groupIds.get(i), groupNames.get(i), new EventChat() {
                @Override
                public void onItemClicked(UserModel user) {
                    home.updateChat(user);
                }
                @Override
                public void onItemClicked(Integer groupid){
                    home.updateChat(groupid);
                }
            }), "wrap");
        }
        refreshMenuList();
    }
    private void showNotification() {
        userAccount.clear();
        menuList.removeAll();
        userAccount = currentAccount.getFriendRequests();
        for (UserModel d : userAccount) {
            menuList.add(new Item_People(currentAccount, d, "notification"), "wrap");
        }
        refreshMenuList();
    }
    private void showPublic() {
        userAccount.clear();
        menuList.removeAll();
        userAccount = currentAccount.getStrangers();
        for (UserModel d : userAccount) {
            menuList.add(new Item_People(currentAccount, d, "public"), "wrap");
        }
        refreshMenuList();
    }
    private void refreshMenuList() {
        menuList.repaint();
        menuList.revalidate();
    }
    private void initComponents() {
        JLayeredPane menu = new JLayeredPane();
        menuMessage = new MenuButton();
        menuGroup = new MenuButton();
        menuBox = new MenuButton();
        menuPublic = new MenuButton();
        sp = new JScrollPane();
        menuList = new JLayeredPane();

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        topPanel.setPreferredSize(new Dimension(210, 40));

        JLabel settingsLabel = new JLabel("Settings");
        settingsLabel.setFont(new Font("sanserif", Font.BOLD, 16));

        JLabel settingsIcon = new JLabel(new ImageIcon(getClass().getResource("/icon/setting.png")));
        settingsIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                SettingPanel settingsPage = new SettingPanel(currentAccount);
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

        JPanel groupPanel = new JPanel();
        groupPanel.setLayout(new BorderLayout());
        groupPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        groupPanel.setPreferredSize(new Dimension(210, 40));

        JLabel groupLabel = new JLabel("Create group");
        groupLabel.setFont(new Font("sanserif", Font.BOLD, 16));

        JLabel groupIcon = new JLabel(new ImageIcon(getClass().getResource("/icon/create.png")));
        groupIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                GroupFrame group = new GroupFrame(currentAccount);
                group.setVisible(true);
                group.setLocationRelativeTo(null);
                Window window = SwingUtilities.getWindowAncestor(groupIcon);
                if (window != null) {
                    window.dispose();
                }
            }
        });
        groupPanel.add(groupLabel, BorderLayout.WEST);
        groupPanel.add(groupIcon, BorderLayout.EAST);


        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setBorder(new LineBorder(new Color(229, 229, 229), 3));
        searchField.setFocusTraversalKeysEnabled(false);
        setBackground(new java.awt.Color(242, 242, 242));
        menu.setBackground(new java.awt.Color(229, 229, 229));
        menu.setOpaque(true);
        menu.setLayout(new java.awt.GridLayout(1, 4));
        menuMessage.setIcon(new ImageIcon(getClass().getResource("/icon/message.png")));
        menuMessage.setSelected(true);
        menuMessage.addActionListener(evt -> {
            menuMessageActionPerformed();
            searchField.setText("");
        });

        menu.add(menuMessage);
        menuGroup.setIcon(new ImageIcon(getClass().getResource("/icon/group.png")));
        menuGroup.addActionListener(evt -> {
            menuGroupActionPerformed();
            searchField.setText("");
        });

        menu.add(menuGroup);

        menuBox.setIcon(new ImageIcon(getClass().getResource("/icon/notification.png")));
        menuBox.addActionListener(evt -> {
            menuNotificationActionPerformed();
            searchField.setText("");
        });

        menu.add(menuBox);
        menuPublic.setIcon(new ImageIcon(getClass().getResource("/icon/public.png")));
        menuPublic.addActionListener(evt -> {
            menuPublicActionPerformed();
            searchField.setText("");
        });

        menu.add(menuPublic);
        sp.setBackground(new java.awt.Color(242, 242, 242));
        sp.setBorder(null);
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        menuList.setBackground(new java.awt.Color(242, 242, 242));
        menuList.setOpaque(true);

        GroupLayout menuListLayout = new GroupLayout(menuList);
        menuList.setLayout(menuListLayout);
        menuListLayout.setHorizontalGroup(
                menuListLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        menuListLayout.setVerticalGroup(
                menuListLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 576, Short.MAX_VALUE)
        );

        sp.setViewportView(menuList);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)

                        .addComponent(menu, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(searchField, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(sp)
                                .addContainerGap())
                        .addComponent(groupPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(topPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(menu, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sp)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(groupPanel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addGap(5)
                                .addComponent(topPanel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
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