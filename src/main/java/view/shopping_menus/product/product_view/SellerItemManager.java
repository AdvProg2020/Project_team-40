package view.shopping_menus.product.product_view;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SellerItemManager implements Initializable{

    private static String lastSeller;
    private String seller;

    public SellerItemManager(){
        seller = lastSeller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }

    public static void setLastSeller(String lastSeller){
        SellerItemManager.lastSeller = lastSeller;
    }
}
