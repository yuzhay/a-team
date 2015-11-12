package com.jet.edu.server;

import org.json.JSONObject;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Yuriy on 12.11.2015.
 */
public interface Storage {
    void addMessage(String name, String msg);
    List<JSONObject> getHistory();
    boolean isUserOnline(String userName);
    void setUserOffline(String userName);
    void addUser(String userName);
    void disconnect();
    void connect() throws SQLException;
}
