package com.user.views;

import com.user.models.UserModel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.util.ArrayList;
import java.util.Iterator;


public class GroupFrame extends JFrame {
    UserModel user;
    List<UserModel> friends;
    List<UserModel> members;
    JTextField groupNameField;
    DefaultListModel<String> leftListModel;
    String groupName;
    Integer id;
    public GroupFrame(UserModel user) {
        this.user = user;
        setTitle("Group Creation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        initCreateGroup();
    }
    public GroupFrame(UserModel user, Integer id, String group_name) {
        this.user = user;
        this.setTitle("Group Setting for admin");
        this.id = id;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        groupName = group_name;
        initSettingGroup();
    }
    private void showPopupMenu(JList<String> list, int x, int y, boolean isRightList, DefaultListModel<String> sourceModel, DefaultListModel<String> targetModel) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem moveOption = new JMenuItem(isRightList ? "add to group" : "kick");
        moveOption.addActionListener(e -> {
            int index = list.getSelectedIndex();
            if (index != -1) {
                String selectedValue = sourceModel.getElementAt(index);
                sourceModel.remove(index);
                targetModel.addElement(selectedValue);
            }
        });
        popupMenu.add(moveOption);
        popupMenu.show(list, x, y);
    }
    private void showPopupMember(JList<String> list, int x, int y, DefaultListModel<String> right, DefaultListModel<String> left, List<UserModel> members) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem setAdmin = new JMenuItem("Thêm Admin");
        JMenuItem removeAdmin = new JMenuItem("Xoá admin");
        JMenuItem removeMember = new JMenuItem("Xóa thành viên");
        int index = list.getSelectedIndex();
        setAdmin.addActionListener(e -> {
            user.setAdmin(id, members.get(index).userID, 1);
            left.set(index, left.get(index) + " - admin");
        });
        removeAdmin.addActionListener(e -> {
            user.setAdmin(id, members.get(index).userID, 0);
            left.set(index, left.get(index).replace(" - admin", ""));
        });
        removeMember.addActionListener(e -> {
            user.removeMember(id, members.get(index).userID);
            String selectedValue = left.getElementAt(index);
            left.remove(index);
            right.addElement(selectedValue);
        });
        if (left.get(index).endsWith(" - admin")) {
            popupMenu.add(removeAdmin);
        }
        else {
            popupMenu.add(setAdmin);
            popupMenu.add(removeMember);
        }
        popupMenu.show(list, x, y);
    }
    private void showPopupFriend(JList<String> list, int x, int y, DefaultListModel<String> right, DefaultListModel<String> left, List<UserModel> friends, List<UserModel> members) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem addMember = new JMenuItem("Thêm thành viên");
        addMember.addActionListener(e -> {
            int index = list.getSelectedIndex();
            if (index != -1){
                UserModel newMember = friends.get(index);
                user.addMember(id, newMember.userID);
                String selectedValue = right.getElementAt(index);
                right.remove(index);
                left.addElement(selectedValue);
                members.add(newMember);
                friends.remove(index);
            }
        });
        popupMenu.add(addMember);
        popupMenu.show(list, x, y);
    }
    private void initSettingGroup() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel groupNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel groupNameLabel = new JLabel("Group Name:");
        groupNameField = new JTextField(20);
        groupNameField.setText(groupName);
        groupNamePanel.add(groupNameLabel);
        groupNamePanel.add(groupNameField);
        mainPanel.add(groupNamePanel);

        JPanel listsPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        leftListModel = new DefaultListModel<>();
        JList<String> leftList = new JList<>(leftListModel);
        JScrollPane leftScrollPane = new JScrollPane(leftList);
        leftScrollPane.setBorder(BorderFactory.createTitledBorder("Members in Group"));
        leftScrollPane.setBackground(new Color(242, 242, 242));
        leftScrollPane.getViewport().setBackground(new Color(242, 242, 242));
        leftScrollPane.setVerticalScrollBar(new ScrollBar());
        leftScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        listsPanel.add(leftScrollPane);

        friends = user.getFriends();
        members = user.getMemberGroup(id);

        for (Iterator<UserModel> iterator = friends.iterator(); iterator.hasNext(); ) {
            UserModel friend = iterator.next();
            for (UserModel member : members) {
                if (friend.userID == member.userID) {
                    iterator.remove();
                    break;
                }
            }
        }
        DefaultListModel<String> rightListModel = new DefaultListModel<>();
        JList<String> rightList = new JList<>(rightListModel);
        for (UserModel friend : friends) {
            rightListModel.addElement(friend.userName);
        }
        for (UserModel member : members) {
            if (member.userID == user.userID) {
                leftListModel.addElement(member.userName + "(you) - admin");
            }
            else if (member.isAdminGroup(id)) {
                leftListModel.addElement(member.userName + " - admin");
            }
            else
                leftListModel.addElement(member.userName);
        }
        JScrollPane rightScrollPane = new JScrollPane(rightList);
        rightScrollPane.setBorder(BorderFactory.createTitledBorder("Friends"));
        rightScrollPane.setBackground(new Color(242, 242, 242));
        rightScrollPane.getViewport().setBackground(new Color(242, 242, 242));
        rightScrollPane.setVerticalScrollBar(new ScrollBar());
        rightScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        listsPanel.add(rightScrollPane);

        mainPanel.add(listsPanel);

        rightList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int index = rightList.locationToIndex(e.getPoint());
                    if (index != -1) {
                        rightList.setSelectedIndex(index);
                        if (rightListModel.getElementAt(index).contains("(you) - admin")) {
                            return;
                        }
                        showPopupFriend(rightList, e.getX(), e.getY(), rightListModel,  leftListModel,friends, members);
                    }
                }
            }
        });

        leftList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int index = leftList.locationToIndex(e.getPoint());
                    if (index != -1) {
                        leftList.setSelectedIndex(index);
                        showPopupMember(leftList, e.getX(), e.getY(), rightListModel,  leftListModel,members);
                    }
                }
            }
        });

        JButton actionButton = new JButton("Change");
        JButton returnChat = new JButton("Return Chat");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(actionButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(returnChat);

        actionButton.addActionListener(e -> {
            if (groupNameField.getText().trim().isEmpty() || leftListModel.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Group name or member list cannot be empty!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            } else {
                user.changeGroupName(id, groupNameField.getText().trim());
            }
        });
        returnChat.addActionListener(e -> {
            ChatUI chat = new ChatUI(user);
            chat.setVisible(true);
            chat.setLocationRelativeTo(null);
            this.dispose();
        });
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonPanel);

        add(mainPanel);
    }

    private void initCreateGroup() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel groupNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel groupNameLabel = new JLabel("Group Name:");
        groupNameField = new JTextField(20);
        groupNamePanel.add(groupNameLabel);
        groupNamePanel.add(groupNameField);
        mainPanel.add(groupNamePanel);

        JPanel listsPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        leftListModel = new DefaultListModel<>();
        JList<String> leftList = new JList<>(leftListModel);
        JScrollPane leftScrollPane = new JScrollPane(leftList);
        leftScrollPane.setBorder(BorderFactory.createTitledBorder("Members in Group"));
        leftScrollPane.setBackground(new Color(242, 242, 242));
        leftScrollPane.getViewport().setBackground(new Color(242, 242, 242));
        leftScrollPane.setVerticalScrollBar(new ScrollBar());
        leftScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        listsPanel.add(leftScrollPane);

        friends = user.getFriends();
        DefaultListModel<String> rightListModel = new DefaultListModel<>();
        JList<String> rightList = new JList<>(rightListModel);
        for (UserModel friend : friends) {
            rightListModel.addElement(friend.userName);
        }
        JScrollPane rightScrollPane = new JScrollPane(rightList);
        rightScrollPane.setBorder(BorderFactory.createTitledBorder("Friends"));
        rightScrollPane.setBackground(new Color(242, 242, 242));
        rightScrollPane.getViewport().setBackground(new Color(242, 242, 242));
        rightScrollPane.setVerticalScrollBar(new ScrollBar());
        rightScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        listsPanel.add(rightScrollPane);

        mainPanel.add(listsPanel);

        rightList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int index = rightList.locationToIndex(e.getPoint());
                    if (index != -1) {
                        rightList.setSelectedIndex(index);
                        showPopupMenu(rightList, e.getX(), e.getY(), true, rightListModel, leftListModel);
                    }
                }
            }
        });

        leftList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int index = leftList.locationToIndex(e.getPoint());
                    if (index != -1) {
                        leftList.setSelectedIndex(index);
                        showPopupMenu(leftList, e.getX(), e.getY(), false, leftListModel, rightListModel);
                    }
                }
            }
        });

        JButton actionButton = new JButton("Create Group");
        JButton returnChat = new JButton("Return Chat");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(actionButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(returnChat);

        actionButton.addActionListener(e -> {
            if (groupNameField.getText().trim().isEmpty() || leftListModel.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Group name or member list cannot be empty!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            } else {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < leftListModel.size(); i++) {
                    list.add(leftListModel.get(i));
                }
                user.createGroup(groupNameField.getText().trim(), list);

                JOptionPane.showMessageDialog(this, "Group Created Successfully.");
                ChatUI chat = new ChatUI(user);
                chat.setVisible(true);
                chat.setLocationRelativeTo(null);
                this.dispose();
            }
        });
        returnChat.addActionListener(e -> {
            ChatUI chat = new ChatUI(user);
            chat.setVisible(true);
            chat.setLocationRelativeTo(null);
            this.dispose();
        });
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonPanel);

        add(mainPanel);
    }

    public static class ScrollBar extends JScrollBar {

        public ScrollBar() {
            setUI(new ModernScrollBarUI());
            setPreferredSize(new Dimension(5, 5));
            setBackground(new Color(242, 242, 242));
            setUnitIncrement(20);
        }

        private static class ModernScrollBarUI extends BasicScrollBarUI {

            private static final int SCROLL_BAR_ALPHA_ROLLOVER = 100;
            private static final int SCROLL_BAR_ALPHA = 50;
            private static final int THUMB_SIZE = 8;
            private static final Color THUMB_COLOR = Color.BLACK;

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return new InvisibleScrollBarButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return new InvisibleScrollBarButton();
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                int alpha = isThumbRollover() ? SCROLL_BAR_ALPHA_ROLLOVER : SCROLL_BAR_ALPHA;
                int orientation = scrollbar.getOrientation();
                int x = thumbBounds.x;
                int y = thumbBounds.y;

                int width = orientation == JScrollBar.VERTICAL ? THUMB_SIZE : thumbBounds.width;
                width = Math.max(width, THUMB_SIZE);

                int height = orientation == JScrollBar.VERTICAL ? thumbBounds.height : THUMB_SIZE;
                height = Math.max(height, THUMB_SIZE);

                Graphics2D graphics2D = (Graphics2D) g.create();
                graphics2D.setColor(new Color(THUMB_COLOR.getRed(), THUMB_COLOR.getGreen(), THUMB_COLOR.getBlue(), alpha));
                graphics2D.fillRect(x, y, width, height);
                graphics2D.dispose();
            }

            private static class InvisibleScrollBarButton extends JButton {

                private InvisibleScrollBarButton() {
                    setOpaque(false);
                    setFocusable(false);
                    setFocusPainted(false);
                    setBorderPainted(false);
                    setBorder(BorderFactory.createEmptyBorder());
                }
            }
        }
    }
}
