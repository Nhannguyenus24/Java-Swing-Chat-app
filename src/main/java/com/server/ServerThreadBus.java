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

    public void remove(String userId) {
        serverThreads.removeIf(thread -> thread.getUserId() != null && thread.getUserId().equals(userId));
    }

    public void broadcast(String senderId, String message) {
        for (ServerThread thread : serverThreads) {
            if (!thread.getUserId().equals(senderId)) {
                thread.sendMessage("global-message," + senderId + ": " + message);
            }
        }
    }

    public void sendPrivateMessage(String senderId, String recipientId, String message) {
        for (ServerThread thread : serverThreads) {
            if (thread.getUserId().equals(recipientId)) {
                thread.sendMessage("global-message," + senderId + " (Private): " + message);
                break;
            }
        }
    }

    public boolean isUserOnline(String userId) {
        return serverThreads.stream().anyMatch(thread -> userId.equals(thread.getUserId()));
    }

    public void updateOnlineList() {
        StringBuilder onlineList = new StringBuilder();
        for (ServerThread thread : serverThreads) {
            if (thread.getUserId() != null) {
                onlineList.append(thread.getUserId()).append("-");
            }
        }

        String onlineMessage = "update-online-list," + onlineList.toString();
        for (ServerThread thread : serverThreads) {
            thread.sendMessage(onlineMessage);
        }
    }
}
