package com.jet.edu;

import com.jet.edu.client.*;
import org.fest.assertions.Assertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisException;
import org.objenesis.ObjenesisHelper;
import org.objenesis.ObjenesisStd;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Yuriy on 13.11.2015.
 */
public class ChatClientTest {
    private Chat chatClient;
    private Factory mockFactory;
    private Connector mockConnector;
    private RegisterState mockRegisterState;
    private int port;
    private Random rnd = new Random();
    ByteArrayOutputStream OUT;
    @Before
    public void before() throws ChatException{
        OUT = new ByteArrayOutputStream();
        port = rnd.nextInt(64000) + 1000;
        mockFactory = Mockito.mock(Factory.class);
        mockConnector = Mockito.mock(Connector.class);
        chatClient = new Chat(mockFactory,mockConnector);
        mockRegisterState = Mockito.mock(RegisterState.class);
        System.setOut(new PrintStream(OUT));
    }

    @After
    public void after() {
        OUT.reset();

    }

    @Test
    public void shouldReadCmdsFromConsole() throws Exception{
        ByteArrayInputStream IN = new ByteArrayInputStream("/chid w r o n g name".getBytes());
        System.setIn(IN);
        Scanner scanner = new Scanner(System.in);

        Field scannerField = Chat.class.getDeclaredField("scanner");
        scannerField.setAccessible(true);
        scannerField.set(chatClient,scanner);

        chatClient.readConsole();

        Assert.assertEquals("некорректное имя!"+System.lineSeparator(),OUT.toString());
        IN.reset();
    }

    @Test
    public void shouldRegisterNames() throws Exception{
        ByteArrayInputStream IN = new ByteArrayInputStream("/chid testUser".getBytes());
        System.setIn(IN);
        Scanner scanner = new Scanner(System.in);

        Field scannerField = Chat.class.getDeclaredField("scanner");
        Field nameField = Chat.class.getDeclaredField("userName");
        nameField.setAccessible(true);
        scannerField.setAccessible(true);
        scannerField.set(chatClient,scanner);

        Mockito.when(mockFactory.getRegisterState()).thenReturn(mockRegisterState);
        Mockito.when(mockRegisterState.writerToConnector()).thenReturn(true);

        chatClient.readConsole();

        Assert.assertEquals(nameField.get(chatClient),"testUser");
        IN.reset();
    }
}
