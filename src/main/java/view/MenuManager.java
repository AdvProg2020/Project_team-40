package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;

public abstract class MenuManager {
    public Pane innerPane;
    protected ArrayList<String> roots;


    public void setInnerPane(String rootLocation){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(rootLocation));
        } catch (IOException e) {
            e.printStackTrace();
        }
        roots.add(rootLocation);
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
        setInnerPane("/layouts/accounts_menu.fxml");
    }

    public void back() {
    }

    public void exit() {
        System.exit(1);
    }
}
