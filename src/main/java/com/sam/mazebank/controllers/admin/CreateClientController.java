package com.sam.mazebank.controllers.admin;

import com.sam.mazebank.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;

public class CreateClientController implements Initializable {
    public TextField fName_fld;
    public TextField lName_fld;
    public PasswordField password_fld;
    public CheckBox pAddress_box;
    public Label pAddress_lbl;
    public CheckBox ch_acc_chk_box;
    public TextField ch_amount_fld;
    public CheckBox sv_acc_chk_box;
    public TextField sv_amount_fld;
    public Button create_client_btn;
    public Label error_lbl;

    private String pAddress = "";
    // private boolean createCheckingAccFlag;
    // private boolean createSavingsAccFlag;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        create_client_btn.setOnAction(event -> createClient());
        pAddress_box.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
            if(newVal){
                pAddress = createPayeeAddress();
                onCreatePayeeAddress();
            }else {
                pAddress_lbl.setText("");
            }
        });
    }

    private void createClient(){
        // create a checking account
        if(ch_acc_chk_box.isSelected()){
            createAccount("Checking");
        }

        // create a savings account
        if(sv_acc_chk_box.isSelected()){
            createAccount("Savings");
        }

        // create client
        String fName = fName_fld.getText();
        String lName = lName_fld.getText();
        String password = password_fld.getText();

        // validate fields
        error_lbl.setText("");
        error_lbl.setStyle("fx-text-fill: #FF0000;");
        if(fName.isEmpty()) {
            error_lbl.setText("Firstname cannot be empty!");
            return;
        }
        if(lName.isEmpty()) {
            error_lbl.setText("Lastname cannot be empty!");
            return;
        }
        if(password.isEmpty()){
            error_lbl.setText("Password cannot be empty!");
            return;
        }

        if(pAddress.isEmpty()) {
            error_lbl.setText("Payee Address cannot be empty!");
            return;
        }

        LocalDate createdAt = LocalDate.now();
        Model.getInstance().getDatabaseDriver().createClient(fName, lName, pAddress, password, createdAt);

        emptyFields();

        error_lbl.setStyle("-fx-text-fill: blue; -fx-font-size: 1.3em; -fx-font-weight: bold;");
        error_lbl.setText("Client Created Successfully!");
    }

    public String createPayeeAddress(){
        char fChar;
        String pAddress = "";
        int id = Model.getInstance().getDatabaseDriver().getLastClientsId() + 1;
        if(fName_fld.getText() != null){
            fChar = Character.toLowerCase(fName_fld.getText().charAt(0));
            String lName = lName_fld.getText();
            pAddress = "@"+fChar+lName+id;
        }

        return pAddress;
    }

    public void onCreatePayeeAddress(){
        if(fName_fld.getText() != null && lName_fld.getText() != null){
            pAddress_lbl.setText(pAddress);
        }
    }

    private void createAccount(String accountType){
        // create account number
        String firstSection = "3201";
        String lastSection = Integer.toString(new Random().nextInt(999)+1000);
        String accountNumber = firstSection + " " + lastSection;
        double chAccBalance = Double.parseDouble(ch_amount_fld.getText()); // checking account balance
        double svAccBalance = Double.parseDouble(sv_amount_fld.getText()); // savings account balance

        // create checking or savings account
        if(accountType.equals("Checking")){
            Model.getInstance().getDatabaseDriver().createCheckingAccount(pAddress, accountNumber, 10, chAccBalance);
        }else {
            Model.getInstance().getDatabaseDriver().createSavingsAccount(pAddress, accountNumber, 2000, svAccBalance);
        }

    }

    public void emptyFields(){
        fName_fld.setText("");
        lName_fld.setText("");
        password_fld.setText("");
        pAddress_box.setSelected(false);
        pAddress_lbl.setText("");
        ch_acc_chk_box.setSelected(false);
        ch_amount_fld.setText("");
        sv_acc_chk_box.setSelected(false);
        sv_amount_fld.setText("");
    }

}
