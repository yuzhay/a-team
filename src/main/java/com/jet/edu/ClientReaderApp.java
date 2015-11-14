package com.jet.edu;

import java.io.*;
import java.net.Socket;

/**
 * ClientReader App
 * Created by Yuriy on 13.11.2015.
 */
public class ClientReaderApp {
    private static final ChatLogger logger = new ChatLogger("ChatServer.log");

    public static void main(String[] argv) throws FileNotFoundException {
        try (
                Socket client = new Socket("localhost", ClientApp.ioPort);
                InputStreamReader isr = new InputStreamReader(client.getInputStream());
                BufferedReader br = new BufferedReader(isr)
        ) {
            //noinspection InfiniteLoopStatement
            while (true) {
                String line = br.readLine();
                System.out.println(line);
                System.out.flush();
            }
        } catch (IOException e) {
            int p = 87;
            logger.printSevere("No Connection with sockets", e);
        }
    }

    private ClientReaderApp() {
    }
}