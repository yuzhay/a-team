package com.jet.edu;

import com.jet.edu.server.ChatServer;
import org.json.JSONObject;
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

    @Test(timeout = 20000)
    public void shouldServerResponseOnChidCommand() throws IOException {
        Socket s = new Socket("localhost", port);
        OutputStreamWriter sw = new OutputStreamWriter(s.getOutputStream());
        InputStreamReader sr = new InputStreamReader(s.getInputStream());
        BufferedReader br = new BufferedReader(sr);

        String given1 = "{'cmd':'/chid','msg':'user'}" + System.lineSeparator();
        String expected1 = "ok";

        sw.write(given1);
        sw.flush();
        String actual1 = br.readLine();
        JSONObject json = new JSONObject(actual1);
        s.close();
        org.junit.Assert.assertEquals(expected1, json.getString("status"));
    }
}
