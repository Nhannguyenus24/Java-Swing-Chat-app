package com.user.views;

import com.server.ChatClient;
import com.user.models.ChatModel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.util.List;
import net.miginfocom.swing.MigLayout;

public class Chat_Bottom extends javax.swing.JPanel {
    ChatModel chat;
    private Chat_Body chatBody;
    ChatClient client;

    public Chat_Bottom(ChatModel chat, Chat_Body chatBody, ChatClient client) {
        this.chat = chat;
        this.chatBody = chatBody;
        this.client = client;
        initComponents();
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx, filly", "0[fill]0[]0[]2", "2[fill]2"));
        JScrollPane scroll = new JScrollPane();
        scroll.setBorder(null);
        JIMSendTextPane txt = new JIMSendTextPane();
        txt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent ke) {
                revalidate();
                repaint();
            }
        });
        txt.setBorder(new EmptyBorder(5, 5, 5, 5));
        txt.setHintText("Write Message Here ...");
        scroll.setViewportView(txt);
        ScrollBar sb = new ScrollBar();
        sb.setBackground(new Color(229, 229, 229));
        sb.setPreferredSize(new Dimension(2, 10));
        scroll.setVerticalScrollBar(sb);
        add(sb);
        add(scroll, "w 100%");
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("filly", "0[]0", "0[bottom]0"));
        panel.setPreferredSize(new Dimension(30, 28));
        panel.setBackground(Color.WHITE);
        JButton cmd = new JButton();
        cmd.setBorder(null);
        cmd.setContentAreaFilled(false);
        cmd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmd.setIcon(new ImageIcon(getClass().getResource("/icon/send.png")));

        cmd.addActionListener(ae -> {
            String text = txt.getText().trim();
            if (!text.isEmpty()) {
                chat.sendMessage(text);
                System.out.println("Message sent: " + text);

                chat.getListUserId().forEach(id -> {
                    try {
                        client.sendMessage(text, id, chat.user.userName, chat.chat_id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                txt.setText("");
                txt.grabFocus();
                chatBody.addItemRight(text, LocalDateTime.now());
                revalidate();
            } else {
                txt.grabFocus();
            }
        });

        panel.add(cmd);
        add(panel);
    }

    private void initComponents() {

        setBackground(new java.awt.Color(229, 229, 229));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 40, Short.MAX_VALUE));
    }
}
