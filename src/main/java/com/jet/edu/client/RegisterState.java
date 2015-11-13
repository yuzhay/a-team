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
     *
     * @throws ChatException
     */
    public boolean writerToConnector() throws ChatException {
        String fromConnector = connector.sendMessage(jsonObject);
        if (fromConnector != null) {
           return checkMessageFromServer(fromConnector);
        } else {
            println("Нет соединения");
            return false;
        }
    }

    private boolean checkMessageFromServer(String msgFromServer) {
        JSONObject messageFromServer = new JSONObject(msgFromServer);
        String message = messageFromServer.get("msg").toString();
        if (message.equals("OK")){
            return true;
        }else{
            return false;
        }
    }
}


