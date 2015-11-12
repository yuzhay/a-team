package com.acme.edu;


import com.jet.edu.server.ChatStorage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by user on 12.11.2015.
 */
public class TestChatStorage {
    private final String connStr =  "jdbc:derby://localhost:1527/chat";
    private ChatStorage chatStorage;

    @Before
    public void setUpTests(){
        chatStorage = new ChatStorage(connStr);
    }

    @After
    public void shutDown(){

    }


    @Test
    public void ShouldConnectToDb() throws Exception{
        chatStorage.connect();
    }

    @Test
    public void shouldDisconnectFromDB() throws Exception{
        chatStorage.disconnet();
    }

    @Test
    public void shouldCorrectlyAddUsers() throws Exception{
        chatStorage.addUser("USER1");
    }
}
