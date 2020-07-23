package client.view.account_menus.seller_view.auction_view;

import client.view.account_menus.customer_seller_common_view.AuctionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AuctionItem {
    public Label idLabel;
    public void viewAuction() {
        AuctionManager.setAuctionID(idLabel.getText());
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().
                    getResource("/layouts/customer_seller_common_menus/auction_menus/auction.fxml"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 800, 600));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
