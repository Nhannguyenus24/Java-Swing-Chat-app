package com.user.views;

import com.user.models.ChatModel;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Chat_Right extends JLayeredPane {
    LocalDateTime time;
    ChatModel chat;
    Chat_Body body;
    public String text;
    private Chat_Item txt;
    public Chat_Right(ChatModel chat, Chat_Body body) {
        this.chat = chat;
        this.body = body;
        initComponents();
        txt.setBackground(new Color(179, 233, 255));
        txt.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    showPopupMenu(e.getX(), e.getY());
                }
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

    private void initComponents() {

        txt = new Chat_Item();

        setLayer(txt, JLayeredPane.DEFAULT_LAYER);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(txt, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(txt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
    }
    private void showPopupMenu(int x, int y) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem deleteOption = new JMenuItem("Delete Message");
        deleteOption.addActionListener(e -> {
                chat.deleteMessage(time);
                body.deleteItemRight(Chat_Right.this);
        });

        popupMenu.add(deleteOption);
        popupMenu.show(txt, x, y);
    }
}
