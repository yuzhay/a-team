package com.jet.edu.client;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Павел on 12.11.2015.
 */
public class Connector {
    Socket socket;

    public Connector(String host, int port) throws ChatException {
        try {
            socket = new Socket(host,port);
        } catch (IOException e) {
            throw new ChatException("", e);
        }
    }

    public void sendMessage(JSONObject jsonMessage) throws ChatException {
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream())){
            dos.writeUTF(jsonMessage.toString());
        } catch (IOException e) {
            throw new ChatException("",e);
        }
    }

}
