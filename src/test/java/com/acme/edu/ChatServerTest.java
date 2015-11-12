package com.acme.edu;

import com.jet.edu.server.ChatServer;
import org.fest.assertions.Assert;
import org.junit.*;

import java.io.*;
import java.net.Socket;
import java.util.Random;

/**
 * Created by Yuriy on 13.11.2015.
 */
public class ChatServerTest {
    private ChatServer server;
    private int port;
    private Random rnd = new Random();

    @Before
    public void before() {
        port = rnd.nextInt(64000) + 1000;
        server = new ChatServer(port);
        server.start();
    }

    @After
    public void after() {
        server.stop();
    }

    @Test(timeout = 2000)
    @Ignore
    public void shouldServerResponseOnChidCommand() throws IOException {
        Socket s = new Socket("localhost", port);
        OutputStreamWriter sw = new OutputStreamWriter(s.getOutputStream());
        InputStreamReader sr = new InputStreamReader(s.getInputStream());
        BufferedReader br = new BufferedReader(sr);

        String given1 = "{'cmd':'/chid','msg':'user'}" + System.lineSeparator();
        String expected1 = "[PUT_EXPECTED_VALUE_HERE]";

        sw.write(given1);
        sw.flush();
        String actual1 = br.readLine();

        s.close();
        org.junit.Assert.assertEquals(expected1, actual1);
    }
}
