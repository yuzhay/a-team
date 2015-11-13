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
    private BufferedWriter bw;
    private BufferedReader br;

    public Connector(String host, int port) throws ChatException {
        try {
            socket = new Socket(host, port);
            bw = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(), charset));
            br = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), charset));
        } catch (IOException e) {
            throw new ChatException("", e);
        }
    }

    public String sendMessage(JSONObject jsonMessage) throws ChatException {
        try {
            bw.write(jsonMessage.toString() + System.lineSeparator());
            bw.flush();
            return br.readLine();
        } catch (IOException e) {
            throw new ChatException("", e);
        }
    }

    public void listenMessage() throws ChatException {
        try {
            while(br.readLine() != null)
            {
                System.out.print(br.readLine());
            }
        } catch (IOException e) {
            throw new ChatException("", e);
        }
    }
}
