package client.view.account_menus.seller_view.accounts_view;

import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import client.view.MenuManager;

import java.net.URL;
import java.util.ResourceBundle;

public class SellerAccountManager extends MenuManager implements Initializable {
    public Pane secondaryInnerPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSecondaryPane(secondaryInnerPane);
        setSecondaryInnerPane("/layouts/accounts_menu.fxml");
    }

    public void goToHome() {
        setSecondaryInnerPane("/layouts/accounts_menu.fxml");
    }

    public void goToProducts() {
        setSecondaryInnerPane("/layouts/seller_menus/manage_product_menus/products_menu_design.fxml");
    }

    public void goToMyOffs() {
        setSecondaryInnerPane("/layouts/seller_menus/sellers_offs_design.fxml");
    }

    public void goToCategories() {
        setSecondaryInnerPane("/layouts/manager_menus/manager_category_menus/category_menu_design.fxml");
    }

    public void goToSalesHistory() {
        setSecondaryInnerPane("/layouts/customer_menus/customer_orders_menus/orders_menu_design.fxml");
    }
}