package client.view.account_menus.customer_seller_common_view;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import server.model.Auction;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AuctionMenuManager extends MenuManager implements Initializable {
    public VBox vBoxItems;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAuctions();
    }

    private void loadAuctions() {
        HashMap<String, String> queries = new HashMap<>();
        Client client = Client.getInstance();
        if(client.getRole().equals("Seller"))
            queries.put("role", "seller");
        else
            queries.put("role", "customer");
        queries.put("username", client.getUsername());
        ArrayList<Auction> response = RequestHandler.get("/accounts/seller_customer_common/auctions/", queries,
                true, new TypeToken<ArrayList<Auction>>(){}.getType());
        for(Auction auction : response){
            setAuctionItem(auction, client.getRole().equals("Seller"));
        }
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
            ((Label) hBox.getChildren().get(0)).setText(auction.getProductName());
            ((Label) hBox.getChildren().get(1)).setText(auction.getDeadline().toString());
            ((Label) hBox.getChildren().get(2)).setText(String.valueOf(auction.getHighestPrice()));
            vBoxItems.getChildren().add(item);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleRefresh() {
        loadAuctions();
    }

    public void handleAddAuction() {
        setSecondaryInnerPane("/layouts/seller_menus/seller_auctions_menus/add_auction.fxml");
    }
}
