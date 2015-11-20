package com.jet.edu;

import com.jet.edu.server.ChatServer;
import org.json.JSONObject;
import org.junit.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Created by Yuriy on 13.11.2015.
 */
public class ChatServerTest {
    private ChatServer server;
    private int port;
    private Random rnd = new Random();

    @Before
    public void before() throws IOException {
        /*port = rnd.nextInt(64000) + 1000;
        server = new ChatServer(port);
        server.start();*/
    }

    @After
    public void after() {
        //server.stop();
    }

    @Test
    @Ignore
    public void shouldServerResponseOnChidCommand() throws IOException {
        Socket[] s = new Socket[1000];

        double sum = 0;
        Random rnd = new Random();

        for (int i = 0; i < s.length; i++) {
            s[i] = new Socket("localhost", 12348);

            OutputStreamWriter sw = new OutputStreamWriter(s[i].getOutputStream());
            InputStreamReader sr = new InputStreamReader(s[i].getInputStream());
            BufferedReader br = new BufferedReader(sr);

            String given1 = "{'cmd':'/chid','msg':'test_user_" + rnd.nextInt() + "'}" + System.lineSeparator();
            String expected1 = "ok";

            double startTime = System.currentTimeMillis();

            sw.write(given1);
            sw.flush();
            String actual1 = br.readLine();

            double endTime = System.currentTimeMillis() - startTime;

            sum += endTime;

            JSONObject json = new JSONObject(actual1);
            org.junit.Assert.assertEquals(expected1, json.getString("status"));
        }


        for (int i = 0; i < s.length; i++) {
            s[i].close();
        }

        System.out.println("Sum Avg:" + (sum / s.length) / 1000);
    }

    @Test
    @Ignore
    public void shouldServerResponseOnChidCommand2() throws IOException {

        int users = 1000;
        int messages = 100;
        int messageSleep = 100;
        int userSleep = 100;

        Random rnd = new Random();

        List result = Collections.synchronizedList(new ArrayList<Double>());

        for (int i = 0; i < users; i++) {
            new Thread(() -> {
                Socket s = null;
                try {
                    s = new Socket("localhost", 12348);
                    double totalSum = 0;
                    double sum = 0;
                    OutputStreamWriter sw = new OutputStreamWriter(s.getOutputStream());
                    InputStreamReader sr = new InputStreamReader(s.getInputStream());
                    BufferedReader br = new BufferedReader(sr);

                    int id = rnd.nextInt();
                    String given1 = "{'cmd':'/chid','msg':'test_user_" + id + "'}" + System.lineSeparator();
                    String expected1 = "ok";

                    double startTime = System.currentTimeMillis();

                    sw.write(given1);
                    sw.flush();
                    String actual1 = br.readLine();
                    sum = System.currentTimeMillis() - startTime;
                    totalSum += sum;

                    for (int j = 0; j < messages - 1; j++) {
                        String given2;
                        if (j % 2 == 0) {
                            given2 = "{'cmd':'/snd','name':'test_user_" + id + "','msg:'asd'}" + System.lineSeparator();
                        } else {
                            given2 = "{'cmd':'/hist','name':'test_user_" + id + "'}" + System.lineSeparator();
                        }
                        startTime = System.currentTimeMillis();
                        sw.write(given2);
                        sw.flush();
                        actual1 = br.readLine();
                        sum = System.currentTimeMillis() - startTime;
                        totalSum += sum;
                        try {
                            Thread.sleep(messageSleep);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    result.add(totalSum / messages);
                    System.out.println(totalSum / messages);
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
            try {
                Thread.sleep(userSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        double totalResult = 0;
        for (int i = 0; i < result.size(); i++) {
            totalResult += (double) result.get(i);
        }
        System.out.println("Total:" + (totalResult / result.size()) + " ms");
    }


}
