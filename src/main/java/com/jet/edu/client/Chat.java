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

    public Chat() throws ChatException {
        connector = new Connector("127.0.0.1", 6666);
    }

    public void readConsole() throws ChatException {
        while (true) {
            String message;
            BufferedReader readConsole = new BufferedReader(new InputStreamReader(System.in));
            try {
                message = readConsole.readLine();
            } catch (IOException e) {
                throw new ChatException("", e);
            }
            if (checkSizeMessage(message)) {
                managerState(message);
            } else {
                System.out.println("ERROR: Слишком большое сообщение");
            }
        }
    }

    private boolean checkSizeMessage(String message) {
        return message.length() < 150;
    }

    private void managerState(String message) {
        JSONObject jsonObject = new JSONObject();
        String[] mes = message.split(" ");
        switch (mes[0]) {
            case CHID:
                jsonObject.put("cmd", CHID);
                jsonObject.put("msg", message);
                new RegisterState(jsonObject, connector);
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
