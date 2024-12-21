package com.user.views;

import com.user.models.UserModel;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;

public class Home extends JLayeredPane {

    Chat chat;
    UserModel user;
    Boolean init = false;
    public Home(UserModel user) {
        this.user = user;
        initComponents();
        init();
    }
    public void updateChat(Integer chat_id){
        init = true;
        chat = new Chat(user, chat_id);
        this.removeAll();
        this.add(new Menu_Left(user, this));
        this.add(chat);
        this.add(new Menu_Right(user, this));
        revalidate();
        repaint();
    }
    public void updateChat(UserModel friend){
        init = true;
        chat = new Chat(user, friend);
        this.removeAll();
        this.add(new Menu_Left(user, this));
        this.add(chat);
        this.add(new Menu_Right(user, this));
        revalidate();
        repaint();
    }
    private void init() {
        setLayout(new MigLayout("fillx, filly", "0[210!]5[fill, 100%]5[200!]0", "0[fill]0"));
        this.add(new Menu_Left(user, this));
        chat = new Chat();
        this.add(chat);
        this.add(new Menu_Right(user, this));
        chat.setVisible(true);
    }
    private void initComponents() {

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 1010, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 551, Short.MAX_VALUE)
        );
    }
}
