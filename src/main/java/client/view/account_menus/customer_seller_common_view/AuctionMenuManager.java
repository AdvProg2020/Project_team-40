package client.view.account_menus.customer_seller_common_view;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import server.model.Auction;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AuctionMenuManager extends MenuManager implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HashMap<String, String> queries = new HashMap<>();
        Client client = Client.getInstance();
        if(client.getRole().equals("Seller"))
            queries.put("role", "seller");
        else
            queries.put("role", "customer");
        queries.put("username", client.getUsername());
        ArrayList<Auction> response = RequestHandler.get("/accounts/seller_customer_common/auctions/", queries,
                true, new TypeToken<ArrayList<Auction>>(){}.getType());
    }

    public void handleRefresh() {
    }

    public void handleAddAuction() {
    }
}
