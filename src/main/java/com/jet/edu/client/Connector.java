package com.jet.edu.client;

import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
//            StringBuilder sb = new StringBuilder();
//            BufferedReader br = new BufferedReader(
//                    new InputStreamReader(socket.getInputStream(), charset));
            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            bw.write(jsonMessage.toString() + System.lineSeparator());
            bw.flush();
//            return isr.readLine();

            while (isr.ready()){
                sb.append((char)isr.read()+"");
            }
        } catch (IOException e) {
            throw new ChatException("", e);
        }
        return sb.toString();
    }
}
