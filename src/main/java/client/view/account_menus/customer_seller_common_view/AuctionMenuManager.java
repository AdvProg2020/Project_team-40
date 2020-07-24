package client.view.account_menus.customer_seller_common_view;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.model.Auction;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AuctionMenuManager extends MenuManager implements Initializable {
    public VBox vBoxItems;
    public Button addAuction;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         loadAuctions();
    }

    private void loadAuctions() {
        HashMap<String, String> queries = new HashMap<>();
        Client client = Client.getInstance();
        if(client.getRole().equals("Seller")) {
            queries.put("role", "seller");
        } else {
            queries.put("role", "customer");
            removeAddAuctionButton();
        }
        queries.put("username", client.getUsername());
        ArrayList<Auction> response = RequestHandler.get("/accounts/seller_customer_common/auctions/", queries,
                true, new TypeToken<ArrayList<Auction>>(){}.getType());
        System.out.println(response.size());
        for(Auction auction : response){
            setAuctionItem(auction, client.getRole().equals("Seller"));
        }
    }

    private void removeAddAuctionButton() {
        if (addAuction.getParent() != null)
            ((Pane)addAuction.getParent()).getChildren().remove(addAuction);
    }

    private void setAuctionItem(Auction auction, boolean isSeller) {
        try {
            AnchorPane item;
            if(isSeller) {
                item = FXMLLoader.load(getClass().
                        getResource("/layouts/seller_menus/sellers_auctions_menus/auction_item.fxml"));
            } else {
                item = FXMLLoader.load(getClass().
                        getResource("/layouts/customer_seller_common_menus/auctions_menus/auction_item.fxml"));
            }
            HBox hBox = (HBox) item.getChildren().get(0);
            ((Label) hBox.getChildren().get(0)).setText(auction.getId());
            ((Label) hBox.getChildren().get(1)).setText(auction.getDeadline().toString());
            ((Label) hBox.getChildren().get(2)).setText(String.valueOf(auction.getHighestPrice()));
            vBoxItems.getChildren().add(item);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleRefresh() {
        vBoxItems.getChildren().clear();
        loadAuctions();
    }

    public void handleAddAuction() {
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layouts/seller_menus/sellers_auctions_menus/add_auction.fxml"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 800, 600));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
