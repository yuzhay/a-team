package com.jet.edu.client;

import org.json.JSONObject;
import com.jet.edu.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by Yuriy on 12.11.2015.
 */
public class Chat implements State {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private Connector connector;
    Scanner scanner = new Scanner(System.in);

    public Chat(String host, int port) throws ChatException {
        connector = new Connector(host, port);
    }

    public void readConsole() throws ChatException, IOException {
        String message;
        while (reader.readLine()!= null) {
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

    private void managerState(String message) throws ChatException, IOException {
        JSONObject jsonObject = new JSONObject();
        String[] mes = message.split(" ");
        message = message.substring(message.indexOf(" ") + 1, message.length());
        switch (mes[0]) {
            case Constants.COMMAND_CHID:
                if (checkName(message)  && message != mes[0]) {
                    jsonObject.put(Constants.CMD, Constants.COMMAND_CHID);
                    jsonObject.put(Constants.MSG, message);
                    new RegisterState(jsonObject, connector).writerToConnector();

                } else{
                    System.out.println(Constants.WRONG_NAME);
                }
                break;
            case Constants.COMMAND_HIST:
                jsonObject.put(Constants.CMD, Constants.COMMAND_HIST);
                jsonObject.put(Constants.MSG, message);
                //new HistoryState(jsonObject);
                break;
            case Constants.COMMAND_SND:
                if (checkSizeMessage(message) && message != mes[0]) {
                    jsonObject.put(Constants.CMD, Constants.COMMAND_SND);
                    jsonObject.put(Constants.MSG, message);
                    new SendState(jsonObject, connector).writerToConnector();
                } else{
                    System.out.println(Constants.WRONG_MESSAGE);
                }
                break;
            default:
                System.out.println("\n"+Constants.WRONG_COMMAND);
                break;
        }
    }
}
