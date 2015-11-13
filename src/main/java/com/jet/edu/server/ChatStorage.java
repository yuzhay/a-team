package com.jet.edu.server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuriy on 12.11.2015.
 */
public class ChatStorage implements Storage {
    private final String connString;
    private Connection conn = null;

    /**
     * Constructor with provided connection string
     *
     * @param connString database connection string
     */
    public ChatStorage(String connString) {
        this.connString = connString;
    }

    /**
     * Constructor with default connection string
     */
    public ChatStorage() {
        this.connString = "jdbc:derby://localhost:1527/chat";
    }

    /**
     * Adds message to Messages table
     *
     * @param name author of the message
     * @param msg  message string
     * @return if message is added succeful
     */
    public boolean addMessage(String name, String msg) {
        String query1 = "(SELECT id FROM USERS WHERE name=?";
        String query2 = "INSERT INTO APP.MESSAGES (USER_ID,MESSAGE) VALUES(?, ?)";
        try {
            conn.setAutoCommit(false);
            PreparedStatement iq = conn.prepareStatement(query1);
            iq.setString(1, name);
            ResultSet result = iq.executeQuery();
            result.next();
            int uid = result.getInt(1);

            iq = conn.prepareStatement(query2);
            iq.setInt(1, uid);
            iq.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
                return false;
            }
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void changeRoom(String roomName, String userName){
        String query = "UPDATE APP.USERS SET ROOM_ID = (SELECT ID FROM ROOMS WHERE NAME = ?) WHERE NAME = ?";
        try (PreparedStatement iq = conn.prepareStatement(query)) {
            iq.setString(1, roomName);
            iq.setString(2, userName);
            iq.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns all history of the chat
     *
     * @return
     */
    public JSONArray getHistory() {
        String query = "SELECT USERS.NAME, MESSAGE, TIME FROM APP.MESSAGES INNER JOIN APP.USERS ON USER_ID = USERS.ID";
        JSONArray jsonArray = new JSONArray();
        try (PreparedStatement iq = conn.prepareStatement(query)) {
            ResultSet result = iq.executeQuery();
            while (result.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("MESSAGE", result.getString("MESSAGE"));
                jsonObject.put("TIME", result.getTimestamp("TIME").toString());
                jsonObject.put("NICKNAME", result.getString("NAME"));
                jsonArray.put(jsonObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        return list;
        return jsonArray;
    }

    public boolean isUserOnline(String userName) {
        String query = "SELECT ONLINE FROM APP.USERS WHERE name = ?";
        try (PreparedStatement iq = conn.prepareStatement(query)) {
            iq.setString(1, userName);
            ResultSet result = iq.executeQuery();

            if (result.next() == false) {
                return false;
            }

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
            iq.setString(1, userName);
            iq.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Register new user
     *
     * @param userName user name
     */
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

    /**
     * Disconnect from database
     */
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

    /**
     * Connect to database
     *
     * @throws SQLException
     */
    @Override
    public void connect() throws SQLException {
        conn = DriverManager.getConnection(connString);
    }
}
