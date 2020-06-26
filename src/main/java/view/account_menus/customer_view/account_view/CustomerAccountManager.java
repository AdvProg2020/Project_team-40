package view.account_menus.customer_view.account_view;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import view.MenuManager;
import view.account_menus.customer_view.cart_view.LogMenuManager;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerAccountManager extends MenuManager implements Initializable {

    public AnchorPane secondaryInnerPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSecondaryPane(secondaryInnerPane);
        setSecondaryInnerPane("/layouts/accounts_menu.fxml");
    }

    public void goToHome() {
        setSecondaryInnerPane("/layouts/accounts_menu.fxml");
    }

    public void goToCart() {
        LogMenuManager.setLastMenuRoot(secondaryInnerPane.getParent());
        setSecondaryInnerPane("/layouts/customer_menus/cart_menu_design.fxml");
    }

    public void goToDiscountCode() {
        setSecondaryInnerPane("/layouts/manager_menus/manager_discount_menus/discount_menu_design.fxml");
    }

    public void goToOrdersMenu(ActionEvent event) {
        setSecondaryInnerPane("/layouts/customer_menus/customer_orders_menus/orders_menu_design.fxml");
    }
}
