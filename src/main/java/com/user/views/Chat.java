package com.user.views;

import net.miginfocom.swing.MigLayout;

import com.server.ChatClient;
import com.server.Client;
import com.user.models.*;

public class Chat extends javax.swing.JPanel {

    public Chat_Body chatBody;
    public ChatModel chat;
    ChatClient client;

    // public Chat(UserModel user, ChatClient client) {
    //     this.client = client;
    //     chat = new ChatModel(user);
    //     initComponents();
    //     init();
    // }

    public Chat(UserModel user, UserModel friend, ChatClient client) {
        this.client = client;
        chat = new ChatModel(user, friend.userID, friend.userName);
        initComponents();
        init();
    }

    public Chat(UserModel user, Integer group_id, ChatClient client) {
        chat = new ChatModel(user, group_id);
        this.client = client;
        initComponents();
        init();

    }

    public Chat(ChatClient client) {
        chat = new ChatModel();
        this.client = client;
        initComponents();
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx", "0[fill]0", "0[]0[100%, bottom]0[shrink 0]0"));
        chatBody = new Chat_Body(chat, client);
        add(new Chat_Title(chat.chat_name), "wrap");
        add(chatBody, "wrap");
        add(new Chat_Bottom(chat, chatBody, client), "h ::50%");
    }

    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 727, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 681, Short.MAX_VALUE));
    }
}
