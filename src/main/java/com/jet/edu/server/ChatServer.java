package com.jet.edu.server;

import com.jet.edu.ChatLogger;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Hashtable;
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
        private Hashtable<Socket, ClientIO> clientStream = new Hashtable<>();
        //endregion

        //region public methods
        @Override
        public void run() {
            new Thread(new OneThreadWorker()).start();

            while (!Thread.interrupted()) {
                try {
                    Socket client = socket.accept();
                    clientStream.put(client,
                            new ClientIO(
                                    new BufferedReader(
                                            new InputStreamReader(client.getInputStream(), charset)
                                    ),
                                    new OutputStreamWriter(client.getOutputStream(), charset)));

                    System.out.println("New client connected");
                } catch (SocketTimeoutException ste) {
                    /*Do nothing. Time is out. Wait for next client*/
                } catch (IOException e) {
                    exceptionsList.add(e);
                }
            }
        }

        private class OneThreadWorker implements Runnable {
            @Override
            public void run() {
                ChatServerState css = new ChatServerState(clientStream);
                while (true) {
                    synchronized (clientStream) {
                        for (Socket s : clientStream.keySet()) {
                            try {
                                BufferedReader br = clientStream.get(s).getInputStream();
                                OutputStreamWriter osw = clientStream.get(s).getOutputStream();
                                if (!br.ready()) {
                                    continue;
                                }

                                String line = br.readLine();
                                System.out.println(s.toString() + ": " + line);

                                css.switchState(line, clientStream.get(s));
                            } catch (IOException e) {
                                e.printStackTrace();
                                clientStream.remove(s);
                            }
                        }
                    }
                }
            }

            private void sendToClients(Socket client, String message) {
                for (Socket s : clientStream.keySet()) {
                    if (s == client) {
                        continue;
                    }
                    try {
                        OutputStreamWriter osw = clientStream.get(s).getOutputStream();
                        osw.write(message);
                        osw.flush();
                    } catch (IOException e) {

                    }
                }
            }
        }
    }


    //endregion
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


class ClientIO {
    private BufferedReader br;
    private OutputStreamWriter sw;

    public ClientIO(BufferedReader br, OutputStreamWriter sw) {
        this.br = br;
        this.sw = sw;
    }

    public BufferedReader getInputStream() {
        return this.br;
    }

    public OutputStreamWriter getOutputStream() {
        return this.sw;
    }
}