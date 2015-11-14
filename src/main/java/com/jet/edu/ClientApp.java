package com.jet.edu;


import com.jet.edu.client.Chat;
import com.jet.edu.client.ChatException;
import com.jet.edu.client.Connector;
import com.jet.edu.client.Factory;
import org.apache.commons.io.output.WriterOutputStream;

import java.io.IOException;
import java.net.Socket;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Project04
 */
public class ClientApp {
    private static int port = 52341;

    public static void main(String[] args) throws ChatException {
        /*OutputServer outServer = new OutputServer();
        new Thread(outServer).start();*/

        Chat chat = new Chat(new Factory(), new Connector("127.0.0.1", 12348));
        System.out.println("Chat client started");
        chat.readConsole();
    }

    private ClientApp() {
    }
    
    private static class OutputServer implements Runnable {
        @Override
        public void run() {
            ServerSocket ss = null;
            try {
                ss = new ServerSocket(port);
                Socket c = ss.accept();
                PrintWriter out = new PrintWriter(
                        new OutputStreamWriter(c.getOutputStream()));
                PrintStream ps = new PrintStream(new WriterOutputStream(out));
                System.setOut(ps);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
