package com.jet.edu.client;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.Timestamp;
import java.util.Iterator;

/**
 * HistoryState send command /hist to server
 * read answer from server
 * print to console answer
 * Command /hist -  wait history of messaged
 */
public class HistoryState implements State {

    private final JSONObject jsonObject;
    private final Connector connector;

    /**
     * initialize connection and jsonObject
     *
     * @param jsonObject
     * @param connector
     */
    public HistoryState(JSONObject jsonObject, Connector connector) {
        this.jsonObject = jsonObject;
        this.connector = connector;
    }

    /**
     * write messages to get history to Connector
     *
     * @throws ChatException
     */
    public void writerToConnector() throws ChatException {
        connector.sendMessage(jsonObject);
    }
}
