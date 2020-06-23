package view.account_menus.seller_view.accounts_view;

import javafx.scene.layout.Pane;
import view.account_menus.AccountsManager;

public abstract class AccountManager extends AccountsManager {
    public Pane secondaryInnerPane;

    public void goToHome() {
        setInnerPane("/layouts/accounts_menu.fxml", secondaryInnerPane);
        //TODO:
    }
}
