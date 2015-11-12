package com.jet.edu;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Logger which prints to file
 */
public class ChatLogger {
    private Logger logger = Logger.getLogger("System");
    private String filepath;

    /**
     * Constructor
     * @param filepath - path to file
     * @throws IOException - exception if something wrong whith file
     */
    public ChatLogger(String filepath) throws IOException {
        this.filepath = filepath;
        FileHandler fileHandler = new FileHandler();
        logger.setUseParentHandlers(false);
        logger.addHandler(fileHandler);
    }

    /**
     * print INFO message
     * @param message - message
     */
    public void printInfo(String message){
        logger.log(Level.INFO,message);
    }

    /**
     * prints WARNING message
     * @param message - message
     */
    public void printWarning(String message){
        logger.log(Level.WARNING,message);
    }

    /**
     * prints SERVE message
     * @param message - message
     */
    public void printSevere(String message){
        logger.log(Level.SEVERE,message);
    }

}
