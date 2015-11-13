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
    private JSONObject jsonObject;
    private Connector connector;

    public HistoryState(JSONObject jsonObject, Connector connector) {
        this.jsonObject = jsonObject;
        this.connector = connector;
    }

    /**
     * write messages to get history to Connector
     * @throws ChatException
     */
    public void writeToConnector() throws ChatException {
        String resp = connector.sendMessage(this.jsonObject);
        if (resp.equals(""))
            return;
        JSONObject responceJson = new JSONObject(resp);
//        System.out.println(connector.sendMessage(jsonObject));
        JSONArray history = responceJson.getJSONArray("history");
        Iterator<Object> jsonObjectIterator = history.iterator();
        while (jsonObjectIterator.hasNext()){
            JSONObject jsonObject = (JSONObject)jsonObjectIterator.next();
            System.out.print("name: "+jsonObject.getString("NICKNAME"));
            System.out.print(" ,message: "+jsonObject.getString("MESSAGE"));
            System.out.println(", time:" + jsonObject.getString("TIME"));
        }

    }

    /**
     * read messages
     */
//    public void ReadConnector() {
//    }
}
