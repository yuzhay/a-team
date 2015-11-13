package com.jet.edu;

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

    private Connector connector;
    private Socket mockSocket;
    private BufferedWriter mockBw;
    private BufferedReader mockBr;
    private InputStream mockInputStream;
    private OutputStream mockOutputStream;
    private JSONObject mockJsonObject;

    @Before
    public void setUpTests() throws Exception{
        mockSocket = Mockito.mock(Socket.class);
        //connector = new Connector(mockSocket);
        mockJsonObject = Mockito.mock(JSONObject.class);
        mockInputStream = Mockito.mock(InputStream.class);
        mockOutputStream = Mockito.mock(OutputStream.class);
        Mockito.when(mockSocket.getOutputStream()).thenReturn(mockOutputStream);
        Mockito.when(mockSocket.getInputStream()).thenReturn(mockInputStream);
    }

    @Test
    public void shouldPrintMessage() throws Exception{
        Connector connector = ObjenesisHelper.newInstance(Connector.class);
        BufferedReader mockBr = Mockito.mock(BufferedReader.class);
        BufferedWriter mockBw = Mockito.mock(BufferedWriter.class);

        Mockito.when(mockJsonObject.toString()).thenReturn("{\"key\":\"value\"}");

//        Mockito.when(mockBw.write(mockJsonObject.toString())).then()
//        Mockito.doNothing().when(mockBw).write(mockJsonObject.toString());
  //      Mockito.doNothing().when(mockBw).flush();
        Mockito.when(mockBr.ready()).thenReturn(true);
        Mockito.when(mockBr.readLine()).thenReturn("TEST STRING");
        Field bwField = Connector.class.getDeclaredField("bw");
        Field brField = Connector.class.getDeclaredField("br");
        brField.setAccessible(true);
        bwField.setAccessible(true);

        brField.set(connector, mockBr);
        bwField.set(connector, mockBw);

        Assert.assertEquals("TEST STRING", connector.sendMessage(new JSONObject().put("key", "value")));
    }

}
