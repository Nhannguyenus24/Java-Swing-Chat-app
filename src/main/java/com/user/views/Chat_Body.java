package com.user.views;

import com.user.models.ChatModel;
import java.awt.Adjustable;
import java.awt.Color;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.awt.Rectangle;


import net.miginfocom.swing.MigLayout;

public class Chat_Body extends javax.swing.JPanel {
    ChatModel chat;

    public Chat_Body(ChatModel model) {
        this.chat = model;
        initComponents();
        init();
        renderChat();
    }

    public void renderChat(){
        LocalDate previousDate = null;
        Boolean is_group = chat.is_group;
        for (int i = 0; i < chat.content.size(); i++){
            String message = chat.content.get(i);
            boolean is_sender = chat.is_sender.get(i);
            LocalDateTime time = chat.timestamp.get(i);
            LocalDate messageDate = time.toLocalDate();
            if (!messageDate.equals(previousDate)){
                addDate(messageDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                previousDate = messageDate;
            }
            if (is_sender){
                    addItemRight(message, time);
            }
            else {
                if (is_group){
                    String sender_name = chat.sender_name.get(i);
                    addItemLeft(message, sender_name, time);
                }
                else
                    addItemLeft(message, "", time);
            }
        }
    }

    private void init() {
        body.setLayout(new MigLayout("fillx", "", "5[]5"));
        sp.setVerticalScrollBar(new ScrollBar());
        sp.getVerticalScrollBar().setBackground(Color.WHITE);
    }

    public void addItemLeft(String text, String user, LocalDateTime time) {
        Chat_Left item = new Chat_Left(chat, this);
        item.setText(text);
        item.setTime(time);
        item.setName(user);
        body.add(item, "wrap, w 100::80%");
        body.repaint();
        body.revalidate();
    }

    public void addItemRight(String text, LocalDateTime time) {
        Chat_Right item = new Chat_Right(chat, this);
        item.setText(text);
        body.add(item, "wrap, al right, w 100::80%");
        body.repaint();
        body.revalidate();
        item.setTime(time);
        scrollToBottom();
    }

    public void addDate(String date) {
        Chat_Date item = new Chat_Date();
        item.setDate(date);
        body.add(item, "wrap, al center");
        body.repaint();
        body.revalidate();
    }
    public void deleteItemRight(Chat_Right item){
        body.remove(item);
        removeConsecutiveDates();
        body.repaint();
        body.revalidate();
    }
    public void deleteItemLeft(Chat_Left item){
        body.remove(item);
        removeConsecutiveDates();
        body.repaint();
        body.revalidate();
    }
    private void removeConsecutiveDates() {
        for (int i = 0; i < body.getComponentCount() - 1; i++) {
            if (body.getComponent(i) instanceof Chat_Date && body.getComponent(i + 1) instanceof Chat_Date) {
                body.remove(i);
            }
        }
    }
    private void initComponents() {

        sp = new javax.swing.JScrollPane();
        body = new javax.swing.JPanel();

        sp.setBorder(null);
        sp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        body.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 826, Short.MAX_VALUE)
        );
        bodyLayout.setVerticalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 555, Short.MAX_VALUE)
        );

        sp.setViewportView(body);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sp)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sp)
        );
    }

    private void scrollToBottom() {
        JScrollBar verticalBar = sp.getVerticalScrollBar();
        AdjustmentListener downScroller = new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                Adjustable adjustable = e.getAdjustable();
                adjustable.setValue(adjustable.getMaximum());
                verticalBar.removeAdjustmentListener(this);
            }
        };
        verticalBar.addAdjustmentListener(downScroller);
    }

    public void moveToMessage(String searchText) {
        boolean found = false;
        for (int i = 0; i < body.getComponentCount(); i++) {
            java.awt.Component comp = body.getComponent(i);

            if (comp instanceof Chat_Left) {
                Chat_Left item = (Chat_Left) comp;
                if (item.text.contains(searchText)) {
                    item.setBackground(new Color(255, 255, 150));

                    if (!found) {
                        scrollToComponent(comp);
                        found = true;
                    }
                }
            } else if (comp instanceof Chat_Right) {
                Chat_Right item = (Chat_Right) comp;
                if (item.text.contains(searchText)) {
                    item.setBackground(new Color(255, 255, 150));

                    if (!found) {
                        scrollToComponent(comp);
                        found = true;
                    }
                }
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "Message not found: " + searchText, "Search", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void scrollToComponent(java.awt.Component comp) {
        Rectangle bounds = comp.getBounds();
        body.scrollRectToVisible(bounds);
    }

    private javax.swing.JPanel body;
    private javax.swing.JScrollPane sp;
}