package com.jet.edu.server;

/**
 * Created by Yuriy on 12.11.2015.
 */
public interface Storage {
    void addMessage(String msg);
    String[] getHistory();
    boolean isUserOnline(String userName);
    void setUserOffline(String userName);
    void addUser(String userName);
}
