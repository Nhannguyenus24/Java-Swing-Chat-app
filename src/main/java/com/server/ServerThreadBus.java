package com.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class ServerThreadBus {

    private final List<ServerThread> serverThreads = Collections.synchronizedList(new ArrayList<>());

    public synchronized void add(ServerThread serverThread) {
        remove(serverThread.getUserId());
        serverThreads.add(serverThread);
    }

    public synchronized void remove(int userId) {
        serverThreads.removeIf(thread -> thread.getUserId() != 0 && thread.getUserId() == userId);
    }

    public void sendPrivateMessage(int senderId, String username, int recipientId, String messageContent, int chat_id) {
        for (ServerThread thread : serverThreads) {
            if (thread.userId == recipientId) {
                JSONObject json = new JSONObject();
                json.put("content", messageContent);
                json.put("recipientId", recipientId);
                json.put("senderId", senderId);
                json.put("username", username);
                json.put("chat_id", chat_id);
                thread.sendMessage(json.toString());
                return;
            }
        }
        System.out.println("Recipient not found: " + recipientId);
    }

    public boolean isUserOnline(int userId) {
        return serverThreads.stream().anyMatch(thread -> userId == thread.getUserId());
    }
}
