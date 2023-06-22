package com.sam.mazebank.models;

import java.sql.*;

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
    public ResultSet getClientData(String pAddress, String password){
        Statement statement;
        ResultSet resultSet = null;

        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM PayeeAddress = '"+pAddress+"' AND Password = '"+password+"';");
        }catch (Exception e){

        }
        return resultSet;
    }

    // Admin Section (Methods applying to admin only)


    // Utility Section (methods applying to both client and admin)
}
