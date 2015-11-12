package com.jet.edu;

import com.jet.edu.server.ChatServer;

/**
 * * Project04
 */
public class ServerApp {
    public static void main(String[] args) {
        System.out.println("Server started!");


        ChatServer server = new ChatServer(12345);
        server.start();
    }
}
