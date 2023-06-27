package com.sam.mazebank.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CheckingAccount extends Account{

    private final IntegerProperty transactionLimit;
    public CheckingAccount(String owner, String accountNumber, double balance, int transLimit) {
        super(owner, accountNumber, balance);
        this.transactionLimit = new SimpleIntegerProperty(this, "Transaction Limit", transLimit);
    }

    public IntegerProperty transactionLimitProperty() {
        return transactionLimit;
    }

    @Override
    public String toString() {
        return accountNumberProperty().get();
    }
}
