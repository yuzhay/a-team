package com.jet.edu;


import com.jet.edu.client.Chat;
import com.jet.edu.client.ChatException;
import com.jet.edu.client.Connector;
import com.jet.edu.client.Factory;

/**
 * Project04
 */
public class ClientApp {
    private ClientApp() {
    }

    public static void main(String[] args) throws ChatException {
        Chat chat = new Chat(new Factory(), new Connector("127.0.0.1", 12348));
        System.out.println("Chat client started");
        chat.readConsole();

    }
}
