package com.jet.edu.server;

/**
 * Chat Server Model Interface
 * Created by Yuriy on 12.11.2015.
 */
public interface ServerModel {
    String COMMAND_HIST = "/hist";
    String COMMAND_SND = "/snd";
    String COMMAND_CHID = "/chid";
    String COMMAND_ROOM = "/room";

    void execute(String str, ClientIO client);
}
