package view.account_menus.customer_view.account_view;

import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import view.account_menus.AllAccountsManager;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerAccountManager extends AllAccountsManager implements Initializable {

    public AnchorPane secondaryInnerPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSecondaryPane(secondaryInnerPane);
        setSecondaryInnerPane("/layouts/accounts_menu.fxml");
    }

    public void goToHome() {
        setSecondaryInnerPane("/layouts/accounts_menu.fxml");
    }

    public void goToOrders() {
        setSecondaryInnerPane("/layouts/customer_menus/orders_menu_design.fxml");
    }

    public void goToCart() {
        setSecondaryInnerPane("/layouts/customer_menus/cart_menu_design.fxml");
    }

    public void goToDiscountCode() {
        setSecondaryInnerPane("/layouts/customer_menus/customer_discount_design.fxml");
    }
}
