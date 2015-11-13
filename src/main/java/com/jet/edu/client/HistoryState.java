package com.jet.edu.client;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by Павел on 12.11.2015.
 */
public class HistoryState implements State {

    private JSONObject jsonObject;
    private Connector connector;

    public HistoryState(JSONObject jsonObject, Connector connector) {
        this.jsonObject = jsonObject;
        this.connector = connector;
    }

    public void writerToConnector() throws ChatException {
        String resp = connector.sendMessage(this.jsonObject);
        if (resp.equals(""))
            return;
        JSONObject responceJson = new JSONObject(resp);
        JSONArray history = responceJson.getJSONArray("history");
        Iterator<Object> jsonObjectIterator = history.iterator();
        while (jsonObjectIterator.hasNext()){
            JSONObject jsonObject = (JSONObject)jsonObjectIterator.next();
            System.out.print("name: "+jsonObject.getString("NICKNAME"));
            System.out.print(" ,message: "+jsonObject.getString("MESSAGE"));
            System.out.println(", time:" + jsonObject.getString("TIME"));
        }
    }
}
