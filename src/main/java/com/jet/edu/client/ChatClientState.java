package com.jet.edu.client;

import org.json.JSONObject;

/**
 * Created by Yuriy on 12.11.2015.
 */
public class ChatClientState implements ClientState {

    public static final String CHID = "/chid";
    public static final String HIST = "/hist";
    public static final String SND = "/snd";
    private Connector client;

    public ChatClientState() throws ChatException {
        client = new Connector("127.0.0.1", 666);
    }

    public void stateCmd(String message) throws ChatException {
        JSONObject jsonObject =  new JSONObject();
        String[] mes = message.split(" ");
        switch (mes[0]) {
            case CHID:
                jsonObject.put("cmd", CHID);
                jsonObject.put("msg",message);
                client.sendMessage(jsonObject);
                break;
            case HIST:
                jsonObject.put("cmd",HIST);
                jsonObject.put("msg",message);
                break;
            case SND:
                jsonObject.put("cmd",SND);
                jsonObject.put("msg",message);
                break;
            default:
        }
    }
}
