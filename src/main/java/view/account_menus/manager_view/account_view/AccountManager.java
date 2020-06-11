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

    public void setInnerPane(Parent root){
        innerPane.getChildren().clear();
        innerPane.getChildren().add(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/layouts/empty_layouts/PersonalInfo.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        setInnerPane(root);
    }


    public void goToHomeMenu(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/layouts/empty_layouts/PersonalInfo.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        setInnerPane(root);
    }

    public void handleLoadUsersMenu(ActionEvent event) {
        setInnerPane("/layouts/empty_layouts/ManageUsersDesign.fxml", innerPane);
    }

    public void handleLoadProductsMenu(ActionEvent event) {
        setInnerPane("/layouts/empty_layouts/ManageProductsDesign.fxml", innerPane);
    }
}
