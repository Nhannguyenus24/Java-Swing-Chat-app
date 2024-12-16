package com.server;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {

    private final Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String userId;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
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
            cleanup();
        }
    }

    private void handleMessage(String message) {
        String[] parts = message.split(",");
        String command = parts[0];

        switch (command) {
            case "login":
                handleLogin(parts[1]);
                break;

            case "send-to-global":
                ServerMain.serverThreadBus.broadcast(userId, parts[1]);
                break;

            case "send-to-person":
                String recipientId = parts[3];
                ServerMain.serverThreadBus.sendPrivateMessage(userId, recipientId, parts[1]);
                break;

            case "logout":
                handleLogout();
                break;

            default:
                System.out.println("Unknown command: " + message);
                break;
        }
    }

    private void handleLogin(String userId) {
        if (ServerMain.serverThreadBus.isUserOnline(userId)) {
            sendMessage("login-failed");
            cleanup();
        } else {
            this.userId = userId;
            sendMessage("login-success," + userId);

            // Broadcast login notification
            ServerMain.serverThreadBus.broadcast("Server", userId + " has joined the chat!");
            ServerMain.serverThreadBus.updateOnlineList();
        }
    }

    private void handleLogout() {
        System.out.println("Client logged out: " + userId);
        if (userId != null) {
            // Broadcast logout notification
            ServerMain.serverThreadBus.broadcast("Server", userId + " has left the chat!");
        }
        cleanup();
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

    private void cleanup() {
        try {
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (userId != null) {
            ServerMain.serverThreadBus.remove(userId);
            ServerMain.serverThreadBus.updateOnlineList();
        }
    }

    public String getUserId() {
        return userId;
    }
}
