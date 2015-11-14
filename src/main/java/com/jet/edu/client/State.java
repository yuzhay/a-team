package com.jet.edu.client;

/**
 * State for different commands
 */
public interface State {
    //region fields
    String CHROOM = "/chroom";
    String CHID = "/chid";
    String HIST = "/hist";
    String SND = "/snd";
    String OK = "ok";
    //endregion

    default void println(String message) {
        System.out.println(message);
    }
}
