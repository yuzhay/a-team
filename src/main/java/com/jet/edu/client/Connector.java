package com.jet.edu.client;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.net.Socket;

/**
 * Connected with server
 * read and write messages from server
 */
public class Connector {
    /**
     * charset
     */
    private final static String CHARSET = "UTF-8";

    /**
     * private fields
     */
    private Socket socket;
    private BufferedWriter bw;
    private BufferedReader br;

    /**
     * connection open
     *
     * @param socket - remote socket
     * @throws ChatException
     */
    public Connector(Socket socket) throws ChatException {
        try {
            this.socket = socket;
            bw = new BufferedWriter(
                    new OutputStreamWriter(this.socket.getOutputStream(), CHARSET));
            br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        }
        catch (IOException e) {
            throw new ChatException("", e);
        }
    }

    /**
     * send messages to server
     * @param jsonMessage - message
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