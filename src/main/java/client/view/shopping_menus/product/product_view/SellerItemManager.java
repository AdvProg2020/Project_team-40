package client.view.shopping_menus.product.product_view;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import server.model.Product;
import client.view.MenuManager;

import java.net.URL;
import java.util.ResourceBundle;

public class SellerItemManager extends MenuManager implements Initializable{

    private static String lastSeller;
    private String seller;
    public Text sellerText;
    public Text priceText;
    public Text statusText;
    public Button chooseButton;

    public SellerItemManager(){
        seller = lastSeller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //TODO : set image
        sellerText.setText(seller);
        Product product = Product.getProductWithSellerAndName(ProductMenuManager.getProduct().getName(), seller);
        priceText.setText(Double.toString(product.getPrice()));
        statusText.setText(product.getStatus().toString());

        chooseButton.setOnAction(actionEvent -> {
            ProductMenuManager.setProduct(product);
            setMainInnerPane("/layouts/shopping_menus/product_menu.fxml");
        });
    }

    public static void setLastSeller(String lastSeller){
        SellerItemManager.lastSeller = lastSeller;
    }
}
