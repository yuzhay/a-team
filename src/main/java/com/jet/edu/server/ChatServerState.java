package com.jet.edu.server;

import com.jet.edu.ChatLogger;
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
    private ChatLogger logger;

    public ChatServerState(Hashtable<Socket, ClientIO> clients, ChatLogger logger) {
        this.clients = clients;
        this.logger = logger;
    }

    @Override
    public void switchState(String str, ClientIO client) {
        JSONObject response = new JSONObject();
        JSONObject json;

        OutputStreamWriter osw = client.getOutputStream();
        BufferedReader br = client.getInputStream();

        String cmd;
        String msg = "";

        try {
            json = new JSONObject(str);
            cmd = json.getString("cmd");

            if (json.has("msg")) {
                msg = json.getString("msg");
            }
        } catch (JSONException ex) {
            response.put("status", "error");
            response.put("msg", "Unknown command");
            sendResponse(response, osw);
            logger.printSevere(response.toString(), ex);
            return;
        }

        response.put("status", "ok");

        try {
            storage.connect();
        } catch (SQLException e) {
            logger.printSevere(response.toString(), e);
        }

        switch (cmd) {
            case COMMAND_SND:
                if (!json.has("name")) {
                    response.put("status", "error");
                    response.put("msg", "Can't find 'name' in json");
                    sendResponse(response, osw);
                    logger.printWarning(response.toString());
                    return;
                }
                String name = json.getString("name");
                long timestamp = storage.addMessage(name, msg);
                if (timestamp > 0) {
                    response.put("msg", msg);
                    response.put("name", name);
                    response.put("time", timestamp);

                    JSONObject ownerResponse = new JSONObject();
                    ownerResponse.put("status", "ok");
                    sendResponse(ownerResponse, osw);

                    sendResponseToAll(response, osw);
                } else {
                    response.put("status", "error");
                    response.put("msg", "Can't add message");
                    sendResponse(response, osw);
                    logger.printWarning(response.toString());
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
            osw.write(json.toString() + System.lineSeparator());
            osw.flush();
            System.out.println("Server answered: '" + json.toString() + "'");
        } catch (IOException e) {
            logger.printWarning("Connection lost", e);
            logger.printConsole("Connection closed by peer");
        }
    }

    private void sendResponseToAll(JSONObject json, OutputStreamWriter osw) {
        for (ClientIO c : clients.values()) {
            if (c.getOutputStream().equals(osw)) {
                continue;
            }
            try {
                c.getOutputStream().write(json.toString());
            } catch (IOException e) {
                logger.printWarning("Not send Messaged to sockets", e);
            }
        }
    }
}
