package com.client;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client extends JFrame {

    // Networking components
    private Socket clientSocket;
    private BufferedWriter writer;
    private BufferedReader reader;

    // Client information
    private int clientId = -1;
    private String userId;
    private List<String> onlineClients;

    // GUI components
    private JTextArea onlineListArea;
    private JTextArea chatArea;
    private JTextField messageField;
    private JComboBox<String> recipientComboBox;
    private JLabel recipientLabel;

    public Client() {
        if (!performLogin()) {
            JOptionPane.showMessageDialog(null, "Login failed. Exiting application.");
            System.exit(0);
        }
        initializeGUI();
        onlineClients = new ArrayList<>();
        setUpSocketConnection();

        // Add a window listener to handle logout on close
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                sendLogoutMessage();
                System.exit(0);
            }
        });
    }

    private boolean performLogin() {
        userId = JOptionPane.showInputDialog(null,
                "Enter your User ID:",
                "Login",
                JOptionPane.PLAIN_MESSAGE);

        return userId != null && !userId.trim().isEmpty();
    }

    private void initializeGUI() {
        // Configure JFrame
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("Client Application");
        this.setVisible(true);

        // Tabbed Pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab 1: Online List
        onlineListArea = new JTextArea(20, 30);
        onlineListArea.setEditable(false);
        JScrollPane onlineScrollPane = new JScrollPane(onlineListArea);
        tabbedPane.addTab("Online Clients", onlineScrollPane);

        // Tab 2: Chat Area
        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));

        chatArea = new JTextArea(20, 30);
        chatArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);

        recipientLabel = new JLabel("Recipient: Global");

        recipientComboBox = new JComboBox<>();
        recipientComboBox.addItem("Send to All");
        recipientComboBox.addActionListener(e -> updateRecipientLabel());

        messageField = new JTextField();
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(e -> sendMessage());

        chatPanel.add(recipientLabel);
        chatPanel.add(chatScrollPane);
        chatPanel.add(new JLabel("Select Recipient"));
        chatPanel.add(recipientComboBox);
        chatPanel.add(new JLabel("Enter Message"));
        chatPanel.add(messageField);
        chatPanel.add(sendButton);

        tabbedPane.addTab("Chat", chatPanel);

        // Add tabbed pane to frame
        this.add(tabbedPane);
        this.pack();
    }

    private void setUpSocketConnection() {
        try {
            clientSocket = new Socket("localhost", 7777);
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Send login message to the server
            writer.write("login," + userId);
            writer.newLine();
            writer.flush();

            // Start a thread to listen for server messages
            new Thread(this::listenToServer).start();
        } catch (IOException e) {
            showError("Failed to connect to server.");
        }
    }

    private void listenToServer() {
        try {
            String message;
            while ((message = reader.readLine()) != null) {
                handleServerMessage(message);
            }
        } catch (IOException e) {
            showError("Connection to server lost.");
        }
    }

    private void handleServerMessage(String message) {
        String[] parts = message.split(",");

        switch (parts[0]) {
            case "login-success":
                clientId = Integer.parseInt(parts[1]);
                this.setTitle("Client " + clientId + " (User: " + userId + ")");
                break;

            case "login-failed":
                showError("Login failed. User ID already in use.");
                System.exit(0);
                break;

            case "update-online-list":
                updateOnlineList(parts[1]);
                break;

            case "global-message":
                appendToChat(parts[1]);
                break;

            default:
                appendToChat("Unknown message: " + message);
                break;
        }
    }

    private void updateOnlineList(String onlineData) {
        onlineClients.clear();
        onlineClients.addAll(List.of(onlineData.split("-")));

        onlineListArea.setText("Online Clients:\n");
        onlineClients.forEach(client -> onlineListArea.append("Client " + client + "\n"));

        updateRecipientComboBox();
    }

    private void updateRecipientComboBox() {
        recipientComboBox.removeAllItems();
        recipientComboBox.addItem("Send to All");
        for (String client : onlineClients) {
            if (!client.equals(String.valueOf(clientId))) {
                recipientComboBox.addItem("Client " + client);
            }
        }
    }

    private void updateRecipientLabel() {
        if (recipientComboBox.getSelectedIndex() == 0) {
            recipientLabel.setText("Recipient: Global");
        } else {
            recipientLabel.setText("Recipient: " + recipientComboBox.getSelectedItem());
        }
    }

    private void sendMessage() {
        String messageContent = messageField.getText().trim();
        if (messageContent.isEmpty()) {
            showError("Message cannot be empty.");
            return;
        }

        try {
            if (recipientComboBox.getSelectedIndex() == 0) {
                writer.write("send-to-global," + messageContent + "," + clientId);
            } else {
                String recipient = recipientComboBox.getSelectedItem().toString().split(" ")[1];
                writer.write("send-to-person," + messageContent + "," + clientId + "," + recipient);
            }
            writer.newLine();
            writer.flush();

            appendToChat("You: " + messageContent);
            messageField.setText("");
        } catch (IOException e) {
            showError("Failed to send message.");
        }
    }

    private void appendToChat(String message) {
        chatArea.append(message + "\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    private void showError(String error) {
        JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void sendLogoutMessage() {
        try {
            if (writer != null) {
                writer.write("logout," + userId);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            System.out.println("Failed to send logout message.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Client::new);
    }

}
