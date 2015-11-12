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

    public void connector(String host, int port) throws IOException {
        socket = new Socket(host,port);
    }

    private void sendMessage(JSONObject jsonMessage) throws ChatException {
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream())){
            dos.writeUTF(jsonMessage.toString());
        } catch (IOException e) {
            throw new ChatException("",e);
        }
    }

}
