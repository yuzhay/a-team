package com.jet.edu.server;

/**
 * Created by Yuriy on 12.11.2015.
 */
public class ChatStorage implements Storage {
    public void addMessage(String msg) {

    }

    public String[] getHistory() {
        return new String[0];
    }

    public boolean isUserOnline(String userName) {
        return false;
    }

    public void setUserOffline(String userName) {

    }

    public void addUser(String userName) {

    }
}
