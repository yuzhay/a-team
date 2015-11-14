package com.jet.edu.client;

import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Class Chat read from console
 * and send readed messages
 */
public class Chat implements State{
    //region fields
    private final Connector connector;
    private final Factory factory;   
    private Scanner scanner = new Scanner(System.in);
    private String userName;
    private Socket socket;
    //endregion

    /**
     * initialize port and host
     * and connection
     * @param factory
     * @param socket
     * @throws ChatException
     */
    public Chat(Factory factory, Socket socket) throws ChatException {
        this.factory = factory;
        this.socket = socket;
        this.connector = new Connector(socket);
    }
    //endregion

    //region methods
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

    private boolean checkNameSize(String name) {
        return name.length() < 50;
    }

    private boolean checkNameSpace(String name) {
        return !name.contains(" ");
    }

    private void managerState(String messageWithCommand) throws ChatException {
        JSONObject jsonObject = new JSONObject();
        String message = messageWithCommand.substring(messageWithCommand.indexOf(" ") + 1);

        if (messageWithCommand.trim().equals(SND)) {
            System.out.println("Некорректное сообщение");
            return;
        }

        if (messageWithCommand.startsWith(CHID) && checkName(message)) {
                jsonObject.put("cmd", CHID);
                jsonObject.put("msg", message);
            
                factory.setRegisterState(jsonObject, connector);
                if (!factory.getRegisterState().writerToConnector()) {
                    System.out.println("Имя уже занято. Повторите попытку ввода!");
                } else {
                    userName = message;
                }

        } else if (messageWithCommand.startsWith(HIST)) {
            jsonObject.put("cmd", HIST);
            factory.setHistoryState(jsonObject, connector);
            factory.getHistoryState().writerToConnector();
        } else {
            if (!checkSizeMessage(message)) {
                System.out.println("Сообщение больше 150 символов! Повторите попытку.");
                return;
            }
            jsonObject.put("cmd", SND);
            jsonObject.put("msg", message);
            jsonObject.put("name", userName);
            factory.setSendState(jsonObject, connector);
            factory.getSendState().writeToConnector();
        }
    }

    private void ListenerServer(){
        Thread thread = new Thread(new Listener(socket));
        thread.start();
    }

    private boolean checkName(String name){
        if (!checkNameSize(name)) {
            System.out.println("Длина имени больше 50 символов");
            return false;
        }else if (!checkNameSpace(name)){
            System.out.println("Имя содержит пробелы!");
            return false;
        }
        return true;
    }
    //endregion
}