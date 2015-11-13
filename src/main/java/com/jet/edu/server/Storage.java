package com.jet.edu.server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Yuriy on 12.11.2015.
 */
public interface Storage {
    boolean addMessage(String name, String msg);
    JSONArray getHistory();
    boolean isUserOnline(String userName);
    void setUserOffline(String userName);
    void addUser(String userName);
    void disconnect();
    void connect() throws SQLException;
}
