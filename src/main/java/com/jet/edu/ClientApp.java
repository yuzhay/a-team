package com.jet.edu;


import com.jet.edu.client.Chat;
import com.jet.edu.client.ChatException;

import java.io.IOException;

/**
 * Project04
 */
public class ClientApp {
    public static void main(String[] args) throws ChatException, IOException {
        Chat chat = new Chat("localhost", 12348);
        chat.readConsole();
    }
}
