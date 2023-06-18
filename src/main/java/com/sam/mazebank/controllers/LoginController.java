package com.sam.mazebank.controllers;

import com.sam.mazebank.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public ChoiceBox acc_selector;
    public Label payee_address_lbl, error_lbl;
    public TextField password_fld;
    public Button login_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_btn.setOnAction(event -> onLogin());
    }

    private void onLogin(){
        Stage stage = (Stage)login_btn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStageWithoutAlert(stage);
        Model.getInstance().getViewFactory().showClientWindow();
    }
}
