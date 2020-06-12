package view;

import controller.accounts.AccountController;
import exceptions.DataException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import model.Loader;

import java.io.IOException;
import java.util.ArrayList;

public abstract class MenuManager {
    public Pane innerPane;
    protected static ArrayList<String> roots;
    protected AccountController accountController = AccountController.getInstance();
    static {
        roots = new ArrayList<>();
        roots.add("/layouts/main.fxml");
    }

    public void setInnerPane(String rootLocation){
        innerPane.getChildren().clear();
        roots.add(rootLocation);
        if(!rootLocation.equals("/layouts/main.fxml")) {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource(rootLocation));
            } catch (IOException e) {
                e.printStackTrace();
            }
            innerPane.getChildren().add(root);
        }
    }

    //Set pane for CHILDREN inner panes
    public void setInnerPane(String rootLocation, Pane innerPane){
        roots.add(rootLocation);
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(rootLocation));
        } catch (IOException e) {
            e.printStackTrace();
        }
        innerPane.getChildren().clear();
        innerPane.getChildren().add(root);
    }

    public void goToProductsMenu(){
        setInnerPane("/layouts/products_menu.fxml");
    }

    public void goToOffsMenu(){
        setInnerPane("/layouts/offs_menu.fxml");
    }

    public void goToAccountsMenu(){
        if(accountController.isLogin()) {
            setInnerPane("/layouts/accounts_menu.fxml");
        } else {
            setInnerPane("/layouts/login_menu.fxml");
        }
    }

    public void back() {
        if(roots.size() == 1) {
            exit();
        }
        roots.remove(roots.size() - 1);
        innerPane.getChildren().clear();
        String lastRoot = roots.get(roots.size() - 1);
        if(!lastRoot.equals("/layouts/main.fxml")) {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource(lastRoot));
            } catch (IOException e) {
                e.printStackTrace();
            }
            innerPane.getChildren().add(root);
        }
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
