package com.jet.edu.client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Yuriy on 12.11.2015.
 */
public class Chat{
    private Connector connector;
    public static final String CHROOM = "/chroom";
    public static final String CHID = "/chid";
    public static final String HIST = "/hist";
    public static final String SND = "/snd";
    private String nameSession = "";
    Scanner scanner = new Scanner(System.in);

    public Chat(String host, int port) throws ChatException {
        connector = new Connector(host, port);
    }

    public void readConsole() throws ChatException, IOException {
        String message;
        while (true) {
            System.out.print(nameSession);
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
        if (messageWithCommand.contains(CHID)) {
            if (checkName(message)) {
                jsonObject.put("cmd", CHID);
                jsonObject.put("msg", message);
                if (new RegisterState(jsonObject, connector).writerToConnector()){
                    nameSession = message;
                }
            } else {
                System.out.println("некорректное имя!");
                System.in.read();
            }
        } else if (messageWithCommand.contains(HIST)) {
            jsonObject.put("cmd", HIST);
       //     jsonObject.put("msg", message);
            new HistoryState(jsonObject, connector).writerToConnector();
        }else if (messageWithCommand.contains(CHROOM)){
            jsonObject.put("cmd", CHROOM);
            new RoomState(jsonObject, connector);
        }
    }
}
