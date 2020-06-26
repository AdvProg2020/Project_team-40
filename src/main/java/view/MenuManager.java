package view;

import com.jfoenix.controls.JFXButton;
import controller.accounts.AccountController;
import exceptions.DataException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import model.Loader;
import model.users.Manager;
import model.users.Seller;
import model.users.User;
import view.main_menu.MainMenuManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MenuManager {
    public static Pane mainInnerPane;
    private static Pane secondaryPane;
    private static JFXButton mainMenuAccountButton;
    private static JFXButton mainMenuProductButton;
    private static JFXButton mainMenuBackButton;

    protected static ArrayList<String> mainRoots;
    protected static ArrayList<String> secondaryRoots;
    protected static ArrayList<Boolean> isMainRoots;

    protected AccountController accountController = AccountController.getInstance();
    static {
        mainRoots = new ArrayList<>();
        mainRoots.add("/layouts/main.fxml");
        secondaryRoots = new ArrayList<>();
    }

    public void setMainInnerPane(String rootLocation) {
        if (mainInnerPane != null)
            mainInnerPane.getChildren().clear();
        mainRoots.add(rootLocation);
        isMainRoots.add(true);
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
        secondaryRoots.add(rootLocation);
        isMainRoots.add(false);
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
        if(accountController.isLogin()) {
            if (User.getLoggedInUser() instanceof Manager)
                setMainInnerPane("/layouts/manager_menus/manager_account_design.fxml");
            else if (User.getLoggedInUser() instanceof Seller)
                setMainInnerPane("/layouts/seller_menus/seller_account_design.fxml");
            else
                setMainInnerPane("/layouts/customer_menus/customer_account_design.fxml");
        } else {
            setMainInnerPane("/layouts/login_menu.fxml");
        }
    }

    public void logout() {
        secondaryRoots.clear();
        ArrayList<Boolean> secondaryRoots = new ArrayList<>();
        for(Boolean isMainRoot : isMainRoots) {
            if(!isMainRoot)
                secondaryRoots.add(isMainRoot);
        }
        for(Boolean isSecondaryRoot : secondaryRoots) {
            isMainRoots.remove(isSecondaryRoot);
        }
        accountController.logout();
        back();
    }

    public void back() {
      /*
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

       */
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
