package com.sam.mazebank.controllers.client;

import com.sam.mazebank.models.Model;
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
    }

    private void onDashboard(){
        Model.getInstance().getViewFactory().getClientSelectedNavItem().set("Dashboard");
    }

    private void onTransactions(){
        Model.getInstance().getViewFactory().getClientSelectedNavItem().set("Transactions");
    }
}
