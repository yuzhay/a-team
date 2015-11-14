package com.jet.edu;

import com.jet.edu.client.Chat;
import com.jet.edu.server.ChatServer;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 14.11.2015.
 */
public class PerfomanceTest {
//    ServerApp server;
//    Chat client;
//
//    @Before
//    public void setUpTest() throws Exception{
//
//    }
//
//    @Test
//    public void timePerfomanceTest() throws Exception{
//        Method startServerMethod = ServerApp.class.getMethod("main",new Class[]{String[].class});
//        startServerMethod.invoke(null, new Object[]{new String[]{}});
//
//        ByteArrayInputStream IN = new ByteArrayInputStream("/chid testUser".getBytes());
//
//        Date date = new Date();
//
//        for (int i=0;i<1000;i++) {
//            System.setIn(IN);
//            Method startClientMethod = ClientApp.class.getMethod("main", new Class[]{String[].class});
//            startClientMethod.invoke(null, new Object[]{new String[]{}});
//        }
//
//        Date date1 = new Date();
//        System.out.println("seconds for invoke 1000 threads:"+
//                System.lineSeparator()+(date1.getTime() - date.getTime())/1000%60);
//    }
}
