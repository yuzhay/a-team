package com.jet.edu.client;

import org.json.JSONObject;

/**
 * Created by Павел on 14.11.2015.
 */
public class RoomState implements State {

    private JSONObject jsonObject;
    private Connector connector;

    public RoomState(JSONObject jsonObject, Connector connector) {
        this.jsonObject = jsonObject;
        this.connector = connector;
    }

    public void writerToConnector() throws ChatException {
        connector.sendMessage(jsonObject);
    }

}
