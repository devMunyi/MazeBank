package com.sam.mazebank;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class App extends Application {
    private Parent root;
    private Scene scene;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/login.fxml"));
            if(root == null){
                root = fxmlLoader.load();
            }

            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            // System.out.println("App is Up & Running");
        }catch (Exception e){
            System.out.println("Error: "+ e.getMessage());
            // e.printStackTrace();
        }

        stage.setOnCloseRequest(e -> {
            e.consume();
            try {
                exitApp(stage);
            }catch (Exception e2){
                System.out.println(e2);
            }
        });
    }


    public void exitApp(Stage stage){
        // Close Alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close App");
        // alert.setHeaderText("About to Exit App");
        alert.setHeaderText("Are You Sure You Want to Exit");
        // alert.setContentText("Are Sure You Want to Exit the Application?");
        if(alert.showAndWait().get() == ButtonType.OK){
            try {
                Platform.exit();
                stage.close();
            }catch (Exception e){
                // System.out.println(e);
                e.printStackTrace();
            }
        }
    }
}
