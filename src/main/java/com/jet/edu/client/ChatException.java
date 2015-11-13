package com.jet.edu.client;

import java.io.IOException;

/**
 * ChatException catch exceptions
 */
public class ChatException extends Exception {
    /**
     * catch chatExceptions
     * @param message
     * @param cause
     */
    public ChatException(String message, Throwable cause) {
        super(message, cause);
    }

}
