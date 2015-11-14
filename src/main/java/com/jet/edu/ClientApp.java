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
    public static final int ioPort = 52349;

    public static void main(String[] args) throws ChatException, IOException {
        System.out.println("[Input] Chat client");
        new Thread(new OutputServer(ioPort)).start();

        Chat chat = new Chat(new Factory(), new Connector(new Socket("127.0.0.1", 12348)));

        chat.readConsole();
    }

    private ClientApp() {
    }
}

/**
 * This is class is used to redefine console output
 */
class OutputServer implements Runnable {
    private final int port;
    private final ChatLogger logger = new ChatLogger("ChatServer.log");

    /**
     * Default constructor
     *
     * @param port select port which will be used
     */
    public OutputServer(int port) {
        this.port = port;
    }

    /**
     * Run server
     */
    @Override
    public void run() {
        try (ServerSocket ss = new ServerSocket(port)) {
            //noinspection InfiniteLoopStatement
            while (true) {
                Socket c = ss.accept();
                PrintWriter out = new PrintWriter(
                        new OutputStreamWriter(c.getOutputStream()));
                PrintStream ps = new PrintStream(new WriterOutputStream(out));
                System.setOut(ps);
                System.setErr(ps);
                System.out.println("[Output] Chat client");
                System.out.flush();
            }
        } catch (IOException e) {
            logger.printSevere("No Connection with sockets", e);
        }
    }
}
