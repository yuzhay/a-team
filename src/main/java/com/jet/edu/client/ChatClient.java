package com.jet.edu.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Yuriy on 12.11.2015.
 */
public class ChatClient implements Client {
    private final ChatClientState state;

    public ChatClient() throws ChatException {
        state = new ChatClientState();
    }

    public void send(String msg) {

    }

    public void readConsole() throws ChatException {
        while (true) {

            String message;
            BufferedReader readConsole = new BufferedReader(new InputStreamReader(System.in));
            try {
                message = readConsole.readLine();
            } catch (IOException e) {
                throw new ChatException("", e);
            }
            if (checkSizeMessage(message)) {
                state.stateCmd(message);
                send(message);
            } else {
                System.out.println("ERROR: Слишком большое сообщение");
            }
        }
    }

    private boolean checkSizeMessage(String message) {
        return message.length() < 150;
    }
}
