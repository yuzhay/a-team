package com.acme.edu;

import com.jet.edu.server.ChatStorage;
import org.junit.Test;


import java.sql.SQLException;

/**
 * Created by Yuriy on 12.11.2015.
 */

public class StorageTest {

    @Test
    public void testUser(){
        ChatStorage storage = new ChatStorage();
        try {
            storage.connect();
            storage.addUser("Admin");
            storage.disconnet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
