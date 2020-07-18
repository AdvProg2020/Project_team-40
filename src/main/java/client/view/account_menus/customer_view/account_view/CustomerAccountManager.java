package client.view.account_menus.customer_view.account_view;

import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import client.view.MenuManager;

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
        setSecondaryInnerPane("/layouts/customer_menus/purchase_menus/cart_menu_design.fxml");
    }

    public void goToDiscountCode() {
        setSecondaryInnerPane("/layouts/manager_menus/manager_discount_menus/discount_menu_design.fxml");
    }

    public void goToOrdersMenu() {
        setSecondaryInnerPane("/layouts/customer_menus/customer_orders_menus/orders_menu_design.fxml");
    }

    public void goToChats() {
        setSecondaryInnerPane("/layouts/customer_menus/chats_menu.fxml");
    }

    public void goToSupports() {
        setSecondaryInnerPane("/layouts/customer_menus/supports_menu.fxml");
    }
}
