package com.jet.edu.server;

import com.jet.edu.ChatLogger;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.nio.charset.Charset;

/**
 * Created by Yuriy on 12.11.2015.
 */
public class ChatServer {
    //region private fields
    private final Charset charset = Charset.forName("utf-8");
    private final ChatLogger logger = new ChatLogger("ChatServer.log");
    private ServerSocket socket;
    private Thread serverThread;
    private Acceptor acceptor;
    //endregion

    /**
     * Start ChatServer
     */
    public void start() {
        try {
            serverThread = new Thread(acceptor);
            serverThread.start();
        } catch (RuntimeException ex) {
            logger.printSevere("Server can't start on localhost", ex);
        }
    }

    /**
     * Stop ChatServer
     */
    public void stop() {
        serverThread.interrupt();
        logger.printConsole("Server stopped");
    }

    /**
     * Default ChatServer constructor
     *
     * @param port set TCP port
     */
    public ChatServer(int port) throws IOException {
        socket = new ServerSocket(port);
        acceptor = new Acceptor(socket, logger, charset);

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
