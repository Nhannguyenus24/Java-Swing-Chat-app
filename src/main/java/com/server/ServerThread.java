package com.server;

import java.io.*;
import java.net.Socket;

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
        }
    }

    public void handleMessage(String message) {
        String[] parts = message.split(",,");
        String content = parts[0];
        if(content.equals("login")) {
            userId = Integer.parseInt(parts[1]);
        }
        int recipientId = Integer.parseInt(parts[1]);
        int senderId = Integer.parseInt(parts[2]);
        String username = parts[3];
        ServerMain.serverThreadBus.sendPrivateMessage(senderId, username, recipientId, content);
        System.out.println("Message received: " + message);
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
