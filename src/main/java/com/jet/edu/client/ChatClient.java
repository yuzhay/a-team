package com.jet.edu.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Yuriy on 12.11.2015.
 */
public class ChatClient implements Client {


    public void send(String msg) {

    }

    public void readConsole() throws IOException {
        while (true) {

            String message;
            BufferedReader readConsole = new BufferedReader(new InputStreamReader(System.in));
            message = readConsole.readLine();
            if (checkSizeMessage(message)) {
                StateCmd(message);
                send(message);
            } else {
                System.out.println("ERROR: Слишком большое сообщение");
            }
        }
    }

    private boolean checkSizeMessage(String message) {
        return message.length() < 150;
    }

    private void StateCmd(String message) {
        String[] mes = message.split(" ");
        switch (mes[0]) {
            case "/chid":
                
                break;
            case "/hist":
                break;
            case "/snd":
                break;
            default:
        }
    }
}
