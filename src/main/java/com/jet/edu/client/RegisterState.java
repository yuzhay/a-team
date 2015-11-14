package com.jet.edu.client;

import org.json.JSONObject;

/**
 * RegisterState send command /chid to server
 * read answer from server
 * print to console answer
 * command /chid <name> - register name
 */
public class RegisterState implements State {
    private final JSONObject jsonObject;
    private final Connector connector;

    /**
     * initialize jsonObject and connection
     *
     * @param jsonObject
     * @param connector
     */
    public RegisterState(JSONObject jsonObject, Connector connector) {
        this.jsonObject = jsonObject;
        this.connector = connector;
    }

    /**
     * write messages registrationName to Connector
     * @throws ChatException
     */
    public void writerToConnector() throws ChatException {
        connector.sendMessage(jsonObject);
    }

    private boolean checkMessageFromServer(String msgFromServer) {
        JSONObject messageFromServer = new JSONObject(msgFromServer);
        String message = messageFromServer.get("status").toString();
        if (message.equals("ok")) {
            System.out.println(message);
            System.out.flush();
            return true;
        } else {
            return false;
        }
    }
}


