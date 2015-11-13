package com.jet.edu.client;

import org.json.JSONObject;
import com.jet.edu.Constants;
/**
 * RegisterState send command /chid to server
 * read answer from server
 * print to console answer
 * command /chid <name> - register name
 */
public class RegisterState implements State {
    private JSONObject jsonObject;
    private Connector connector;
    private String currentname = "";

    /**
     * initialize jsonObject and connection
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
    public void writeToConnector() throws ChatException {
        String fromConnector = connector.sendMessage(jsonObject);
        if (fromConnector != null) {
            JSONObject messageFromServer = new JSONObject(fromConnector);
            String message = messageFromServer.get("msg").toString();
            System.out.println(message);
        } else{
            System.out.println(Constants.NO_CONNECTION);
        }
    }

    /**
     * read messages
     */
//    public void ReadConnector() {
//        //System.out.println(connector);
//    }
}


