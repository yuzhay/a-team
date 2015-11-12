package com.jet.edu.server;

import org.json.JSONObject;

/**
 * Created by Yuriy on 12.11.2015.
 */
public class ChatServerState implements ServerState {

    private final Storage storage = new ChatStorage();

    public String switchState(String str) {
        JSONObject json = new JSONObject(str);

        if (json.has("cmd")) {
            String cmd = json.getString("cmd");
            String msg = json.getString("msg");
            switch (cmd) {
                case COMMAND_SND:
                    break;
                case COMMAND_CHID:
                    storage.addUser(msg);
                    break;
                case COMMAND_HIST:
                    break;
            }
        }


        System.out.println(json);
        return json.toString();
    }
}
