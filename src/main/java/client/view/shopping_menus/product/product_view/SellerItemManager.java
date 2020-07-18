package client.view.shopping_menus.product.product_view;

import client.controller.RequestHandler;
import client.view.MenuManager;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.restlet.resource.ResourceException;
import server.model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
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
        try {


            //TODO : set image
            sellerText.setText(seller);
            HashMap<String, String> queries = new HashMap<>();
            queries.put("name", ProductMenuManager.getProduct().getName());
            queries.put("sellerName", seller);
            Product product = RequestHandler.get("/shop/product/seller", queries, false, Product.class);
            priceText.setText(Double.toString(product.getPrice()));
            statusText.setText(product.getStatus().toString());

            chooseButton.setOnAction(actionEvent -> {
                ProductMenuManager.setProduct(product);
                setMainInnerPane("/layouts/shopping_menus/product_menu.fxml");
            });
        }
        catch(ResourceException e) {
            try {
                System.err.println(RequestHandler.getClientResource().getResponseEntity().getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void setLastSeller(String lastSeller){
        SellerItemManager.lastSeller = lastSeller;
    }
}
