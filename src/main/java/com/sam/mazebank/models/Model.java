package com.sam.mazebank.models;

import com.sam.mazebank.views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.time.LocalDate;

public class Model {
    // utility section
    private static Model model;
    private final ViewFactory viewFactory;
    private final DatabaseDriver databaseDriver;

    // Client section
    private Client client;
    private boolean isClientLoggedIn;

    // Admin Section
    private boolean isAdminLoggedIn;
    private final ObservableList<Client> clients;


    private Model(){
        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();

        // client Section
        this.isClientLoggedIn = false;
        this.client = new Client("", "", "", null, null, null);
        this.clients = FXCollections.observableArrayList();
    }

    public static synchronized Model getInstance(){
        if(model == null){
            model = new Model();
        }
        return model;
    }

    public ViewFactory getViewFactory(){
        return viewFactory;
    }

    public DatabaseDriver getDatabaseDriver() {
        return databaseDriver;
    }


    // client section

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isClientLoggedIn() {
        return isClientLoggedIn;
    }

    public void setClientLoggedIn(boolean clientLoggedIn) {
        isClientLoggedIn = clientLoggedIn;
    }

    public void evaluateClientCred(String pAddress, String password){
        CheckingAccount checkingAccount;
        SavingsAccount savingsAccount;
        // DatabaseDriver databaseDriver1 = new DatabaseDriver();
        ResultSet resultSet = databaseDriver.getClientData(pAddress, password);

        try {
            if(resultSet != null && resultSet.isBeforeFirst()){
                this.client.firstNameProperty().set(resultSet.getString("FirstName"));
                this.client.lastNameProperty().set(resultSet.getString("LastName"));
                this.client.payeeAddressProperty().set(resultSet.getString("PayeeAddress"));
                String[] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                this.client.dateCreatedProperty().set(date);

                // set isClientLoggedIn flag to true
                this.isClientLoggedIn = true;
            }else {
                this.isClientLoggedIn = false;
            }

        }catch (Exception e){
            System.out.println("Error occurred in evaluateClientCred: "+e);
        }finally {
            // Model.getInstance().getDatabaseDriver().closeConn2(databaseDriver1.getConn());
        }

    }

    public boolean isAdminLoggedIn() {
        return isAdminLoggedIn;
    }

    public void setAdminLoggedIn(boolean adminLoggedIn) {
        isAdminLoggedIn = adminLoggedIn;
    }

    public void evaluateAdminCred(String username, String password){
        // DatabaseDriver databaseDriver1 = new DatabaseDriver();
        ResultSet resultSet = databaseDriver.getAdminData(username, password);

        try {
            if(resultSet != null && resultSet.isBeforeFirst()){
                this.isAdminLoggedIn = true;
            }else {
                this.isAdminLoggedIn = false;
            }
        }catch (Exception e){
            System.out.println("Error occurred in evaluateAdminCred: "+e);
        }finally {
            // Model.getInstance().getDatabaseDriver().closeConn2(databaseDriver1.getConn());
        }
    }

    public ObservableList<Client> getClients() {
        return clients;
    }

    public void setClients(){
        CheckingAccount checkingAccount = null;
        SavingsAccount savingsAccount = null;

        ResultSet resultSet = databaseDriver.getAllClientsData();
        try {
            while (resultSet.next()){
                String fName = resultSet.getString("FirstName");
                String lName = resultSet.getString("LastName");
                String pAddress = resultSet.getString("PayeeAddress");
                String[] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                checkingAccount = getCheckingAccount(pAddress);
                savingsAccount = getSavingsAccount(pAddress);
                clients.add(new Client(fName, lName, pAddress, checkingAccount, savingsAccount, date));
            }
        }catch (Exception e){
            System.out.println("Error Occurred in setClients: "+e);
        }
    }


    /*
    Utility methods
     */
    public CheckingAccount getCheckingAccount(String pAddress){
     CheckingAccount checkingAccount = null;
     ResultSet resultSet = databaseDriver.getCheckingAccount(pAddress);

     try {
         while(resultSet.next()){
             String num = resultSet.getString("AccountNumber");
             int tLimit = (int) resultSet.getDouble("TransactionLimit");
             double accBalance = resultSet.getDouble("Balance");
             checkingAccount = new CheckingAccount(pAddress, num, accBalance, tLimit);
         }

     }catch (Exception e){
         System.out.println("Error occurred in the model getCheckingAccount "+e);
     }
     return checkingAccount;
    }

    public SavingsAccount getSavingsAccount(String pAddress){
        SavingsAccount savingsAccount = null;
        ResultSet resultSet = databaseDriver.getSavingsAccount(pAddress);

        try {
            while (resultSet.next()){
                String num = resultSet.getString("AccountNumber");
                double wLimit = resultSet.getDouble("WithdrawalLimit");
                double accBalance = resultSet.getDouble("Balance");

                savingsAccount = new SavingsAccount(pAddress, num, accBalance, wLimit);
            }
        }catch (Exception e){
            System.out.println("Error occurred in the model getSavingsAccount "+e);
        }
        return savingsAccount;
    }
}
