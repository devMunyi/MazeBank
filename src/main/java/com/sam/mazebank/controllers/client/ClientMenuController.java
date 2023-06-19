package com.sam.mazebank.controllers.client;

import com.sam.mazebank.models.Model;
import com.sam.mazebank.views.ClientMenuOptions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuController implements Initializable {

    public Button dashboard_btn, transactions_btn, accounts_btn, profile_btn, logout_btn, report_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addCustomListeners();
    }

    private void addCustomListeners(){
        dashboard_btn.setOnAction(event -> onDashboard());
        transactions_btn.setOnAction(event -> onTransactions());
        accounts_btn.setOnAction(event -> onAccounts());
    }

    private void onDashboard(){
        Model.getInstance().getViewFactory().getClientSelectedNavItem().set(ClientMenuOptions.DASHBOARD);
    }

    private void onTransactions(){
        Model.getInstance().getViewFactory().getClientSelectedNavItem().set(ClientMenuOptions.TRANSACTIONS);
    }

    private void onAccounts(){
        Model.getInstance().getViewFactory().getClientSelectedNavItem().set(ClientMenuOptions.ACCOUNTS);
    }
}
