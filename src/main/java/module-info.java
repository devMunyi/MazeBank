module com.sam.mazebank {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.sam.mazebank to javafx.fxml;
    exports com.sam.mazebank;
    exports com.sam.mazebank.controllers;
    exports com.sam.mazebank.controllers.admin;
    exports com.sam.mazebank.controllers.client;
    exports com.sam.mazebank.models;
    exports com.sam.mazebank.views;
}