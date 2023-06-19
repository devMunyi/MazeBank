package com.sam.mazebank.views;

import com.sam.mazebank.controllers.admin.AdminController;
import com.sam.mazebank.controllers.client.ClientController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {

    private AccountType accountType;

    // client views
    private final ObjectProperty<ClientMenuOptions> clientSelectedNavItem;
    private AnchorPane dashboardView;
    private AnchorPane transactionsView;
    private AnchorPane accountsView;

    // admin views
    private final ObjectProperty<AdminMenuOptions> adminSelectedNavItem;
    private AnchorPane createClientView;
    private AnchorPane clientsView;

    // constructor
    public ViewFactory(){
        this.accountType = AccountType.CLIENT;
        this.clientSelectedNavItem = new SimpleObjectProperty<>();
        this.adminSelectedNavItem = new SimpleObjectProperty<>();
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    /*
      Client Views Section
    */
    public ObjectProperty<ClientMenuOptions> getClientSelectedNavItem(){
        return clientSelectedNavItem;
    }
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

    public AnchorPane getTransactionsView() {

        if(transactionsView == null){
            try {
                transactionsView = new FXMLLoader(getClass().getResource("/fxml/client/transactions.fxml")).load();
            }catch (Exception e){
                System.out.println(e);
            }
        }

        return transactionsView;
    }

    public AnchorPane getAccountsView() {
        if(accountsView == null){
            try {
                accountsView = new FXMLLoader(getClass().getResource("/fxml/client/accounts.fxml")).load();
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return accountsView;
    }

    // to display client window
    public void showClientWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);

        createStage(loader);
    }


    /*
        Admin Views Section
     */

    public ObjectProperty<AdminMenuOptions> getAdminSelectedNavItem(){
        return adminSelectedNavItem;
    }
    public AnchorPane getCreateClientView(){
        if(createClientView == null){
            try {
                createClientView = new FXMLLoader(getClass().getResource("/fxml/admin/create-client.fxml")).load();
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return createClientView;
    }

    public AnchorPane getClientsView() {
        if(clientsView == null){
            try {
                clientsView = new FXMLLoader(getClass().getResource("/fxml/admin/clients.fxml")).load();
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return clientsView;
    }

    public void showAdminWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin/admin.fxml"));
        AdminController adminController = new AdminController();
        loader.setController(adminController);
        createStage(loader);
    }

    // to display login window
    public void showLoginWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
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
