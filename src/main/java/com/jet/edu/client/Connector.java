package com.jet.edu.client;

import org.json.JSONObject;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;

/**
 * Connected with server
 * read and write messages from server
 */
public class Connector {
    private Socket socket;
    private final static String charset = "UTF-8";

    /**
     * connection open
     * @param host
     * @param port
     * @throws ChatException
     */
    public Connector(String host, int port) throws ChatException {
        try {
            socket = SSLSocketFactory.getDefault().createSocket(host, port);
        } catch (IOException e) {
            throw new ChatException("", e);
        }
    }

    /**
     * send messages to server
     * @param jsonMessage
     * @return readed string messages
     * @throws ChatException
     */
    public String sendMessage(JSONObject jsonMessage) throws ChatException {
        String line;
        try (BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(), charset));
             InputStreamReader isr = new InputStreamReader(socket.getInputStream());) {
            bw.write(jsonMessage.toString() + System.lineSeparator());
            bw.flush();

            BufferedReader br = new BufferedReader(isr);
            while(!br.ready()){}
            line = br.readLine();
        } catch (IOException e) {
            throw new ChatException("", e);
        }
        return line;
    }
}
