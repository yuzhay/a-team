package com.jet.edu.client;

import java.io.BufferedReader;
import com.jet.edu.ChatLogger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Павел on 14.11.2015.
 */
public class Listener implements Runnable {
    //region fields
    private Socket socket;
    private String messageUsers = "";
    private InputStream inputStreamReader;
    private final ChatLogger logger = new ChatLogger();

    //endregion

    public Listener(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while ((inputStreamReader = socket.getInputStream()).available() == 0) {
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String messageUsers = br.readLine();
            System.out.println(messageUsers);
            System.out.flush();
            messageUsers = "";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
