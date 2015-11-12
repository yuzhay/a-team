package com.jet.edu.server;

import java.sql.SQLException;

/**
 * Created by Yuriy on 12.11.2015.
 */
public interface Storage {
    void addMessage(String name, String msg);
    String[] getHistory();
    boolean isUserOnline(String userName);
    void setUserOffline(String userName);
    void addUser(String userName);
    void disconnect();
    void connect() throws SQLException;
}
