package com.jet.edu.server;

import com.jet.edu.ChatLogger;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ChatServer Acceptor class
 * Created by Yuriy on 14.11.2015.
 */
//region Acceptor
class Acceptor implements Runnable {
    private final ChatLogger logger;
    private final ServerSocket serverSocket;
    private final Charset charset;
    //region private fields
    private final HashMap<Socket, ClientIO> clientsMap = new HashMap<>();
    private final List<ClientIO> clientsArray = new ArrayList<>(200);
    //endregion

    public Acceptor(ServerSocket serverSocket, ChatLogger logger, Charset charset) {
        this.logger = logger;
        this.serverSocket = serverSocket;
        this.charset = charset;
    }
    //region public methods

    /**
     * Socket accept thread runner
     */
    @Override
    public void run() {
        new Thread(new OneThreadWorker()).start();
        while (!Thread.interrupted()) {
            try {
                Socket client = serverSocket.accept();
                addClient(client);
                logger.printConsole("New client connected");
            } catch (SocketTimeoutException ex) {
                logger.printWarning("timeout", ex);
                    /*Do nothing. Time is out. Wait for next client*/
            } catch (IOException e) {
                logger.printWarning("Client serverSocket IO error", e);
                logger.printConsole("Client serverSocket IO error. See log.");
            }
        }
    }

    private void addClient(Socket sock) {
        synchronized (clientsMap) {
            try {
                clientsArray.add(new ClientIO(
                        new BufferedReader(
                                new InputStreamReader(sock.getInputStream(), charset)
                        ),
                        new OutputStreamWriter(sock.getOutputStream(), charset),
                        new BufferedInputStream(sock.getInputStream()), ""));
            } catch (IOException e) {
                logger.printSevere("Server can't start on localhost:", e);
            }
        }
    }

    private void removeClient(int index) {
        synchronized (clientsArray) {
            clientsArray.remove(index);
        }

    }

    private class OneThreadWorker implements Runnable {

        private ChatStorage storage = new ChatStorage();

        @Override
        public void run() {
            ChatServerModel css = new ChatServerModel(clientsArray, logger);

            while (true) {
                synchronized (clientsArray) {
                    for (int i = 0; i < clientsArray.size(); i++) {
                        ClientIO c = clientsArray.get(i);
                        try {
                            BufferedReader br = c.getInputStream();
                            BufferedInputStream bis = c.getBufferedInputStream();

                            if (bis.available() == 0) {
                                continue;
                            }

                            String line = br.readLine();
                            logger.printConsole(c.toString() + ": " + line);

                            css.execute(line, i);
                        } catch (IOException e) {
                            logger.printWarning("IO client", e);
                            removeClient(i);
                            storage.removeUser(c.getUserName());
                        }
                    }
                }

            }
        }
    }
}

