package com.jet.edu.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by Павел on 14.11.2015.
 */
public class Listener implements Runnable {
    //region fields
    private Socket socket;
    private String messageUsers = "";
    private InputStream inputStreamReader;
    //endregion

    public Listener(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while ((inputStreamReader = socket.getInputStream()).available() == 0) {
            }

            while (inputStreamReader.available() > 0) {
                messageUsers += inputStreamReader.read();
            }
            System.out.println(messageUsers);
            messageUsers = "";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
