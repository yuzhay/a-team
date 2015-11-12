package com.jet.edu.client;

import org.json.JSONObject;

/**
 * Created by Павел on 12.11.2015.
 */
public class RegisterState implements State {
    private JSONObject jsonObject;
    private Connector connector;

    public RegisterState(JSONObject jsonObject, Connector connector) {
        this.jsonObject = jsonObject;
        this.connector = connector;
    }

    public void writerToConector() throws ChatException {
        System.out.println(connector.sendMessage(jsonObject));
    }

    public void ReadConnector() {
    }
}


