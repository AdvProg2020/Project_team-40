package view.account_menus.manager_view.account_view;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountManager extends view.account_menus.AccountsManager implements Initializable {
    public JFXButton homeButton;
    public JFXButton discountsButton;
    public JFXButton categoriesButton;
    public Pane secondaryInnerPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setInnerPane("/layouts/accounts_menu.fxml", secondaryInnerPane);
    }

    public void goToHomeMenu() {
        setInnerPane("/layouts/accounts_menu.fxml", secondaryInnerPane);
    }

    public void handleLoadUsersMenu() {
        setInnerPane("/layouts/manager_menus/manager_users_menus/manage_users_design.fxml", secondaryInnerPane);
    }

    public void handleLoadProductsMenu() {
        setInnerPane("/layouts/manager_menus/manager_products_menu/manage_products_design.fxml", secondaryInnerPane);
    }

    public void goToDiscountsMenu() {
        setInnerPane("/layouts/manager_menus/manager_discount_menus/discount_menu_design.fxml", secondaryInnerPane);
    }

    public void goToRequestsMenu() {
        setInnerPane("/layouts/manager_menus/manager_request_menus/RequestsMenuDesign.fxml", secondaryInnerPane);

    }

    public void handleGoToCategoriesMenu() {
        setInnerPane("/layouts/manager_menus/manager_category_menus/CategoryMenuDesign.fxml", secondaryInnerPane);
    }
}
