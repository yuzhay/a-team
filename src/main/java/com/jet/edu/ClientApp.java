package com.jet.edu;


import com.jet.edu.client.Chat;
import com.jet.edu.client.ChatException;
import com.jet.edu.client.Connector;
import com.jet.edu.client.Factory;

import java.io.IOException;
import java.net.Socket;

/**
 * Project04
 */
public class ClientApp {
    private ClientApp() {
    }

    public static void main(String[] args) throws ChatException,IOException {
        Chat chat = new Chat(new Factory(), new Connector(new Socket("127.0.0.1", 12348)));
        System.out.println("Chat client started");
        chat.readConsole();

    }
}