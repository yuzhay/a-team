package com.jet.edu.server;

import org.json.JSONObject;

/**
 * Created by Yuriy on 12.11.2015.
 */
public class ChatServerState implements ServerState {

    private final Storage storage = new ChatStorage();

    public String switchState(String str) {
        JSONObject json = new JSONObject(str);

        JSONObject response = new JSONObject();
        response.put("status", "ok");
        response.put("msg", "User registered");


        if (json.has("cmd")) {
            String cmd = json.getString("cmd");
            String msg = json.getString("msg");
            switch (cmd) {
                case COMMAND_SND:
                    String name = json.getString("name");
                    storage.addMessage(name, msg);
                    response.put("msg", msg);
                    response.put("op", "SEND_TO_OTHERS");
                    return response.toString();
                case COMMAND_CHID:
                    storage.addUser(msg);
                    response.put("msg", "User registered");
                    response.put("op", "SEND_TO_ME");
                    return response.toString();
                case COMMAND_HIST:
                    response.put("op", "SEND_TO_ME");
                    return response.toString();
            }
        }

        return json.toString();
    }
}
