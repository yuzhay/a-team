package com.jet.edu;

import com.jet.edu.client.Chat;
import com.jet.edu.client.ChatException;

/**
 * Project04
 */
public class ClientApp {
    public static void main(String[] args) throws ChatException {
        Chat chat = new Chat("localhost", 12345);
        chat.readConsole();
    }
}
