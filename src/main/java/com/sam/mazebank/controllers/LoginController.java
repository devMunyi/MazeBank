package com.sam.mazebank.controllers;

import com.sam.mazebank.models.Model;
// import com.sam.mazebank.views.AccountType;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public ChoiceBox acc_selector;
    public Label payee_address_lbl, error_lbl;
    public TextField password_fld;
    public Button login_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_btn.setOnAction(event -> Model.getInstance().getViewFactory().showClientWindow());
        // acc_selector.getItems().addAll(AccountType.CLIENT, AccountType.ADMIN);
    }
}
