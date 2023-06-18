package com.sam.mazebank.views;

import com.sam.mazebank.controllers.client.ClientController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {
    // client views
    private AnchorPane dashboardView;

    //constructor
    public ViewFactory(){}

    // method to load client dashboard
    public AnchorPane getDashboardView(){
        if(dashboardView == null){
            try {
                dashboardView = new FXMLLoader(getClass().getResource("/fxml/client/dashboard.fxml")).load();
            }catch (Exception e){
                System.out.println(e);
            }

        }

        return dashboardView;
    }


    // to display login window
    public void showLoginWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        createStage(loader);
    }


    // to display client window
    public void showClientWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);

        createStage(loader);
    }

    private void createStage(FXMLLoader loader){
        Scene scene = null;

        try {
            scene = new Scene(loader.load());
        }catch (Exception e){
//            System.out.println(e);
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Maze Bank");
        stage.show();
    }

    public void closeStageWithoutAlert(Stage stage){
        try {
            stage.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private void closeStageWithAlert(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("About to Close the App");
        alert.setHeaderText("Are you sure want to close the App?");

        if(alert.showAndWait().get() == ButtonType.OK){
            try {
                stage.close();
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

}
