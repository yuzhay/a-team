package com.jet.edu.server;

import org.json.JSONObject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Yuriy on 12.11.2015.
 */
public class ChatServerState implements ServerState {
    public String switchState(String str) {
        JSONObject jsonRequest = new JSONObject(str);

        throw new NotImplementedException();
    }
}
