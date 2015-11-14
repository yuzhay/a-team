package com.jet.edu.client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Scanner;

/**
 * Class Chat read from console
 * and send readed messages
 */
public class Chat {
    private final Connector connector;
    private final Factory factory;
    public static final String CHID = "/chid";
    public static final String HIST = "/hist";
    public static final String SND = "/snd";

    private final Scanner scanner = new Scanner(System.in);
    private String userName;

    /**
     *Constructor Chat
     * initialize factory and Connector
     *
     * @param factory
     * @param connector
     * @throws ChatException
     */
    public Chat(Factory factory, Connector connector) throws ChatException {
        this.factory = factory;
        this.connector = connector;
    }

    /**
     * read from console messages
     *
     * @throws ChatException
     * @throws IOException
     */
    public void readConsole() throws ChatException {
        String message;
        while (scanner.hasNext()) {
            message = scanner.nextLine();
            managerState(message);
        }
    }

    private boolean checkSizeMessage(String message) {
        return message.length() < 150;
    }

    private boolean checkName(String name) {
        return (name.length() > 0 && name.length() < 50 && !name.contains(" "));
    }

    private void managerState(String messageWithCommand) throws ChatException {
        String message = messageWithCommand.substring(messageWithCommand.indexOf(" ") + 1);

        if (messageWithCommand.trim().equals(SND)) {
            System.out.println("некорректное сообщение!");
            return;
        }
        JSONObject jsonObject = new JSONObject();
        if (messageWithCommand.startsWith(CHID)) {
            if (checkName(message)) {
                jsonObject.put("cmd", CHID);
                jsonObject.put("msg", message);
                if (!checkName(message)) {
                    System.out.println("некорректное имя!");
                    return;
                }
                factory.setRegisterState(jsonObject, connector);
                if (!factory.getRegisterState().writerToConnector()) {
                    System.out.println("Введите имя заново");
                } else {
                    userName = message;
                }
            } else {
                System.out.println("некорректное имя!");
            }
        } else if (messageWithCommand.startsWith(HIST)) {
            jsonObject.put("cmd", HIST);
            factory.setHistoryState(jsonObject, connector);
            factory.getHistoryState().writerToConnector();
        } else {
            if (!checkSizeMessage(message)) {
                System.out.println("некорректное сообщение!");
                return;
            }
            jsonObject.put("cmd", SND);
            jsonObject.put("msg", message);
            jsonObject.put("name", userName);
            factory.setSendState(jsonObject, connector);
            factory.getSendState().writeToConnector();
        }
    }
}