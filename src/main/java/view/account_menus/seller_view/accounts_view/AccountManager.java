package view.account_menus.seller_view.accounts_view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import view.account_menus.AccountsManager;

import java.io.IOException;

public class AccountManager extends AccountsManager {
    public Pane secondaryInnerPane;

    public void goToHome() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/layouts/accounts_menu.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        setInnerPane(root, secondaryInnerPane);
        //TODO:
    }
}
