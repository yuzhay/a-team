package com.jet.edu.server;

import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuriy on 12.11.2015.
 */
public class ChatStorage implements Storage {
    private final String connString;
    private Connection conn = null;

    public ChatStorage(String connString) {
        this.connString = connString;
    }

    public ChatStorage() {
        this.connString = "jdbc:derby://localhost:1527/chat";
    }

    public void addMessage(String name, String msg) {
        String query = "INSERT INTO APP.MESSAGES (USER_ID,MESSAGE) VALUES((SELECT id FROM USERS WHERE name=?), ?)";
        try (
                PreparedStatement iq = conn.prepareStatement(query)) {
            iq.setString(1, name);
            iq.setString(2, msg);
            iq.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<JSONObject> getHistory() {
        String query = "SELECT USERS.NAME, MESSAGE, TIME FROM APP.MESSAGES INNER JOIN APP.USERS ON USER_ID = USERS.ID";
        List<JSONObject> list = new ArrayList<>();
        try (PreparedStatement iq = conn.prepareStatement(query)){
            ResultSet result = iq.executeQuery();
            while (result.next()){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("MESSAGE",result.getString("MESSAGE"));
                jsonObject.put("TIME",result.getTimestamp("TIME").toString());
                jsonObject.put("NICKNAME",result.getString("NAME"));
                list.add(jsonObject);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public boolean isUserOnline(String userName) {
        String query = "SELECT ONLINE FROM APP.USERS WHERE name=?";
        try (PreparedStatement iq = conn.prepareStatement(query)) {
            iq.setString(1, userName);
            ResultSet result = iq.executeQuery();
            result.next();
            int online = result.getInt("ONLINE");
            return online > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setUserOffline(String userName) {
        String query = "UPDATE APP.USERS set ONLINE = 0 WHERE name = ?";
        try (
                PreparedStatement iq = conn.prepareStatement(query)) {
            iq.setString(1,userName);
            iq.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUser(String userName) {
        String query = "INSERT INTO APP.USERS (NAME, ONLINE) VALUES(?, 1)";
        try (
                PreparedStatement iq = conn.prepareStatement(query)) {
            iq.setString(1, userName);
            iq.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                /*ToDO: log this error*/
            }
        }
    }

    @Override
    public void connect() throws SQLException {
        conn = DriverManager.getConnection(connString);
    }
}
