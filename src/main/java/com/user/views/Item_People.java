package com.user.views;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Font;
import java.io.IOException;

import com.user.models.UserModel;
import javax.swing.*;
import com.user.events.EventChat;

public class Item_People extends JPanel {

    private final UserModel account;
    private final UserModel user;
    private final String status;
    private final EventChat eventchat;
    private final Integer group_id;

    private ActiveStatus activeStatus;
    private JLabel lb;
    private JLabel lbStatus;
    private JButton btnAccept;
    private JButton btnReject;
    private JButton btnAddFriend;

    public Item_People(UserModel account, UserModel user, String status, EventChat eventchat) {
        this.eventchat = eventchat;
        this.account = account;
        this.group_id = null;
        this.user = user;
        this.status = status;
        initComponents();
        lb.setText(user.userName);
        activeStatus.setActive(user.status);
        init();
    }

    public Item_People(UserModel account, UserModel user, String status) {
        this.eventchat = null;
        this.group_id = null;
        this.account = account;
        this.user = user;
        this.status = status;
        initComponents();
        lb.setText(user.userName);
        activeStatus.setActive(user.status);
        init();
    }
    public Item_People(UserModel user, Integer group_id, String group_name, EventChat eventchat) {
        this.user = null;
        this.eventchat = eventchat;
        this.account = user;
        this.group_id = group_id;
        this.status = "group";
        initComponents();
        lb.setText(group_name);
        init();
    }
    private void init() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(230, 230, 230));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(new Color(242, 242, 242));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (status.equals("friend"))
                    eventchat.onItemClicked(user);
                else if (status.equals("group"))
                    eventchat.onItemClicked(group_id);
            }

            @Override
            public void mousePressed(MouseEvent e){
                if (SwingUtilities.isRightMouseButton(e) && status.equals("friend"))
                    showPopupMenu(e.getX(), e.getY());
            }
            });
    }

    private void initComponents() {
        lb = new JLabel();
        lbStatus = new JLabel();
        activeStatus = new ActiveStatus();
        btnAccept = new JButton();
        btnReject = new JButton();
        btnAddFriend = new JButton();

        setBackground(new java.awt.Color(242, 242, 242));

        lb.setFont(new java.awt.Font("sanserif", Font.PLAIN, 14));

        lbStatus.setFont(new java.awt.Font("sanserif", Font.ITALIC, 12));
        lbStatus.setForeground(new java.awt.Color(117, 117, 117));
        switch (status) {
            case "friend":
                lbStatus.setText("friend");
                break;
            case "group":
                lbStatus.setText("group");
                break;
            case "notification":
                lbStatus.setText("request");
                break;
            default:
                lbStatus.setText("new user");
                break;
        }

        activeStatus.setActive(true);

        btnAccept.setText("Accept");
        btnReject.setText("Reject");
        btnAddFriend.setText("Add Friend");

        btnAccept.addActionListener(e -> onAccept());
        btnReject.addActionListener(e -> onReject());
        btnAddFriend.addActionListener(e -> onAddFriend());

        updateLayoutBasedOnStatus();
    }

    private void showPopupMenu(int x, int y) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem unfriendOption = new JMenuItem("unfriend");
        JMenuItem blockOption = new JMenuItem("block");
        unfriendOption.addActionListener(e -> {
            account.unFriend(user.userID);
            Item_People.this.setVisible(false);
            Item_People.this.validate();
            Item_People.this.repaint();
        });
        blockOption.addActionListener(e -> {
            account.blockFriend(user.userID, true);
            Item_People.this.setVisible(false);
            Item_People.this.validate();
            Item_People.this.repaint();
        });

        popupMenu.add(unfriendOption);
        popupMenu.add(blockOption);
        popupMenu.show(Item_People.this, x, y);
    }

    private void updateLayoutBasedOnStatus() {
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        switch (status) {
            case "notification":
                layout.setHorizontalGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(lbStatus)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnAccept)
                                        .addComponent(btnReject)
                                        .addGap(5, 5, 5)
                                )
                                .addComponent(lb, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(lb)
                                                        .addGap(6, 6, 6)
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                .addComponent(btnReject)
                                                                .addComponent(btnAccept)
                                                                .addComponent(lbStatus, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                        .addGap(3, 3, 3))
                );
                break;

            case "public":
                layout.setHorizontalGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(lbStatus)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnAddFriend)
                                        .addGap(5, 5, 5)
                                )
                                .addComponent(lb, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(lb)
                                                        .addGap(6, 6, 6)
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                .addComponent(btnAddFriend)
                                                                .addComponent(lbStatus, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                        .addGap(3, 3, 3))
                );
                break;

            default: // "friend" or "group"
                layout.setHorizontalGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(lb, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(lbStatus)
                                                .addGap(3, 3, 3)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                );

                layout.setVerticalGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(lb)
                                                        .addGap(6, 6, 6)
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                .addComponent(lbStatus, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                        .addGap(3, 3, 3))
                );
                break;
        }
    }

    private void onAccept() {
        account.acceptInvite(user.userID);
        this.setVisible(false);
        revalidate();
    }

    private void onReject() {
        account.rejectInvite(user.userID);
        this.setVisible(false);
        revalidate();
    }

    private void onAddFriend() {
        account.sendFriendRequest(user.userID);
        this.setVisible(false);
        revalidate();
    }

    public static class ActiveStatus extends Component {
        public void setActive(boolean active) {
            this.active = active;
            repaint();
        }

        private boolean active;

        public ActiveStatus() {
            setPreferredSize(new Dimension(8, 8));
        }

        @Override
        public void paint(Graphics g) {
            if (active) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(62, 165, 49));
                g2.fillOval(0, (getHeight() / 2) - 4, 8, 8);
            }
        }
    }
}
