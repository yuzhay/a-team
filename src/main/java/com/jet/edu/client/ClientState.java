package com.jet.edu.client;

/**
 * Created by Yuriy on 12.11.2015.
 */
public interface ClientState {
    void stateCmd(String str) throws ChatException;
}
