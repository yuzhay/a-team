package com.jet.edu;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * A-Team project
 */
public class App {

//    private static final Logger logger = Logger.getLogger("myLogger");
    public static void main(String[] args) {
//        System.out.println("Hello World!");
//        try {
//            Connection c = DriverManager.getConnection("jdbc:derby://10.11.103.27:1527/chat");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        //logger.log(Level.INFO,"zxc");
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("ID",1);
        jsonObject.put("NAME","TEST");

        jsonArray.put(jsonObject);
        jsonArray.put(jsonObject);
        jsonArray.put(jsonObject);
        Iterator<Object> it = jsonArray.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }
        System.out.println(jsonArray);


//        try {
//            FileHandler handler = new FileHandler("D://test.txt");
//            logger.addHandler(handler);
//            SimpleFormatter simpleFormatter = new SimpleFormatter();
//            logger.info("asdzxc");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        logger.warning("qwe");
        try {
            ChatLogger chatLogger = new ChatLogger("C://test.txt");
            chatLogger.printInfo("zxc");
        }catch (IOException e){

        }
    }
}
