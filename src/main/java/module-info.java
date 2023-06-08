module com.sam.mazebank {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.sam.mazebank to javafx.fxml;
    exports com.sam.mazebank;
}