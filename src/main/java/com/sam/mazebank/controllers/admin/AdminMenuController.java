package com.sam.mazebank.controllers.admin;

import com.sam.mazebank.models.Model;
import com.sam.mazebank.views.AdminMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    public Button create_client_btn, clients_btn, deposit_btn, logout_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners(){
        create_client_btn.setOnAction(event -> onCreateClient());
        clients_btn.setOnAction(event -> onClients());
        deposit_btn.setOnAction(event -> onDeposit());
        logout_btn.setOnAction(event -> onLogout());
    }

    private void onCreateClient(){
        Model.getInstance().getViewFactory().getAdminSelectedNavItem().set(AdminMenuOptions.CREATE_CLIENT);
    }

    private void onClients(){
        Model.getInstance().getViewFactory().getAdminSelectedNavItem().set(AdminMenuOptions.CLIENTS);
    }

    private void onDeposit(){
        Model.getInstance().getViewFactory().getAdminSelectedNavItem().set(AdminMenuOptions.DEPOSIT);
    }

    private void onLogout(){
        Stage stage = (Stage) clients_btn.getScene().getWindow();
        Model.getInstance().setAdminLoggedIn(false);
        Model.getInstance().getViewFactory().closeStageWithoutAlert(stage);
        Model.getInstance().getViewFactory().showLoginWindow();
    }

}
