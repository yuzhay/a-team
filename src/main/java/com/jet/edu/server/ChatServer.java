package com.jet.edu.server;

import com.jet.edu.ChatLogger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Yuriy on 12.11.2015.
 */
public class ChatServer implements Server {
    //region private fields
    private final Charset charset = Charset.forName("utf-8");
    private final ChatLogger logger = new ChatLogger("ChatServer.log");
    private ServerSocket socket;
    private Thread serverThread;
    private Accepter accepter = new Accepter();
    //endregion

    /**
     * Start ChatServer
     */
    @Override
    public void start() {
        try {
            serverThread = new Thread(accepter);
            serverThread.start();
        } catch (RuntimeException ex) {
            logger.printSevere("Server can't start on localhost", ex);
        }
    }

    /**
     * Stop ChatServer
     */
    @Override
    public void stop() {
        serverThread.interrupt();
        logger.printConsole("Server stopped");
    }

    //region Accepter
    private class Accepter implements Runnable {
        //region private fields
        private List<IOException> exceptionsList = new ArrayList<>();
        private HashMap<Socket, ClientIO> clientStream = new HashMap<>();
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
                    logger.printConsole("New client connected");
                } catch (SocketTimeoutException ex) {
                    logger.printWarning("timeout", ex);
                    /*Do nothing. Time is out. Wait for next client*/
                } catch (IOException e) {
                    addException(e);
                    logger.printWarning(e.toString());
                    logger.printConsole("Client socket IO error. See log.");
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
                                    new OutputStreamWriter(sock.getOutputStream(), charset),
                                    new BufferedInputStream(sock.getInputStream()))
                    );
                } catch (IOException e) {
                    addException(e);
                    logger.printSevere("Server can't start on localhost:", e);
                }
            }
        }

        private void removeClient(Socket sock) {
            synchronized (clientStream) {
                clientStream.remove(sock);
            }
            logger.printConsole("Client disconnected: " + sock);
        }

        private class OneThreadWorker implements Runnable {
            @Override
            public void run() {
                ChatServerState css = new ChatServerState(clientStream, logger);
                while (true) {
                    synchronized (clientStream) {
                        for (Socket s : clientStream.keySet()) {
                            try {
                                BufferedReader br = clientStream.get(s).getInputStream();
                                OutputStreamWriter osw = clientStream.get(s).getOutputStream();
                                BufferedInputStream bis = clientStream.get(s).getBufferedInputStream();

                                if (bis.available() == 0) {
                                    continue;
                                }

                                String line = br.readLine();
                                System.out.println(s.toString() + ": " + line);

                                css.switchState(line, clientStream.get(s));
                            } catch (IOException e) {
                                logger.printWarning("IO client", e);
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
                        addException(e);
                        logger.printWarning("Send to clients", e);
                    }
                }
            }
        }

        private void addException(IOException ex) {
            synchronized (exceptionsList) {
                exceptionsList.add(ex);
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
            logger.printSevere("Server can't bind localhost:" + port, e);
        }
    }

    //endregion
}

class ClientIO {
    private BufferedReader br;
    private OutputStreamWriter sw;
    private BufferedInputStream bis;
    //region public methods

    /**
     * Default ClientIO constructor
     *
     * @param br socket input stream
     * @param sw socket output stream
     */
    public ClientIO(BufferedReader br, OutputStreamWriter sw, BufferedInputStream bis) {
        this.br = br;
        this.sw = sw;
        this.bis = bis;
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
     * Get socket buffered input stream
     *
     * @return input stream
     */
    public BufferedInputStream getBufferedInputStream() {
        return this.bis;
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
