package com.jet.edu.client;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Yuriy on 12.11.2015.
 */
public class Chat implements State {
    private State state = null;
    private Connector connector;
    public static final String CHID = "/chid";
    public static final String HIST = "/hist";
    public static final String SND = "/snd";

    public Chat(String host, int port) throws ChatException {
        connector = new Connector(host, port);
    }

    public void readConsole() throws ChatException, IOException {
        String message;
        BufferedReader readConsole = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            message = readConsole.readLine();
            managerState(message);
        }
    }

    private boolean checkSizeMessage(String message) {
        return message.length() < 150;
    }

    private boolean checkName(String name) {
        return (name.length() < 50 && !name.contains(" "));
    }

    private void managerState(String message) throws ChatException, IOException {
        JSONObject jsonObject = new JSONObject();
        String[] mes = message.split(" ");
        message = message.substring(message.indexOf(" ") + 1, message.length());
        switch (mes[0]) {
            case CHID:
                if (checkName(message)) {
                    jsonObject.put("cmd", CHID);
                    jsonObject.put("msg", message);
                    new RegisterState(jsonObject, connector).writerToConector();

                }else{
                    System.out.println("некорректное имя!");
                    System.in.read();
                }
                break;
            case HIST:
                jsonObject.put("cmd", HIST);
                jsonObject.put("msg", message);
                //new HistoryState(jsonObject);
                break;
            case SND:
                jsonObject.put("cmd", SND);
                jsonObject.put("msg", message);
                //new SendState(jsonObject);
                break;
            default:
        }
    }
}
