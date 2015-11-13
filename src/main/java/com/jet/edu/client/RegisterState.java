package com.jet.edu.client;

import org.json.JSONObject;

import java.io.IOException;

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


    public void writeToConnector() throws ChatException {
        System.out.println(connector.sendMessage(jsonObject));
    }

    public void writerToConector() throws ChatException {
        String fromConnector = connector.sendMessage(jsonObject);
        if (fromConnector != null) {
            JSONObject messageFromServer = new JSONObject(fromConnector);
            String message = messageFromServer.get("msg").toString();
            System.out.println(message);
        } else{
            System.out.println("Нет соединения");
        }
    }

    public void ReadConnector() {
        //System.out.println(connector);
    }
}


