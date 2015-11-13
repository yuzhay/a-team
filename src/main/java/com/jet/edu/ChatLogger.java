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
    private FileHandler handler;

    /**
     * Constructor
     * @param filepath - path to file
     * @throws IOException - exception if something wrong whith file
     */
    public ChatLogger(String filepath){
        try{
            this.handler = new FileHandler(filepath);
            logger.setUseParentHandlers(false);
            logger.addHandler(handler);
        } catch (IOException e){
            this.handler = null;
            this.logger.log(Level.WARNING,"ERROR WITH FILE! LOGS WILL BE WRITE INTO CONSOLE", e);
        }
    }

    /**
     * print INFO message
     * @param message - message
     */
    public void printInfo(String message, Throwable e){
        logger.log(Level.INFO,message + System.lineSeparator()+ e);
    }

    /**
     * prints WARNING message
     * @param message - message
     */
    public void printWarning(String message,  Throwable e){
        logger.log(Level.WARNING,message+ System.lineSeparator()+ e);
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
    public void printSevere(String message, Throwable e){
        logger.log(Level.SEVERE,message + System.lineSeparator()+ e);
    }

    public void printConsole(String message){
        System.out.print(message);
    }

}
