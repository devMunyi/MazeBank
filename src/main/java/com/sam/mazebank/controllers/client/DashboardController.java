package com.sam.mazebank.controllers.client;

import com.sam.mazebank.models.Model;
import com.sam.mazebank.models.Transaction;
import com.sam.mazebank.views.TransactionCellFactory;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class DashboardController implements Initializable {
    public Text user_name;
    public Label login_date_lbl;
    public Label checking_bal;
    public Label checking_acc_num;
    public Label savings_bal;
    public Label savings_acc_num;
    public Label income_lbl;
    public Label expenses_lbl;
    public ListView<Transaction> transactions_listview;
    public TextField payee_fld;
    public TextField amount_fld;
    public TextArea message_fld;
    public Button send_money_btn;
    public Label notice_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindData();
        accountSummary();
        initLatestTransactions();
        transactions_listview.setItems(Model.getInstance().getLatestTransactions());
        transactions_listview.setCellFactory(e -> new TransactionCellFactory());
        send_money_btn.setOnAction(event -> onSendMoney());
    }

    public void bindData(){
        user_name.textProperty().bind(Bindings.concat("Hi ").concat(Model.getInstance().getClient().firstNameProperty()));
        login_date_lbl.setText("Today, "+ LocalDate.now());
        checking_bal.textProperty().bind(Model.getInstance().getClient().checkingAccountProperty().get().balanceProperty().asString());
        checking_acc_num.textProperty().bind(Model.getInstance().getClient().checkingAccountProperty().get().accountNumberProperty());
        savings_bal.textProperty().bind(Model.getInstance().getClient().savingsAccountProperty().get().balanceProperty().asString());
        savings_acc_num.textProperty().bind(Model.getInstance().getClient().savingsAccountProperty().get().accountNumberProperty());
    }

    public void initLatestTransactions(){
        if(Model.getInstance().getLatestTransactions().isEmpty()){
            Model.getInstance().setLatestTransactions();
        }
    }

    public void onSendMoney(){
        String receiver = payee_fld.getText().trim();
        String sender = Model.getInstance().getClient().savingsAccountProperty().get().ownerProperty().get();
        String amtString= amount_fld.getText().trim();
        double amount = !amtString.isEmpty() ? Double.parseDouble(amtString) : 0;
        String message = message_fld.getText().trim();
        attachNotice("", "-fx-text-fill: red; -fx-font-size: 10;");

        // validations
        if(validateSendMoneyInputs(receiver, sender, amtString)){
            return;
        };


        // ensure sender has enough funds and if true deduct sender equivalent amount from savings account
        boolean updatedSenderBal = Model.getInstance().getDatabaseDriver().updateBalance(sender, amount, "SUB");
        System.out.println("UPDATE SENDER BAL RESP:"+ updatedSenderBal);
        if(!updatedSenderBal){
            attachNotice("Error Occurred While Updating Savings Account Balance", "-fx-text-fill: red; -fx-font-size: 10;");
            return;
        }

        // Top Up Receiver's savings Account
        boolean updatedReceiverBal = Model.getInstance().getDatabaseDriver().updateBalance(receiver, amount, "ADD");
        System.out.println("UPDATE RECEIVER BAL RESP:"+ updatedReceiverBal);
        if(!updatedReceiverBal){
            attachNotice("Error Occurred While Transferring Money", "-fx-text-fill: red; -fx-font-size: 10;");
            return;
        }

        // record a new transaction
        boolean addedTransaction = Model.getInstance().getDatabaseDriver().addTransaction(sender, receiver, amount, message);
        System.out.println("ADD NEW TRANSACTION RESP:"+ addedTransaction);
        if(!addedTransaction){
            attachNotice("Error Occurred While Adding a Transaction!", "-fx-text-fill: green; -fx-font-size: 10;");
            return;
        }

        // get sender new savings balance
        double senderNewBal = Model.getInstance().getDatabaseDriver().getSavingsAccountBal(sender);
        System.out.println("SENDER BAL RESP: "+ senderNewBal);

        try {
            Model.getInstance().getClient().savingsAccountProperty().get().setBalanceProperty(senderNewBal);
        }catch (Exception e){
            System.out.println(e);
        }

        attachNotice("Money was Sent Successfully!", "-fx-text-fill: green; -fx-font-size: 10;");
        clearFields();
    }

    public boolean validateSendMoneyInputs(String sender, String receiver, String amountStr){

        boolean anInputIsInvalid = false;

        try {
            if(sender != null && sender.isEmpty()){
                attachNotice("Sender Address Missing", "-fx-text-fill: red; -fx-font-size: 10;");
                anInputIsInvalid  = true;
            }

            if(sender != null && receiver.isEmpty()){
                attachNotice("Payee Address Cannot be Empty", "-fx-text-fill: red; -fx-font-size: 10;");
                anInputIsInvalid = true;
            }

            if(amountStr != null && amountStr.isEmpty()){
                attachNotice("Amount Cannot be Empty", "-fx-text-fill: red; -fx-font-size: 10;");
                anInputIsInvalid = true;
            }

            // assert amountStr != null;
            if(amountStr != null && !amountStr.isEmpty() && Double.parseDouble(amountStr) < 1){
                attachNotice("Amount Entered is Invalid", "-fx-text-fill: red; -fx-font-size: 10;");
                anInputIsInvalid = true;
            }
        }catch (Exception e){
            anInputIsInvalid = true;
            System.out.println(e);
        }

        return anInputIsInvalid;
    }

    public void clearFields(){
        payee_fld.setText("");
        amount_fld.setText("");
        message_fld.setText("");

        // clear notice_lbl after 2500 seconds
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                // notice_lbl.setText("");
                Platform.runLater(() -> notice_lbl.setText(""));
            }
        }, 2500);
    }

    public void attachNotice(String message, String fxClass){
        notice_lbl.setStyle(fxClass);
        notice_lbl.setText(message);
    }

    public void accountSummary(){
        double income = 0;
        double expense = 0;

        // ensure database call to get all transactions is made conditionally
        if(Model.getInstance().getAllTransactions().isEmpty()){
            Model.getInstance().setAllTransactions();
        }

        String loggedUser = Model.getInstance().getClient().payeeAddressProperty().get();
        for (Transaction transaction: Model.getInstance().getAllTransactions()){
            // means an expense
            String transSender = transaction.senderProperty().get();
            if(Objects.equals(transSender, loggedUser)){
                // System.out.println("YES transSender equal to loggedUser");
                expense += transaction.amountProperty().get();
            }else {
                // System.out.println("YES transSender NOT equal to loggedUser");
                income += transaction.amountProperty().get();
            }
        }

        income_lbl.setText("+ $"+income);
        expenses_lbl.setText("- $"+expense);
    }
}
