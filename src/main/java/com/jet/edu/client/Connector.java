package com.jet.edu.client;

import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

/**
 * Created by Павел on 12.11.2015.
 */
public class Connector {
    Socket socket;
    private String charset = "UTF-8";

    public Connector(String host, int port) throws ChatException {
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            throw new ChatException("", e);
        }
    }

    public String sendMessage(JSONObject jsonMessage) throws ChatException {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream(), charset));
             BufferedReader br = new BufferedReader(
                     new InputStreamReader(socket.getInputStream(), charset))) {
            bw.write(jsonMessage.toString());
            return br.readLine();
        } catch (IOException e) {
            throw new ChatException("", e);
        }
    }
}
