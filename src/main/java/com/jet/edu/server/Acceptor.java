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
import java.util.HashMap;
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
    //endregion

    public Acceptor(ServerSocket serverSocket, ChatLogger logger, Charset charset){
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
                logger.printWarning(e.toString());
                logger.printConsole("Client serverSocket IO error. See log.");
            }
        }
    }

    private void addClient(Socket sock) {
        synchronized (clientsMap) {
            try {
                clientsMap.put(sock,
                        new ClientIO(
                                new BufferedReader(
                                        new InputStreamReader(sock.getInputStream(), charset)
                                ),
                                new OutputStreamWriter(sock.getOutputStream(), charset),
                                new BufferedInputStream(sock.getInputStream()))
                );
            } catch (IOException e) {
                logger.printSevere("Server can't start on localhost:", e);
            }
        }
    }

    private void removeClient(Socket sock) {
        synchronized (clientsMap) {
            clientsMap.remove(sock);
        }
        logger.printConsole("Client disconnected: " + sock);
    }

    private class OneThreadWorker implements Runnable {
        @Override
        public void run() {
            ChatServerModel css = new ChatServerModel(clientsMap, logger);
            //noinspection InfiniteLoopStatement
            while (true) {
                synchronized (clientsMap) {
                    for (Map.Entry<Socket, ClientIO> elem : clientsMap.entrySet()) {
                        try {
                            BufferedReader br = elem.getValue().getInputStream();
                            BufferedInputStream bis = elem.getValue().getBufferedInputStream();

                            if (bis.available() == 0) {
                                continue;
                            }

                            String line = br.readLine();
                            System.out.println(elem.getKey().toString() + ": " + line);

                            css.execute(line, clientsMap.get(elem.getKey()));
                        } catch (IOException e) {
                            logger.printWarning("IO client", e);
                            removeClient(elem.getKey());
                        }
                    }
                }
            }
        }
    }
}

