package com.acme.edu;

import com.jet.edu.client.Chat;
import com.jet.edu.server.ChatServer;
import org.junit.After;
import org.junit.Before;

import java.util.Random;

/**
 * Created by Yuriy on 13.11.2015.
 */
public class ChatClientTest {
    private Chat chatClient;
    private int port;
    private Random rnd = new Random();

    @Before
    public void before() {
        port = rnd.nextInt(64000) + 1000;

    }

    @After
    public void after() {

    }
}
