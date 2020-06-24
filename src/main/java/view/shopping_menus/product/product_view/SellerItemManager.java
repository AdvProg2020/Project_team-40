package view.shopping_menus.product.product_view;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.Product;
import view.MenuManager;

import java.net.URL;
import java.util.ResourceBundle;

public class SellerItemManager extends MenuManager implements Initializable{

    private static String lastSeller;
    private String seller;
    public ImageView imageView;
    public Text sellerName;
    public Button chooseButton;

    public SellerItemManager(){
        seller = lastSeller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //TODO : set image
        sellerName.setText(lastSeller);

        chooseButton.setOnAction(actionEvent -> {
            ProductMenuManager.setProduct(Product.getProductWithSellerAndName(ProductMenuManager.getProduct().getName(), seller));
            setMainInnerPane("/layouts/shopping_menus/product_menu.fxml");
        });
    }

    public static void setLastSeller(String lastSeller){
        SellerItemManager.lastSeller = lastSeller;
    }
}