package com.jet.edu.server;

/**
 * Created by Yuriy on 12.11.2015.
 */
public interface ServerState {
    String COMMAND_HIST = "/hist";
    String COMMAND_SND = "/snd";
    String COMMAND_CHID = "/chid";

    void switchState(String str, ClientIO client);
}
