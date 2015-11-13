package com.jet.edu;

import com.jet.edu.server.ChatServer;

/**
 * * Project04
 */
public class ServerApp {
    public static void main(String[] args) {
        ChatServer server = new ChatServer(12348);
        server.start();
        System.out.println("Server started!");
    }
}
