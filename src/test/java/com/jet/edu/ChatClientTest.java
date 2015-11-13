package com.jet.edu;

import com.jet.edu.client.Chat;
import com.jet.edu.client.ChatException;
import com.jet.edu.client.Connector;
import com.jet.edu.client.Factory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Yuriy on 13.11.2015.
 */
public class ChatClientTest {
    private Chat chatClient;
    private Factory mockFactory;
    private Connector mockConnector;
    private int port;
    private Random rnd = new Random();
    @Before
    public void before() throws ChatException{
        port = rnd.nextInt(64000) + 1000;
        mockFactory = Mockito.mock(Factory.class);
        mockConnector = Mockito.mock(Connector.class);
        chatClient = new Chat(mockFactory,mockConnector);
    }

    @After
    public void after() {

    }

//    @Test
//    public void shouldReturn
}
