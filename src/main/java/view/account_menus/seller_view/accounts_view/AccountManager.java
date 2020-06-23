package view.account_menus.seller_view.accounts_view;

import javafx.scene.layout.Pane;
import view.account_menus.AllAccountsManager;

public class AccountManager extends AllAccountsManager {
    public Pane secondaryInnerPane;

    public void goToHome() {
        setInnerPane("/layouts/accounts_menu.fxml", secondaryInnerPane);
    }

    public void goToProducts() {
        //TODO
    }

    public void goToMyOffs() {
        //TODO
    }

    public void goToCategories() {
    }
}