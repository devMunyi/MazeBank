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
    public TextField payee_address_fld;
    public Label error_lbl;
    public PasswordField password_fld;
    public Button login_btn;
    public Label payee_address_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        acc_selector.setItems(FXCollections.observableArrayList(AccountType.CLIENT, AccountType.ADMIN));
        acc_selector.setValue(Model.getInstance().getViewFactory().getAccountType());
        acc_selector.valueProperty().addListener(observable -> setAcc_selector());
        login_btn.setOnAction(event -> onLogin());
    }

    private void onLogin(){
        Stage stage = (Stage)login_btn.getScene().getWindow();
        AccountType selectedLoginType = Model.getInstance().getViewFactory().getAccountType();
        String pAddressOrUsernameInput = payee_address_fld.getText().trim();
        String passInput = password_fld.getCharacters().toString().trim(); // or just use .getText()

        // Begin validations
        if(pAddressOrUsernameInput.isEmpty()){
            if(selectedLoginType == AccountType.CLIENT){
                error_lbl.setText("Payee address cannot be empty!");
            }else{
                error_lbl.setText("Username cannot be empty!");
            }
            return;
        }

        if(pAddressOrUsernameInput.length() < 3){
            if(selectedLoginType == AccountType.CLIENT){
                error_lbl.setText("Payee address is too short!");
            }else{
                error_lbl.setText("Username is too short!");
            }
            return;
        }

        if(passInput.isEmpty()){
            error_lbl.setText("Password cannot be empty!");
            return;
        }

        if(selectedLoginType == AccountType.ADMIN){
            Model.getInstance().evaluateAdminCred(pAddressOrUsernameInput, passInput);
            if(Model.getInstance().isAdminLoggedIn()){
                // admin is authenticated
                Model.getInstance().getViewFactory().closeStageWithoutAlert(stage);
                Model.getInstance().getViewFactory().showAdminWindow();
            }else {
                error_lbl.setText("No such credentials!");
            }

        }else {
            Model.getInstance().evaluateClientCred(pAddressOrUsernameInput, passInput);
            if(Model.getInstance().isClientLoggedIn()){
                // client is authenticated
                Model.getInstance().getViewFactory().closeStageWithoutAlert(stage);
                Model.getInstance().getViewFactory().showClientWindow();
            }else {
                // client provided invalid credentials
                error_lbl.setText("No such credentials");
            }

        }
    }

    public void setAcc_selector(){
        AccountType selectedLoginType = acc_selector.getValue();
        Model.getInstance().getViewFactory().setAccountType(selectedLoginType);
        if(selectedLoginType == AccountType.ADMIN){
            payee_address_lbl.setText("Username:");
        }else {
            payee_address_lbl.setText("Payee Address:");
        }
    }
}
