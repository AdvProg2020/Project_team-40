package view.account_menus.manager_view.account_view;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import view.MenuManager;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountManager extends MenuManager implements Initializable {
    public Pane secondaryInnerPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSecondaryPane(secondaryInnerPane);
        setSecondaryInnerPane("/layouts/accounts_menu.fxml");
    }

    public void goToHomeMenu() {
        setSecondaryInnerPane("/layouts/accounts_menu.fxml");
    }

    public void handleLoadUsersMenu() {
        setSecondaryInnerPane("/layouts/manager_menus/manager_users_menus/manage_users_design.fxml");
    }

    public void handleLoadProductsMenu() {
        setSecondaryInnerPane("/layouts/manager_menus/manager_products_menu/manage_products_design.fxml");
    }

    public void goToDiscountsMenu() {
        setSecondaryInnerPane("/layouts/manager_menus/manager_discount_menus/discount_menu_design.fxml");
    }

    public void goToRequestsMenu() {
        setSecondaryInnerPane("/layouts/manager_menus/manager_request_menus/RequestsMenuDesign.fxml");
    }

    public void handleGoToCategoriesMenu() {
        setSecondaryInnerPane("/layouts/manager_menus/manager_category_menus/CategoryMenuDesign.fxml");
    }
}
