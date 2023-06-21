package com.sam.mazebank.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseDriver {

    private Connection conn;

    public DatabaseDriver(){
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:mazebank.db");
        }catch (SQLException e){
            System.out.println(e);
        }catch (Exception e2){
            System.out.println(e2);
        }
    }


    // Client Section (Methods applying to client only)


    // Admin Section (Methods applying to admin only)


    // Utility Section (methods applying to both client and admin)
}
