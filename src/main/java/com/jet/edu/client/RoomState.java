package com.jet.edu.client;

import org.json.JSONObject;

/**
 * Created by Павел on 13.11.2015.
 */
public class RoomState implements State {

    private JSONObject jsonObject;
    private Connector connector;

    public RoomState(JSONObject jsonObject, Connector connector) {
        this.jsonObject = jsonObject;
        this.connector = connector;
    }

    public void writerToConnector() throws ChatException {
        String fromConnector = connector.sendMessage(jsonObject);
        if (fromConnector != null) {
            JSONObject messageFromServer = new JSONObject(fromConnector);
            String message = messageFromServer.get("msg").toString();
            System.out.println(message);
        } else{
            System.out.println("Нет соединения");
        }
    }
}
