package com.jet.edu.server;

import org.json.JSONObject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.Console;

/**
 * Created by Yuriy on 12.11.2015.
 */
public class ChatServerState implements ServerState {

    private final Storage storage = new ChatStorage();

    public String switchState(String str) {
        JSONObject json = new JSONObject(str);

        JSONObject response = new JSONObject();
        response.put("status","ok");
        response.put("msg","User registered");

        if (json.has("cmd")) {
            String cmd = json.getString("cmd");
            String msg = json.getString("msg");

            switch (cmd) {
                case COMMAND_SND:

                    break;
                case COMMAND_CHID:
                    storage.addUser(msg);
                    return response.toString();
                case COMMAND_HIST:
                    break;
            }
        }

        return json.toString();
    }
}
