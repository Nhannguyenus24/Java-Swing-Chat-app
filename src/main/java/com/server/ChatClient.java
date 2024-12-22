package com.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class ChatClient {
    public Socket socket;
    public BufferedReader reader;
    public BufferedWriter writer;
    public int userId;

    public final List<ClientEventListener> eventListeners = new ArrayList<>();

    // Constructor
    public ChatClient(String host, int port, int userId, int chat_id) throws IOException {
        this.userId = userId;
        this.socket = new Socket(host, port);
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        // Khởi động luồng để lắng nghe tin nhắn từ server
        new Thread(this::listenToServer).start();
    }

    // Gửi tin nhắn tới server
    public void sendMessage(String messageContent, int recipientId, String username) throws IOException {
        // Tạo JSON object chứa nội dung tin nhắn
        JSONObject json = new JSONObject();
        json.put("content", messageContent);
        json.put("recipientId", recipientId);
        json.put("senderId", userId);
        json.put("username", username);

        // Gửi tin nhắn dưới dạng JSON string
        writer.write(json.toString());
        writer.newLine();
        writer.flush();
    }

    // Lắng nghe tin nhắn từ server
    public void listenToServer() {
        try {
            String message;
            while ((message = reader.readLine()) != null) {
                handleServerMessage(message);
            }
        } catch (IOException e) {
            System.err.println("Error while listening to server: " + e.getMessage());
        }
    }

    public void handleServerMessage(String message) {
        try {
            // Chuyển đổi chuỗi tin nhắn JSON thành JSONObject
            JSONObject json = new JSONObject(message);
            String content = json.getString("content");
            int senderId = json.getInt("senderId");
            int recipientId = json.getInt("recipientId");
            String username = json.getString("username");

            // Hiển thị tin nhắn hoặc thực hiện hành động tương ứng
            System.out.println("Message from " + username + " (ID: " + senderId + "): " + " to (ID: " + recipientId
                    + "): " + content);

            // Gọi event listener
            receiveMessage(message);
        } catch (Exception e) {
            System.err.println("Failed to handle server message: " + message);
            e.printStackTrace();
        }
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
    public void receiveMessage(String message) {
        for (ClientEventListener listener : eventListeners) {
            listener.onMessageReceived(message);
        }
    }

    // Giao diện ClientEventListener
    public interface ClientEventListener {
        void onMessageReceived(String message);
    }
}
