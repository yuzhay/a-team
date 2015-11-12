package com.acme.edu;


import com.jet.edu.server.ChatStorage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        chatStorage.disconnect();
    }

    @Test
    public void shouldCorrectlyAddUsers() throws Exception{
        chatStorage.connect();
        chatStorage.addUser("USER1");
    }

    @Test
    public void shouldCorrectlyReturnOnlineStatus() throws Exception{
        chatStorage.connect();

        Assert.assertFalse(chatStorage.isUserOnline("Yura"));   }

    @Test
    public void shouldSetOffline() throws Exception{
        chatStorage.connect();
        chatStorage.setUserOffline("Yura");
        Assert.assertFalse(chatStorage.isUserOnline("Yura"));
    }


    @Test
    public void addMessage() throws Exception{
        chatStorage.connect();
        chatStorage.addMessage("Yura","Hello!");
    }


}
