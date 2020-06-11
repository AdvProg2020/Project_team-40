package view;

import controller.accounts.AccountController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

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
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(roots.get(roots.size() - 1)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        innerPane.getChildren().clear();
        innerPane.getChildren().add(root);
    }

    public void exit() {
        System.exit(1);
    }
}
