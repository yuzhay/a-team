package com.jet.edu;

import com.jet.edu.server.ChatServer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by user on 13.11.2015.
 */
public class ServerTest {
    private ChatServer chatServer;
    @Before
    public void setUpTest(){
        chatServer = new ChatServer(12345);
    }

}

