package com.user.views;

import net.miginfocom.swing.MigLayout;
import com.user.models.*;

public class Chat extends javax.swing.JPanel {

    public Chat_Body chatBody;
    public ChatModel chat;

    public Chat(UserModel user, UserModel friend) {
        chat = new ChatModel(user, friend.userID, friend.userName);
        initComponents();
        init();
    }
    public Chat(UserModel user, Integer group_id) {
        chat = new ChatModel(user, group_id);
        initComponents();
        init();

    }
    public Chat() {
        chat = new ChatModel();
        initComponents();
        init();
    }
    private void init() {
        setLayout(new MigLayout("fillx", "0[fill]0", "0[]0[100%, bottom]0[shrink 0]0"));
        chatBody = new Chat_Body(chat);
        add(new Chat_Title(chat.chat_name), "wrap");
        add(chatBody, "wrap");
        add(new Chat_Bottom(chat, chatBody), "h ::50%");
    }

    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 727, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 681, Short.MAX_VALUE)
        );
    }
}
