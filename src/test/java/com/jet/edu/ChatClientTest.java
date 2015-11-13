package com.jet.edu;

import com.jet.edu.client.Chat;
import com.jet.edu.client.ChatException;
import com.jet.edu.client.Connector;
import com.jet.edu.client.Factory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

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
