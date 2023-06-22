package com.sam.mazebank.models;

import com.sam.mazebank.views.ViewFactory;

public class Model {

    // utility section
    private static Model model;
    private final ViewFactory viewFactory;
    private final DatabaseDriver databaseDriver;

    // Client section
    private Client client;
    private boolean isClientLoggedIn;


    private Model(){
        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();

        // client Section
        this.isClientLoggedIn = false;
        this.client = new Client("", "", "", null, null, null);
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

    public boolean isClientLoggedIn() {
        return isClientLoggedIn;
    }
}
