package client.view.account_menus.seller_view.accounts_view;

import client.controller.Client;
import client.controller.RequestHandler;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import server.model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AddAuctionManager implements Initializable {
    public VBox vBoxItems;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HashMap<String, String> queries = new HashMap<>();
        queries.put("username", Client.getInstance().getUsername());
        ArrayList<Product> products = RequestHandler.get("/accounts/seller_account_controller/products/", queries,
                true, new TypeToken<ArrayList<Product>>(){}.getType());
        for(Product product : products) {
            setProductsItem(product);
        }
    }

    private void setProductsItem(Product product) {
        try {
            AnchorPane item = FXMLLoader.load(getClass().
                    getResource("/layouts/seller_menus/sellers_auctions_menus/product_item.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addAuction() {

    }

}
