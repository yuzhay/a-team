package com.jet.edu;

import com.jet.edu.client.ChatException;
import com.jet.edu.client.Connector;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.objenesis.ObjenesisBase;
import org.objenesis.ObjenesisHelper;

import java.io.*;
import java.lang.reflect.Field;
import java.net.Socket;

/**
 * Created by user on 13.11.2015.
 */
public class ConnectorTest {

    private BufferedReader mockBr;
    private BufferedWriter mockBw;
    private Field brField;
    private Field bwField;

    @Before
    public void setUpTests() throws Exception{
        mockBr = Mockito.mock(BufferedReader.class);
        mockBw = Mockito.mock(BufferedWriter.class);
        brField = Connector.class.getDeclaredField("br");
        bwField = Connector.class.getDeclaredField("bw");
        brField.setAccessible(true);
        bwField.setAccessible(true);
    }

    @Test
    public void shouldPrintMessage() throws Exception{
        Connector connector = ObjenesisHelper.newInstance(Connector.class);

        Mockito.when(mockBr.ready()).thenReturn(true);
        Mockito.when(mockBr.readLine()).thenReturn("TEST STRING");


        brField.set(connector, mockBr);
        bwField.set(connector, mockBw);

        Assert.assertEquals("TEST STRING", connector.sendMessage(new JSONObject().put("key", "value")));
    }

    @Test (expected = ChatException.class)
    public void shouldThrowChatException() throws Exception{
        Connector connector = ObjenesisHelper.newInstance(Connector.class);

        Mockito.when(mockBr.ready()).thenReturn(true);
        Mockito.when(mockBr.readLine()).thenThrow(ChatException.class);

        brField.set(connector, mockBr);
        bwField.set(connector, mockBw);

        connector.sendMessage(new JSONObject().put("key","value"));
    }

}
