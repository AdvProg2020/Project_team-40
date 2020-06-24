package view;

import controller.accounts.AccountController;
import exceptions.DataException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import model.Loader;
import model.users.Manager;
import model.users.Seller;
import model.users.User;

import java.io.IOException;
import java.util.ArrayList;

public abstract class MenuManager {
    public static Pane mainInnerPane;
    private static Pane secondaryPane;

    protected static ArrayList<String> roots;

    protected AccountController accountController = AccountController.getInstance();
    static {
        roots = new ArrayList<>();
        roots.add("/layouts/main.fxml");
    }
    public void setMainInnerPane(String rootLocation) {
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

    public void goToOffsMenu(){
        setMainInnerPane("/layouts/offs_menu.fxml");
    }

    public void goToAccountsMenu() {
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
        roots.clear();
        roots.add("/layouts/main.fxml");
        roots.add("");
        accountController.logout();
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
}
