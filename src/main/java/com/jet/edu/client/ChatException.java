package com.jet.edu.client;

import java.io.IOException;

/**
 * Created by Павел on 12.11.2015.
 */
public class ChatException extends Exception {
    public ChatException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChatException(IOException e) {

    }
}
