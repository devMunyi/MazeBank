package com.sam.mazebank.controllers.admin;

import com.sam.mazebank.models.Client;
import com.sam.mazebank.models.Model;
import com.sam.mazebank.views.ClientCellFactory;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DepositController implements Initializable {
    public TextField pAddress_fld;
    public Button search_btn;
    public ListView<Client> result_listview;
    public TextField amount_fld;
    public Button deposit_btn;
    public Label notice_lbl;
    public Label bal_lbl;

    private Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        search_btn.setOnAction(e -> onClientSearch());
        deposit_btn.setOnAction(e -> onDeposit());
    }

    public void onClientSearch(){

        if(pAddress_fld.getText() == null || pAddress_fld.getText().isEmpty()){
            notice_lbl.setStyle("-fx-text-fill: #FF0000;");
            notice_lbl.setText("Please enter payee address first!");
            return;
        }

        ObservableList<Client> searchResults = Model.getInstance().searchClient(pAddress_fld.getText());
        result_listview.setItems(searchResults);
        result_listview.setCellFactory(e -> new ClientCellFactory());
        client = searchResults.get(0);
        bal_lbl.setText("$"+client.savingsAccountProperty().get().balanceProperty().get());
    }

    public void onDeposit(){
        if(client == null){
            notice_lbl.setStyle("-fx-text-fill: #FF0000;");
            notice_lbl.setText("Please search a client first!");
            return;
        }

        if(amount_fld.getText().isEmpty() || amount_fld.getText() == null){
            notice_lbl.setStyle("-fx-text-fill: #FF0000;");
            notice_lbl.setText("Amount cannot be empty!");
            return;
        }
         double amount = Double.parseDouble(amount_fld.getText());
         double newBalance = amount + client.savingsAccountProperty().get().balanceProperty().get();
         boolean okay = Model.getInstance().getDatabaseDriver().depositSavings(client.savingsAccountProperty().get().ownerProperty().get(), newBalance);

         if(okay){
             notice_lbl.setStyle("-fx-text-fill: green;");
             notice_lbl.setText("Deposit was made successfully!");
             bal_lbl.setText(String.valueOf("$"+newBalance));
         }else{
             notice_lbl.setStyle("-fx-text-fill: #FF0000;");
             notice_lbl.setText("Something went wrong!");
         }
    }

}


