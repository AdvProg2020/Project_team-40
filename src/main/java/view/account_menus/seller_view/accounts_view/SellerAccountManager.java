package view.account_menus.seller_view.accounts_view;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import view.account_menus.AllAccountsManager;

import java.net.URL;
import java.util.ResourceBundle;

public class SellerAccountManager extends AllAccountsManager implements Initializable {
    public Pane secondaryInnerPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setInnerPane("/layouts/accounts_menu.fxml", secondaryInnerPane);
    }

    public void goToHome() {
        setInnerPane("/layouts/accounts_menu.fxml", secondaryInnerPane);
    }

    public void goToProducts() {
        setInnerPane("/layouts/accounts_menu.fxml", secondaryInnerPane);
    }

    public void goToMyOffs() {
        //TODO
    }

    public void goToCategories() {
        setInnerPane("/layouts/manager_menus/manager_category_menus/CategoryMenuDesign.fxml", secondaryInnerPane);
    }
}