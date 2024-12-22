package com.server;

import java.util.ArrayList;
import java.util.List;

public class ServerThreadBus {

    private final List<ServerThread> serverThreads;

    public ServerThreadBus() {
        serverThreads = new ArrayList<>();
    }

    public void add(ServerThread serverThread) {
        serverThreads.add(serverThread);
    }

    public void remove(int userId) {
        serverThreads.removeIf(thread -> thread.getUserId() != 0 && thread.getUserId() == userId);
    }

    public void sendPrivateMessage(int senderId, String username, int recipientId, String messageContent) {
        for (ServerThread thread : serverThreads) {
            System.out.println("Thread ID: " + thread.getUserId());
            System.out.println("Sender ID: " + senderId);
            System.out.println("Recipient ID: " + recipientId);
            if (thread.getUserId() == recipientId) {
                String formattedMessage = String.format("%s,,%d,,%d,,%s", messageContent, recipientId, senderId,
                        username);
                thread.sendMessage(formattedMessage);
                break;
            }
        }
    }

    public boolean isUserOnline(int userId) {
        return serverThreads.stream().anyMatch(thread -> userId == thread.getUserId());
    }
}
