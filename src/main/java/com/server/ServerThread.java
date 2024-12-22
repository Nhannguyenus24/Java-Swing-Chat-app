package com.server;

import java.io.*;
import java.net.Socket;
import org.json.JSONObject;

public class ServerThread extends Thread {

    public final Socket socket;
    public BufferedReader reader;
    public BufferedWriter writer;
    public int userId = 0;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while (true) {
                String message = reader.readLine();
                if (message == null)
                    break;

                handleMessage(message);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected: " + userId);
        } finally {
            ServerMain.serverThreadBus.remove(userId);
            try {
                reader.close();
                writer.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void handleMessage(String message) {
        System.out.println("Received message: " + message);
        try {
            if (message.startsWith("CHECK_STATUS")) {
                String[] parts = message.split(" ");
                int targetUserId = Integer.parseInt(parts[1]);
                boolean isOnline = ServerMain.serverThreadBus.isUserOnline(targetUserId);
                if (isOnline) {
                    sendMessage("ONLINE");
                } else {
                    sendMessage("OFFLINE");
                }
                return;
            }
            JSONObject json = new JSONObject(message);
            String content = json.getString("content");
            int recipientId = json.getInt("recipientId");
            int senderId = json.getInt("senderId");
            String username = json.getString("username");

            if (content.equals("login")) {
                userId = senderId;
                System.out.println("User logged in with userId: " + userId);
            }

            ServerMain.serverThreadBus.sendPrivateMessage(senderId, username, recipientId, content,
                    json.getInt("chat_id"));
            System.out.println("Message received: " + json.toString());
        } catch (Exception e) {
            System.err.println("Failed to handle message: " + message);
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.out.println("Failed to send message to: " + userId);
        }
    }

    public int getUserId() {
        return userId;
    }
}