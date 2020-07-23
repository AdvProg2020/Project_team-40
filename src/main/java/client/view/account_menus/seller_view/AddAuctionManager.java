package client.view.account_menus.seller_view;

import client.controller.Client;
import client.controller.RequestHandler;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import server.model.Product;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AddAuctionManager implements Initializable {

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

    }

    public void addAuction() {

    }

}
