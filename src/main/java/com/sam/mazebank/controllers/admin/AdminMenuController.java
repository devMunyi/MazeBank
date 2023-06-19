package com.sam.mazebank.controllers.admin;

import com.sam.mazebank.models.Model;
import com.sam.mazebank.views.AdminMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    }

    private void onCreateClient(){
        Model.getInstance().getViewFactory().getAdminSelectedNavItem().set(AdminMenuOptions.CREATE_CLIENT);
    }

    private void onClients(){
        Model.getInstance().getViewFactory().getAdminSelectedNavItem().set(AdminMenuOptions.CLIENTS);
    }

}
