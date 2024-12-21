package com;

import com.user.views.ChatUI;
import com.user.views.LoginPanel;
import javax.swing.*;
public class Main {
    public static void main(String[] args) {
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            }  catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(ChatUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            java.awt.EventQueue.invokeLater(() -> new LoginPanel().setVisible(true));
    }
}