package com.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatClient {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private int userId;
    private final List<ClientEventListener> eventListeners = new ArrayList<>();

    // Constructor
    public ChatClient(String host, int port, int userId) throws IOException {
        this.userId = userId;
        this.socket = new Socket(host, port);
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        // Khởi động luồng để lắng nghe tin nhắn từ server
        new Thread(this::listenToServer).start();
        sendMessage("login", userId, "host");
    }

    // Gửi tin nhắn tới server
    public void sendMessage(String messageContent, int recipientId, String username) throws IOException {
        String formattedMessage = String.format("%s,,%d,,%d,,%s", messageContent, recipientId, userId, username);
        writer.write(formattedMessage);
        writer.newLine();
        writer.flush();
    }

    // Lắng nghe tin nhắn từ server
    private void listenToServer() {
        try {
            String message;
            while ((message = reader.readLine()) != null) {
                handleServerMessage(message);
            }
        } catch (IOException e) {
            System.err.println("Error while listening to server: " + e.getMessage());
        }
    }

    // Xử lý tin nhắn từ server
    private void handleServerMessage(String message) {
        String[] parts = message.split(",,");
        String content = parts[0];
        int recipientId = Integer.parseInt(parts[1]);
        int senderId = Integer.parseInt(parts[2]);
        String username = parts[3];
        receiveMessage(message);
    }

    // Đóng kết nối
    public void closeConnection() throws IOException {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    // Thêm listener
    public void addEventListener(ClientEventListener listener) {
        eventListeners.add(listener);
    }

    // Gọi các listener khi nhận tin nhắn
    private void receiveMessage(String message) {
        for (ClientEventListener listener : eventListeners) {
            listener.onMessageReceived(message);
        }
    }

    // Giao diện ClientEventListener
    public interface ClientEventListener {
        void onMessageReceived(String message);
    }
}
