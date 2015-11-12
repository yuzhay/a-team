package com.jet.edu;

import com.jet.edu.server.ChatServer;
import com.jet.edu.server.ChatStorage;

import java.sql.SQLException;

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
