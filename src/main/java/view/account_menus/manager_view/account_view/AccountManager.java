package view.account_menus.manager_view.account_view;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import view.MenuManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountManager extends MenuManager implements Initializable {
    public JFXButton homeButton;
    public Pane innerPane;
    public JFXButton discountsButton;
    public JFXButton categoriesButton;

    public void setInnerPane(Parent root){
        innerPane.getChildren().clear();
        innerPane.getChildren().add(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/layouts/accounts_menu.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        setInnerPane(root);
    }


    public void goToHomeMenu(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/layouts/accounts_menu.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        setInnerPane(root);
    }

    public void handleLoadUsersMenu(ActionEvent event) {
        setInnerPane("/layouts/manager_menus/manager_users_menus/manage_users_design.fxml", innerPane);
    }

    public void handleLoadProductsMenu(ActionEvent event) {
        setInnerPane("/layouts/manager_menus/manager_products_menu/manage_products_design.fxml", innerPane);
    }

    public void goToDiscountsMenu(ActionEvent event) {
        setInnerPane("/layouts/manager_menus/manager_discount_menus/discount_menu_design.fxml", innerPane);
    }

    public void goToRequestsMenu(ActionEvent event) {
        setInnerPane("/layouts/manager_menus/manager_request_menus/RequestsMenuDesign.fxml", innerPane);

    }

    public void handleGoToCategoriesMenu(ActionEvent event) {
        setInnerPane("/layouts/manager_menus/manager_category_menus/CategoryMenuDesign.fxml", innerPane);

    }
}
