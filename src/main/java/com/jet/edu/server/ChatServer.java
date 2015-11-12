package com.jet.edu.server;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Yuriy on 12.11.2015.
 */
public class ChatServer implements Server {
    //region private fields
    private final Charset charset = Charset.forName("utf-8");
    private ServerSocket socket;
    private Thread serverThread;
    private Accepter accepter = new Accepter();
    //endregion

    //region Accepter
    private class Accepter implements Runnable {
        //region private fields
        private List<IOException> exceptionsList = new ArrayList<>();
        private ExecutorService pool = Executors.newFixedThreadPool(1000);
        //endregion

        //region public methods
        @Override
        public void run() {
            while (!Thread.interrupted()) {
                try {
                    Socket client = socket.accept();
                    pool.execute(new Worker(client));
                } catch (SocketTimeoutException ste) {
                    /*Do nothing. Time is out. Wait for next client*/
                } catch (IOException e) {
                    exceptionsList.add(e);
                }
            }
            pool.shutdownNow();
        }

        private class Worker implements Runnable {
            private Socket client;

            public Worker(Socket client) {
                this.client = client;
            }

            @Override
            public void run() {
                try (
                        BufferedReader br = new BufferedReader(
                                new InputStreamReader(client.getInputStream(), charset));
                        OutputStreamWriter osw = new OutputStreamWriter(client.getOutputStream(), charset)
                ) {
                    String line =  br.readLine();

                    ChatServerState state = new ChatServerState();
                    osw.write(state.switchState(line));
                    osw.flush();
                } catch (IOException e) {
                    exceptionsList.add(e);
                    e.printStackTrace();
                }
            }
        }

        //endregion
    }
    //endregion

    public ChatServer(int port) {
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            /*TODO: handle*/
        }
    }

    public void start() {
        try {
            serverThread = new Thread(accepter);
            serverThread.start();
        } catch (RuntimeException ex) {
            /*TODO: handle*/
        }
    }

    public void stop() {
        serverThread.interrupt();
    }
}
