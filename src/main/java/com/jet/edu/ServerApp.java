package com.jet.edu;

import com.jet.edu.server.ChatServer;

/**
 * * Project04
 */
public class ServerApp {
    private ServerApp(){}

    public static void main(String[] args) {
        int port = 12348;
        ChatServer server = new ChatServer(port);
        server.start();
        System.out.println("Chat Server started on localhost:" + port +" successful");
    }
}
