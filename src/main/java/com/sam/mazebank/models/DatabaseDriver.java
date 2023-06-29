package com.sam.mazebank.models;

import java.sql.*;
import java.time.LocalDate;

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

    private Connection connect() {
        String url = "jdbc:sqlite:mazebank.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Error occurred in connect method: "+e.getMessage());
        }
        return conn;
    }

    // Client Section (Methods applying to client only)
    public ResultSet getClientData(String pAddress, String password){
        // Connection conn = this.connect();
        ResultSet resultSet = null;
        Statement statement;

        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Clients WHERE PayeeAddress = '"+pAddress+"' AND Password = '"+password+"';");
        }catch (Exception e){
            System.out.println("Error occurred in getClientData: "+e);
        }finally {
            // closeConn(conn);
        }

        return resultSet;
    }

    public ResultSet getAllClientsData(){
        ResultSet resultSet = null;
        Statement statement;

        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Clients");
        }catch (SQLException e){
            System.out.println("Error occurred in getAllClientsData: "+ e);
        }

        return resultSet;
    }

    public ResultSet getTransactions(String pAddress, int rLimit){

        ResultSet resultSet = null;
        Statement statement;

        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Transactions WHERE Sender = '"+pAddress+"' OR Receiver = '"+pAddress+"' ORDER BY ID DESC LIMIT "+rLimit+";");
        }catch (SQLException e){
            System.out.println("Error Occurred in getTransactions: "+ e);
            e.printStackTrace();
        }

        return  resultSet;

    }

    // Admin Section (Methods applying to admin only)
    public ResultSet getAdminData(String username, String password){
        // Connection conn = this.connect();
        ResultSet resultSet = null;
        Statement statement;

        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Admins WHERE username = '"+ username +"' AND password = '"+ password +"';");
        }catch (Exception e){
            System.out.println("Error occurred in getAdminData: "+e);
        }finally {
            // closeConn(conn);
        }
        return resultSet;
    }

    public void createClient(String fName, String lName, String pAddress, String pass, LocalDate date){
        // Connection conn = this.connect();
        Statement statement;

        try {
            statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO CLIENTS (FirstName, LastName, PayeeAddress, Password, Date) VALUES ('"+fName+"', '"+lName+"', '"+pAddress+"', '"+pass+"', '"+date+"');");
        }catch (Exception e) {
            System.out.println("ERROR on creating client: " +e);
        }finally {
            // closeConn2(conn);
        }
    }

    public void createCheckingAccount(String owner, String accountNumber, int tLimit, double balance){
        // Connection conn = this.connect();
        Statement statement;
        try {
            statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO CheckingAccounts(Owner, AccountNumber, TransactionLimit, Balance) VALUES('"+owner+"', '"+accountNumber+"', "+tLimit+", "+balance+");");
        }catch (Exception e){
            System.out.println("ERROR on creating checking account: " + e);
        }finally {
            // closeConn2(conn);
        }
    }

    public void createSavingsAccount(String pAddress, String accountNumber, double wLimit, double balance){
        // Connection conn = this.connect();
        Statement statement;

        try {
            statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO SavingsAccounts(Owner, AccountNumber, WithdrawalLimit, Balance) VALUES('"+pAddress+"', '"+accountNumber+"', "+wLimit+", "+balance+");");
        }catch (SQLException e){
            System.out.println("ERROR on creating savings account: "+e);
        }finally {
            // closeConn2(conn);
        }
    }

    public ResultSet searchClient(String pAddress){
        ResultSet resultSet = null;
        Statement statement;

        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Clients WHERE PayeeAddress = '"+pAddress+"';");
        }catch (SQLException e){
            System.out.println("Error occurred in searchClient "+e.getMessage());
            e.printStackTrace();
        }

        return resultSet;
    }

    public boolean depositSavings(String pAddress, double amount){
        Statement statement;
        boolean okay = false;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("UPDATE SavingsAccounts SET Balance = "+amount+" WHERE Owner = '"+pAddress+"';");
            okay = true;
        }catch (SQLException e){
            System.out.println("Error occurred in depositSavings: "+ e.getMessage());
        }

        return okay;
    }


    // Utility Section (methods applying to both client and admin)

    public void closeConn() {
        try { if (conn != null) conn.close(); } catch (Exception e) {
            System.out.println("Error occurred in closing conn: "+e);
        };
    }

    public void closeConn2(Connection conn) {
        try { if (conn != null) conn.close(); } catch (Exception e) {
            System.out.println("Error occurred in closing conn: "+e);
        };
    }
    public int getLastClientsId(){
        // Connection conn = this.connect();
        Statement statement;
        ResultSet resultSet;
        int id = 0;

        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT seq FROM sqlite_sequence WHERE name = 'Clients';");
            id = resultSet.getInt("seq");
        }catch (SQLException e){
            System.out.println("Error occurred in getLastClientsId: "+e);
        }finally {
            // closeConn2(conn);
        }
        return id;
    }

    public Connection getConn() {
        return conn;
    }

    public ResultSet getCheckingAccount(String pAddress){
        Statement statement;
        ResultSet resultSet = null;

        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM CheckingAccounts WHERE Owner = '"+pAddress+"';");
        }catch (SQLException e){
            System.out.println("Error occurred in the dbDriver getCheckingAccount: "+e);
        }

        return  resultSet;
    }

    public ResultSet getSavingsAccount(String pAddress){
        Statement statement;
        ResultSet resultSet = null;

        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM SavingsAccounts WHERE Owner = '"+pAddress+"';");
        }catch (SQLException e){
            System.out.println("Error occurred in the dbDriver getCheckingAccount: "+e);
        }

        return  resultSet;
    }
}
