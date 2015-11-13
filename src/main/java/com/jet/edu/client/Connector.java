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
        StringBuilder sb = new StringBuilder();
        try {
            BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(), charset));

            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            bw.write(jsonMessage.toString() + System.lineSeparator());
            bw.flush();

            BufferedReader br = new BufferedReader(isr);

            String line = br.readLine();

            /*while (isr.ready()){
                sb.append((char)isr.read()+"");
            }*/

            sb.append(line);

        } catch (IOException e) {
            throw new ChatException("", e);
        }
        return sb.toString();
    }
}
