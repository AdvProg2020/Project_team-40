package view.account_menus.seller_view;

import controller.accounts.SellerAccountController;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.log.Log;
import view.MenuManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SalesHistoryManager extends MenuManager implements Initializable {
    public VBox mainBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Log> salesHistory = SellerAccountController.getInstance().getSalesHistory();
        for(Log log : salesHistory) {
            mainBox.getChildren().add(new Label(log.toString()));
        }
    }
}
