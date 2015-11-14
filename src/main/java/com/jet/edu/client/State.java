package com.jet.edu.client;

/**
 * State for different commands
 */
public interface State {
    /**
     * Print message and flush buffer
     * @param message parameter to print
     */
    default void println(String message) {
        System.out.println(message);
        System.out.flush();
    }
}
