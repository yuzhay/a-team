package com.jet.edu.client;

import org.json.JSONArray;
import org.json.JSONObject;

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
        String resp = connector.sendMessage(this.jsonObject);
        if (resp.equals(""))
            return;
        JSONObject responceJson = new JSONObject(resp);
        JSONArray history = responceJson.getJSONArray("history");
        Iterator<Object> jsonObjectIterator = history.iterator();
        while (jsonObjectIterator.hasNext()) {
            JSONObject jsonObject = (JSONObject) jsonObjectIterator.next();

            println(
                    String.format("%s[%s]: %s",
                            jsonObject.getString("NICKNAME"),
                            jsonObject.getString("MESSAGE"),
                            jsonObject.getString("TIME")
                    ));

        }

    }
}
