package com.jet.edu;

import java.io.*;
import java.net.Socket;

/**
 * ClientReader App
 * Created by Yuriy on 13.11.2015.
 */
public class ClientReaderApp {
    public static void main(String[] argv) throws FileNotFoundException {
        int port = ClientApp.ioPort;
        if (argv.length == 1) {
            port = Integer.valueOf(argv[0]);
        }
        try (
                Socket client = new Socket("localhost", port);
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
            System.out.println("Can't connect to client app on port" + port);
            System.exit(1);
        }
    }

    private ClientReaderApp() {
    }
}