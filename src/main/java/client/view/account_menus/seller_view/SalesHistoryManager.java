package client.view.account_menus.seller_view;

import server.controller.accounts.SellerAccountController;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import server.model.log.Log;
import client.view.MenuManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SalesHistoryManager extends MenuManager implements Initializable {
    public VBox mainBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Log> salesHistory = SellerAccountController.getInstance().getSalesHistory();
        for(Log log : salesHistory) {
            String[] information = log.toString().split("\n");
            for(String line : information) {
                Label label = new Label(line);
                label.setFont(new Font(20));
                mainBox.getChildren().add(label);
            }
            mainBox.getChildren().add(new Label("\n"));
        }
    }
}
