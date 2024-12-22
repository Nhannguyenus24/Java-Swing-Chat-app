package com.user.views;

import com.server.ChatClient;
import com.user.models.UserModel;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;

public class Home extends JLayeredPane {

    Chat chat;
    UserModel user;
    Boolean init = false;
    ChatClient client;

    public Home(UserModel user, ChatClient client) {
        this.user = user;
        this.client = client;
        initComponents();
        init();
    }

    public void updateChat(Integer chat_id) {
        init = true;
        chat = new Chat(user, chat_id, client);
        this.removeAll();
        this.add(new Menu_Left(user, this, client));
        this.add(chat);
        this.add(new Menu_Right(user, this, client));
        revalidate();
        repaint();
    }

    public void updateChat(UserModel friend) {
        init = true;
        chat = new Chat(user, friend, client);
        this.removeAll();
        this.add(new Menu_Left(user, this, client));
        this.add(chat);
        this.add(new Menu_Right(user, this, client));
        revalidate();
        repaint();
    }

    private void init() {
        setLayout(new MigLayout("fillx, filly", "0[210!]5[fill, 100%]5[200!]0", "0[fill]0"));
        this.add(new Menu_Left(user, this, client));
        chat = new Chat(client);
        this.add(chat);
        this.add(new Menu_Right(user, this, client));
        chat.setVisible(true);
    }

    private void initComponents() {

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 1010, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 551, Short.MAX_VALUE));
    }
}
