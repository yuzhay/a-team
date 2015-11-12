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

        if (json.has("cmd")){
            String cmd = json.getString("cmd");
        }


        System.out.println(json);
        return json.toString();
    }
}
