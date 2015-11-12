package com.jet.edu.client;

/**
 * Created by Yuriy on 12.11.2015.
 */
public interface Client {
    void connect(String host, int port);
    void send(String msg);
}
