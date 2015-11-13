package com.jet.edu.client;

import org.json.JSONObject;

/**
 * Created by Павел on 12.11.2015.
 */
public class RegisterState implements State {
    private JSONObject jsonObject;
    private Connector connector;
    private String currentname = "";

    public RegisterState(JSONObject jsonObject, Connector connector) {
        this.jsonObject = jsonObject;
        this.connector = connector;
    }

    public boolean writerToConnector() throws ChatException {
        String fromConnector = connector.sendMessage(jsonObject);
        if (fromConnector != null) {
            JSONObject messageFromServer = new JSONObject(fromConnector);
            String message = messageFromServer.get("msg").toString();
            return message.equals("OK");
            //System.out.println(message);
        } else{
            System.out.println("Нет соединения");
            return false;
        }
    }
}


