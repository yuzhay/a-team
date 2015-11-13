package com.jet.edu.client;

import org.json.JSONObject;


import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
        StringBuilder sb = new StringBuilder();
        try (BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(), charset));
             InputStreamReader isr = new InputStreamReader(socket.getInputStream());) {
            bw.write(jsonMessage.toString() + System.lineSeparator());
            bw.flush();
            while (isr.ready()){
                sb.append((char)isr.read()+"");
            }
        } catch (IOException e) {
            throw new ChatException("", e);
        }
        return sb.toString();
    }
}
