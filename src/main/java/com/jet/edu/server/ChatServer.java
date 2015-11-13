package com.jet.edu.server;

import com.jet.edu.ChatLogger;

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
    private ChatLogger chatLogger = new ChatLogger(System.getProperty("user.home")+ File.separator +"logger.txt");
    //endregion

    /**
     * Start ChatServer
     */
    public void start() {
        try {
            serverThread = new Thread(accepter);
            serverThread.start();
        } catch (RuntimeException ex) {
            chatLogger.printSevere(ex.toString());
        }
    }

    /**
     * Stop ChatServer
     */
    public void stop() {
        serverThread.interrupt();
    }

    //region Accepter
    private class Accepter implements Runnable {
        //region private fields
        private List<IOException> exceptionsList = new ArrayList<>();
        private ExecutorService pool = Executors.newFixedThreadPool(1000);
        private Hashtable<Socket, ClientIO> clientStream = new Hashtable<>();
        //endregion

        //region public methods

        /**
         * Socket accept thread runner
         */
        @Override
        public void run() {
            new Thread(new OneThreadWorker()).start();

            while (!Thread.interrupted()) {
                try {
                    Socket client = socket.accept();
                    addClient(client);
                    System.out.println("New client connected");
                } catch (SocketTimeoutException ste) {
                    chatLogger.printSevere(ste.toString());
                } catch (IOException e) {
                    exceptionsList.add(e);
                }
            }
        }

        private void addClient(Socket sock) {
            synchronized (clientStream) {
                try {
                    clientStream.put(sock,
                            new ClientIO(
                                    new BufferedReader(
                                            new InputStreamReader(sock.getInputStream(), charset)
                                    ),
                                    new OutputStreamWriter(sock.getOutputStream(), charset)));
                } catch (IOException e) {
                    chatLogger.printSevere(e.toString());
                }
            }
        }

        private void removeClient(Socket sock) {
            synchronized (clientStream) {
                clientStream.remove(sock);
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
                                chatLogger.printSevere(e.toString());
                                removeClient(s);
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
                        chatLogger.printSevere(e.toString());
                    }
                }
            }
        }
    }

    //endregion

    /**
     * Default ChatServer constructor
     *
     * @param port set TCP port
     */
    public ChatServer(int port) {
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            chatLogger.printSevere(e.toString());
        }
    }

    //endregion
}

class ClientIO {
    private BufferedReader br;
    private OutputStreamWriter sw;

    //region public methods

    /**
     * Default ClientIO constructor
     *
     * @param br socket input stream
     * @param sw socket output stream
     */
    public ClientIO(BufferedReader br, OutputStreamWriter sw) {
        this.br = br;
        this.sw = sw;
    }

    /**
     * Get socket input stream
     *
     * @return input stream
     */
    public BufferedReader getInputStream() {
        return this.br;
    }

    /**
     * Get socket output stream
     *
     * @return output stream
     */
    public OutputStreamWriter getOutputStream() {
        return this.sw;
    }

    //endregion
}
