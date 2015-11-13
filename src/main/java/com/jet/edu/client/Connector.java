package com.jet.edu.client;

import org.json.JSONObject;

import java.io.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import javax.net.ssl.SSLSocketFactory;
import java.net.Socket;

/**
 * Connected with server
 * read and write messages from server
 */
public class Connector {
    private Socket socket;
    private BufferedWriter bw;
    private BufferedReader br;

    private final static String charset = "UTF-8";

    /**
     * connection open
     *
     * @param host
     * @param port
     * @throws ChatException
     */
    public Connector(String host, int port) throws ChatException {
        try {
            socket = SSLSocketFactory.getDefault().createSocket(host, port);
            bw = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(), charset));
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
        try {
            bw.write(jsonMessage.toString() + System.lineSeparator());
            bw.flush();
            while (!br.ready()) {}
            return br.readLine();
        } catch (IOException e) {
            throw new ChatException("", e);
        }
    }
}
