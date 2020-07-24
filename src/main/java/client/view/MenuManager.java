package client.view;

import client.controller.Client;
import client.controller.RequestHandler;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MenuManager {
    public static Pane mainInnerPane;
    private static Pane secondaryPane;
    private static JFXButton mainMenuAccountButton;
    private static JFXButton mainMenuProductButton;
    private static JFXButton mainMenuBackButton;
    protected static ArrayList<String> roots;
    private HashMap<String, String> requestQueries = new HashMap<>();

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
        String username = Client.getInstance().getUsername();
        String role = Client.getInstance().getRole();
        if(username != null) {
            if (role.equals("Manager"))
                setMainInnerPane("/layouts/manager_menus/manager_account_design.fxml");
            else if (role.equals("Seller"))
                setMainInnerPane("/layouts/seller_menus/seller_account_design.fxml");
            else if (role.equals("Customer"))
                setMainInnerPane("/layouts/customer_menus/customer_account_design.fxml");
            else if (role.equals("Support"))
                setMainInnerPane("/layouts/support_menus/support_account_design.fxml");
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
            Parent root = FXMLLoader.load(getClass().getResource("/layouts/bank_menus/bank_menu.fxml"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 1300, 600));
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToWallet() {
        setSecondaryInnerPane("/layouts/customer_seller_common_menus/wallet_menus/wallet_menu_design.fxml");
    }

    public void goToAuctions() {
        setSecondaryInnerPane("/layouts/customer_seller_common_menus/auctions_menus/auctions_menu.fxml");
    }

    public void logout() {
        roots.clear();
        roots.add("/layouts/main.fxml");
        roots.add("");
        requestQueries.clear();
        requestQueries.put("username", Client.getInstance().getUsername());
        RequestHandler.delete("/accounts/account/", requestQueries, false, null);

        Client.getInstance().logout();
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
        requestQueries.clear();
        requestQueries.put("username", Client.getInstance().getUsername());
        RequestHandler.delete("/accounts/account/", requestQueries, false, null);
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