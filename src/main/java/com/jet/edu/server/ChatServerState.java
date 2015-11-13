package com.jet.edu.server;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Hashtable;

/**
 * Created by Yuriy on 12.11.2015.
 */
public class ChatServerState implements ServerState {

    private final Storage storage = new ChatStorage();
    private Hashtable<Socket, ClientIO> clients;

    public ChatServerState(Hashtable<Socket, ClientIO> clients) {
        this.clients = clients;
    }

    public void switchState(String str, ClientIO client) {
        JSONObject response = new JSONObject();
        JSONObject json;

        OutputStreamWriter osw = client.getOutputStream();
        BufferedReader br = client.getInputStream();

        String cmd, msg;

        try {
            json = new JSONObject(str);
            cmd = json.getString("cmd");
            msg = json.getString("msg");
        } catch (JSONException ex) {
            response.put("status", "error");
            response.put("msg", "Unknown command");
            sendResponse(response, osw);
            return;
        }

        response.put("status", "ok");

        try {
            storage.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        switch (cmd) {
            case COMMAND_SND:
                String name = json.getString("name");
                boolean added = storage.addMessage(name, msg);
                if(added) {
                    response.put("msg", msg);
                    sendResponseToAll(response, osw);
                }else{
                    response.put("status", "error");
                    sendResponse(response, osw);
                }
                return;
            case COMMAND_CHID:
                if (storage.isUserOnline(msg)) {
                    response.put("status", "error");
                    response.put("msg", "This users is already registered");
                    sendResponse(response, osw);
                    return;
                }
                storage.addUser(msg);
                response.put("msg", "User registered");
                sendResponse(response, osw);
                return;
            case COMMAND_HIST:
                response.put("history", storage.getHistory());
                sendResponse(response, osw);
                return;
        }
    }


    private void sendResponse(JSONObject json, OutputStreamWriter osw) {
        try {
            osw.write(json.toString());
            osw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendResponseToAll(JSONObject json, OutputStreamWriter osw) {
        for (ClientIO c : clients.values()) {
            if (c.getOutputStream() == osw) {
                continue;
            }
            try {
                c.getOutputStream().write(json.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
