package com.jet.edu.server;

import com.jet.edu.ChatLogger;
import com.jet.edu.server.ChatServer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * * Project04
 */
public class ServerApp {
    private ServerApp() {
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        int port = 12348;
        ChatServer server = null;
        ChatLogger logger = new ChatLogger();
        try {
            server = new ChatServer(port);
            server.start();
            System.out.println("Chat Server started on localhost:" + port + " successful");
        } catch (IOException e) {
            if(server != null){
                server.stop();
            }
            String errMsg = "Server can't bind localhost:" + port;
            logger.printSevere(errMsg, e);
            logger.printConsole(errMsg);
            System.exit(1);
        }

    }
}