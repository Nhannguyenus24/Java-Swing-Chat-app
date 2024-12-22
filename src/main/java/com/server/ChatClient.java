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

    public ChatClient(String host, int port, int userId) throws IOException {
        this.userId = userId;
        this.socket = new Socket(host, port);
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        new Thread(this::listenToServer).start();
        sendMessage("login", userId, "host", -4);
        System.out.println(sendMessage("CHECK_STATUS " + userId));
    }

    public boolean sendMessage(String message) throws IOException {
        writer.write(message);
        writer.newLine();
        writer.flush();
        return handleServerMessage(reader.readLine());
    }

    public void sendMessage(String messageContent, int recipientId, String username, int chat_id) throws IOException {
        JSONObject json = new JSONObject();
        json.put("content", messageContent);
        json.put("recipientId", recipientId);
        json.put("senderId", userId);
        json.put("username", username);
        json.put("chat_id", chat_id);

        writer.write(json.toString());
        writer.newLine();
        writer.flush();
    }

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

    public boolean handleServerMessage(String message) {
        System.out.println("Received message: " + message);
        try {
            if (!message.startsWith("{")) {
                System.out.println(message);
                return "ONLINE".equals(message);
            }
            JSONObject json = new JSONObject(message);
            String content = json.getString("content");
            int senderId = json.getInt("senderId");
            int recipientId = json.getInt("recipientId");
            String username = json.getString("username");

            System.out.println("Message from " + username + " (ID: " + senderId + "): " + " to (ID: " + recipientId
                    + "): " + content);

            receiveMessage(message);
        } catch (Exception e) {
            System.err.println("Failed to handle server message: " + message);
            e.printStackTrace();
        }
        return false;
    }

    public void closeConnection() throws IOException {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    public void addEventListener(ClientEventListener listener) {
        eventListeners.add(listener);
    }

    public void receiveMessage(String message) {
        for (ClientEventListener listener : eventListeners) {
            listener.onMessageReceived(message);
        }
    }

    public interface ClientEventListener {
        void onMessageReceived(String message);
    }
}
