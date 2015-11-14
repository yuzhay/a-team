package com.jet.edu;

import com.jet.edu.server.ChatServer;
import java.io.UnsupportedEncodingException;

/**
 * * Project04
 */
public class ServerApp {
    private ServerApp(){}

    public static void main(String[] args) throws UnsupportedEncodingException {
        int port = 12348;

        System.setOut(new java.io.PrintStream(System.out, true, "Cp866"));
        ChatServer server = new ChatServer(port);
        server.start();
        System.out.println("Chat Server started on localhost:" + port +" successful");
    }
}