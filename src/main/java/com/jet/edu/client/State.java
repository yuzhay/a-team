package com.jet.edu.client;

/**
 * State for different commands
 */
public interface State {
    default void println(String message) {
        System.out.println(message);
    }
}
