package com.jet.edu;


import com.jet.edu.server.ChatStorage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Iterator;
import java.util.List;

/**
 * Created by user on 12.11.2015.
 */
public class TestChatStorage {
    private final String connStr = "jdbc:derby://localhost:1527/chat";
    private ChatStorage chatStorage;

    @Before
    public void setUpTests() {
        chatStorage = new ChatStorage(connStr);
    }

    @After
    public void shutDown() {

    }

    @Test
    public void shouldCorrectlyReturnOnlineStatus() throws Exception {
        chatStorage.connect();

        Assert.assertFalse(chatStorage.isUserOnline("Yura"));
    }

    @Test
    public void shouldSetOffline() throws Exception {
        chatStorage.connect();
        chatStorage.setUserOffline("Yura");
        Assert.assertFalse(chatStorage.isUserOnline("Yura"));
    }

    @Test
    @Ignore
    public void shouldCorrectlyGetHistory() throws Exception {
        chatStorage.connect();

        JSONArray jsonArray = chatStorage.getHistory();

        Assert.assertEquals(jsonArray.getJSONObject(0).getString("MESSAGE"), "Hello world!");
        Assert.assertEquals(jsonArray.getJSONObject(0).getString("NICKNAME"), "Yura");
    }

}
