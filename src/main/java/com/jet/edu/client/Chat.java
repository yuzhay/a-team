package com.jet.edu.client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Yuriy on 12.11.2015.
 */
public class Chat{
    private Connector connector;
    private Factory factory;
    public static final String CHROOM = "/chroom";
    public static final String CHID = "/chid";
    public static final String HIST = "/hist";
    public static final String SND = "/snd";

    Scanner scanner = new Scanner(System.in);

    public Chat(Factory factory, Connector connector) throws ChatException {
        this.factory = factory;
        this.connector = connector;
    }

    public void readConsole() throws ChatException, IOException {
        String message;
        while (true) {
            message = scanner.nextLine();
            managerState(message);
        }
    }

    private boolean checkSizeMessage(String message) {
        return message.length() < 150;
    }

    private boolean checkName(String name) {
        return (name.length() < 50 && !name.contains(" "));
    }

    private void managerState(String messageWithCommand) throws ChatException, IOException {
        JSONObject jsonObject = new JSONObject();
        String message = messageWithCommand.substring(messageWithCommand.indexOf(" ") + 1);
        if (messageWithCommand.startsWith(CHID)) {
            if (checkName(message)) {
                jsonObject.put("cmd", CHID);
                jsonObject.put("msg", message);
                factory.setRegisterState(jsonObject, connector);
                factory.getRegisterState().writerToConnector();
            } else {
                System.out.println("некорректное имя!");
                System.in.read();
            }
        } else if (messageWithCommand.startsWith(HIST)) {
            jsonObject.put("cmd", HIST);
            factory.setHistoryState(jsonObject, connector);
            factory.getHistoryState().writerToConnector();
        }else if (messageWithCommand.contains(CHROOM)){
            jsonObject.put("cmd", CHROOM);
        } else {
            jsonObject.put("cmd", SND);
            jsonObject.put("msg", messageWithCommand);
            factory.setSendState(jsonObject,connector);
            factory.getSendState().writeToConnector();
        }
    }
}
