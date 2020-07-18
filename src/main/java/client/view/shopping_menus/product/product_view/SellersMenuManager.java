package client.view.shopping_menus.product.product_view;

import client.controller.RequestHandler;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import client.view.MenuManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SellersMenuManager extends MenuManager implements Initializable{

    public VBox sellersSection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //Add seller items to list
        HashMap<String, String> queries = new HashMap<>();
        queries.put("name", ProductMenuManager.getProduct().getName());
        ArrayList<String> sellers = RequestHandler.get("/shop/product/sellers/", queries, false,
                new TypeToken<ArrayList<String>>(){}.getType());
        assert sellers != null;
        for(String seller : sellers) {
            try {
                SellerItemManager.setLastSeller(seller);
                Node node = FXMLLoader.load(getClass().getResource("/layouts/shopping_menus/seller_item.fxml"));
                sellersSection.getChildren().add(node);
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
