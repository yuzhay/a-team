package com.jet.edu.client;

import org.json.JSONObject;
import com.jet.edu.Constants;
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

    public void writerToConnector() throws ChatException {
        String fromConnector = connector.sendMessage(jsonObject);
        if (fromConnector != null) {
            JSONObject messageFromServer = new JSONObject(fromConnector);
            String message = messageFromServer.get("msg").toString();
            System.out.println(message);
        } else{
            System.out.println(Constants.NO_CONNECTION);
        }
    }

    public void ReadConnector() throws ChatException {
        connector.listenMessage();
    }
}


