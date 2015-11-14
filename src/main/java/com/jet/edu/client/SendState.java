package com.jet.edu.client;

import org.json.JSONObject;

/**
 * SendState send command /snd to server
 * read answer from server
 * print to console answer
 * Command /snd <message> -send message
 */
public class SendState implements State {
    private final JSONObject jsonObject;
    private final Connector connector;

    /**
     * initialize jsonObject and connection
     *
     * @param jsonObject
     * @param connector
     */
    public SendState(JSONObject jsonObject, Connector connector) {
        this.jsonObject = jsonObject;
        this.connector = connector;
    }

    /**
     * write messages to Connector
     *
     * @throws ChatException
     */
    public void writeToConnector() throws ChatException {
        connector.sendMessage(jsonObject);
    }
}
