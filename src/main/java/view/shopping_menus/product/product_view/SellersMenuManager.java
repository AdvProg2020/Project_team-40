package view.shopping_menus.product.product_view;

import controller.menus.ProductController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import view.MenuManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SellersMenuManager extends MenuManager implements Initializable{

    public VBox sellersSection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //Add seller items to list
        ArrayList<String> sellers = ProductController.getInstance().getSellersForProduct(ProductMenuManager.getProduct().getName());
        for(String seller : sellers) {
            try {
                SellerItemManager.setLastSeller(seller);
                Node node = (Node) FXMLLoader.load(getClass().getResource("/layouts/shopping_menus/seller_item.fxml"));
                sellersSection.getChildren().add(node);
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
