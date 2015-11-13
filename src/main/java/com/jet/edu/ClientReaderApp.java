package com.jet.edu;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Yuriy on 13.11.2015.
 */
public class ClientReaderApp {
    private static int port = 52341;


    public static void main(String[] argv) throws FileNotFoundException {
        /*new Thread(new InputClient()).start();*/
    }

    private static class InputClient implements Runnable {
        @Override
        public void run() {
            Socket client = null;
            try {
                client = new Socket("localhost", port);
                System.setIn(new BufferedInputStream(client.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private ClientReaderApp() {
    }
}
