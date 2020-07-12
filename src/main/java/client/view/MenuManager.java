package client.view;

import client.view.account_menus.ThisUser;
import com.jfoenix.controls.JFXButton;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.controller.accounts.AccountController;
import exceptions.DataException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import server.model.Loader;
import server.model.users.Manager;
import server.model.users.Seller;
import server.model.users.User;

import java.io.IOException;
import java.util.ArrayList;

public class MenuManager {
    public static Pane mainInnerPane;
    private static Pane secondaryPane;
    private static JFXButton mainMenuAccountButton;
    private static JFXButton mainMenuProductButton;
    private static JFXButton mainMenuBackButton;
    protected static ArrayList<String> roots;
    protected static final int BANK_PORT = 8080;

    protected AccountController accountController = AccountController.getInstance();
    static {
        roots = new ArrayList<>();
        roots.add("/layouts/main.fxml");
    }

    public void setMainInnerPane(String rootLocation) {
        if (mainInnerPane != null)
            mainInnerPane.getChildren().clear();
        roots.add(rootLocation);
        if(!rootLocation.equals("/layouts/main.fxml")) {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource(rootLocation));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mainInnerPane.getChildren().add(root);
        }
    }

    //Set pane for CHILDREN inner panes
    public void setSecondaryInnerPane(String rootLocation){
        roots.add(rootLocation);
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(rootLocation));
        } catch (IOException e) {
            e.printStackTrace();
        }
        secondaryPane.getChildren().clear();
        secondaryPane.getChildren().add(root);
    }

    protected void setSecondaryInnerPane(Parent root) {
        secondaryPane.getChildren().clear();
        secondaryPane.getChildren().add(root);
    }

    public void goToProductsMenu(){
        setMainInnerPane("/layouts/shopping_menus/products_menu.fxml");
    }

    private static void setButtonsVisible(){
        if (mainMenuAccountButton != null && mainMenuBackButton != null && mainMenuProductButton != null) {
            mainMenuAccountButton.setDisable(false);
            mainMenuProductButton.setDisable(false);
            mainMenuBackButton.setDisable(false);
            mainMenuAccountButton = null;
            mainMenuBackButton = null;
            mainMenuProductButton = null;
        }
    }

    public void goToAccountsMenu() {
        setButtonsVisible();
        if(ThisUser.getUsername() != null) {
            if (User.getUserByUsername(ThisUser.getUsername()) instanceof Manager)
                setMainInnerPane("/layouts/manager_menus/manager_account_design.fxml");
            else if (User.getUserByUsername(ThisUser.getUsername()) instanceof Seller)
                setMainInnerPane("/layouts/seller_menus/seller_account_design.fxml");
            else
                setMainInnerPane("/layouts/customer_menus/customer_account_design.fxml");
        } else {
            setMainInnerPane("/layouts/login_menu.fxml");
        }
    }

    public void goToBankLogin() {
        setSecondaryInnerPane("/layouts/bank_menus/bank_login.fxml");
    }

    public void goToBank() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/layouts/manager_menus/manager_category_menus/add_category.fxml"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 1200, 600));
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        roots.clear();
        roots.add("/layouts/main.fxml");
        roots.add("");
        ThisUser.logout();
        back();
    }

    public void back() {
        if(roots.size() == 1) {
            exit();
        }
        roots.remove(roots.size() - 1);
        mainInnerPane.getChildren().clear();
        String lastRoot = roots.get(roots.size() - 1);
        if(!lastRoot.equals("/layouts/main.fxml")) {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource(lastRoot));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mainInnerPane.getChildren().add(root);
        }
    }

    public static void setSecondaryPane(Pane pane) {
        secondaryPane = pane;
    }

    public void exit() {
        try {
            Loader.getLoader().saveData();
        } catch (DataException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }

    public static void setMainMenuAccountButton(JFXButton mainMenuAccountButton) {
        MenuManager.mainMenuAccountButton = mainMenuAccountButton;
    }

    public static void setMainMenuProductButton(JFXButton mainMenuProductButton) {
        MenuManager.mainMenuProductButton = mainMenuProductButton;
    }

    public static void setMainMenuBackButton(JFXButton mainMenuBackButton) {
        MenuManager.mainMenuBackButton = mainMenuBackButton;
    }
}