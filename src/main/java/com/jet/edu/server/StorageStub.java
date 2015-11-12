package com.jet.edu.server;

import com.jet.edu.ChatLogger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by user on 12.11.2015.
 */
public class StorageStub implements Storage{

    @Override
    public void addMessage(String msg){

    }

    @Override
    public String[] getHistory() {
        return new String[0];
    }

    @Override
    public boolean isUserOnline(String userName) {
        return true;
    }

    @Override
    public void setUserOffline(String userName) {

    }

    @Override
    public void addUser(String userName) {

    }

    public JSONArray getHist(){
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(new JSONObject().put("id",1).put("name","name1"));
        jsonArray.put(new JSONObject().put("id",3).put("name","name2"));
        jsonArray.put(new JSONObject().put("id",3).put("name","name3"));
        jsonArray.put(new JSONObject().put("id",4).put("name","name4"));
        jsonArray.put(new JSONObject().put("id",5).put("name","name5"));
        return jsonArray;
    }
}
