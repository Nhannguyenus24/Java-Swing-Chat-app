package com.admin.controllers;

import com.admin.models.GroupModel;
import com.admin.views.dialogs.GroupAdminDialog;
import com.admin.views.dialogs.GroupMemberDialog;
import com.admin.views.panels.ChatGroupPanel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.List;

public class ChatGroupController {
    public ChatGroupPanel chatGroupPanel;
    public List<Object[]> groups;
    public DefaultTableModel groupModel;

    public ChatGroupController(ChatGroupPanel panel) {
        this.chatGroupPanel = panel;
        this.loadGroupData();
        this.addEventListeners();
    }

    private void loadGroupData() {
        groups = GroupModel.getAllGroups();
        groupModel = this.chatGroupPanel.groupTableModel;

        groupModel.setRowCount(0);
        for (Object[] group : groups) {
            groupModel.addRow(group);
        }
    }

    private void addEventListeners() {
        this.chatGroupPanel.searchButton.addActionListener(this::searchGroupListAction);
        this.chatGroupPanel.viewMembersButton.addActionListener(this::viewGroupMembersAction);
        this.chatGroupPanel.viewAdminsButton.addActionListener(this::viewGroupAdminsAction);
    }

    private void searchGroupListAction(ActionEvent e) {
        String keyword = this.chatGroupPanel.searchField.getText();
        List<Object[]> searchResults = GroupModel.searchGroups(keyword);

        groupModel.setRowCount(0);
        for (Object[] row : searchResults)
            groupModel.addRow(row);
    }

    private void viewGroupMembersAction(ActionEvent e) {
        int viewRow = this.chatGroupPanel.groupTable.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a group");
            return;
        }
        int modelRow = this.chatGroupPanel.groupTable.convertRowIndexToModel(viewRow);
        String groupName = (String) this.chatGroupPanel.groupTableModel.getValueAt(modelRow, 0);
        List<Object[]> members = GroupModel.getMembersByGroupname(groupName);
        if (members.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are no people in this group");
            return;
        }
        new GroupMemberDialog(members);
    }

    private void viewGroupAdminsAction(ActionEvent e) {
        int viewRow = this.chatGroupPanel.groupTable.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a group");
            return;
        }
        int modelRow = this.chatGroupPanel.groupTable.convertRowIndexToModel(viewRow);
        String groupName = (String) this.chatGroupPanel.groupTableModel.getValueAt(modelRow, 0);
        List<Object[]> admins = GroupModel.getAdminsByGroupname(groupName);
        if (admins.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are no admins in this group");
            return;
        }
        new GroupAdminDialog(admins);
    }
}
