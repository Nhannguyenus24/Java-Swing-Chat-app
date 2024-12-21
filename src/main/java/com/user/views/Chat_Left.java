package com.user.views;

import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.user.models.ChatModel;

public class Chat_Left extends JLayeredPane {
    LocalDateTime time;
    ChatModel chat;
    Chat_Body body;
    public String text;
    public Chat_Left(ChatModel chat, Chat_Body body) {
        this.chat = chat;
        this.body = body;
        initComponents();
        txt.setBackground(new Color(242, 242, 242));
        txt.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e))
                    showPopupMenu(e.getX(), e.getY());
            }
        });
    }
    public void setBackground(Color color) {
        txt.setBackground(color);
    }
    public void setText(String text) {
        this.text = text;
            txt.setText(text);
    }
    public void setTime(LocalDateTime time) {
        this.time = time;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String currentTime = time.format(formatter);
        txt.setTime(currentTime);
    }
    public void setName(String username){
        if (!username.isEmpty()) {
            txt.setUser(username);
        }
    }
    private void initComponents() {
        txt = new Chat_Item();

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(txt, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(txt, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }
    private void showPopupMenu(int x, int y) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem deleteOption = new JMenuItem("Delete Message");
        deleteOption.addActionListener(e -> {
            chat.deleteMessage(time);
            body.deleteItemLeft(Chat_Left.this);
        });


        popupMenu.add(deleteOption);
        popupMenu.show(txt, x, y);
    }
    private Chat_Item txt;
}
