package view.account_menus.customer_view.account_view;

import javafx.fxml.Initializable;
import view.account_menus.AllAccountsManager;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerAccountManager extends AllAccountsManager implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSecondaryInnerPane("/layouts/accounts_menu.fxml");
    }

    public void goToHome() {
        setSecondaryInnerPane("/layouts/accounts_menu.fxml");
    }

    public void goToOrders() {

    }

    public void goToCart() {
    }

    public void goToDiscountCode() {
    }
}
