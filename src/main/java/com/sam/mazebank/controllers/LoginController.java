package com.sam.mazebank.controllers;

import com.sam.mazebank.models.Model;
import com.sam.mazebank.views.AccountType;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public ChoiceBox<AccountType> acc_selector;
    public Label payee_address_lbl, error_lbl;
    public TextField password_fld;
    public Button login_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        acc_selector.setItems(FXCollections.observableArrayList(AccountType.CLIENT, AccountType.ADMIN));
        acc_selector.setValue(Model.getInstance().getViewFactory().getAccountType());
        acc_selector.valueProperty().addListener(observable -> Model.getInstance().getViewFactory().setAccountType(acc_selector.getValue()));
        login_btn.setOnAction(event -> onLogin());
    }

    private void onLogin(){
        Stage stage = (Stage)login_btn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStageWithoutAlert(stage);

        if(Model.getInstance().getViewFactory().getAccountType() == AccountType.ADMIN){
            Model.getInstance().getViewFactory().showAdminWindow();
        }else {
            Model.getInstance().getViewFactory().showClientWindow();
        }
    }
}
