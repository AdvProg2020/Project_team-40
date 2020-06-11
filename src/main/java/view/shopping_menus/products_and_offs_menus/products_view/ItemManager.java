package view.shopping_menus.products_and_offs_menus.products_view;

import controller.menus.AllProductsController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import model.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemManager implements Initializable{
    private Product product;

    @FXML
    private ImageView image;
    @FXML
    private Label name;
    @FXML
    private Label price;
    @FXML
    private Label status;
    @FXML
    private Button digest;

    public ItemManager(){
        initializeProduct();
    }

    private void initializeProduct(){
        product = AllProductsController.getInstance().getAllProducts().get(ProductsMenuManager.getIndexOfLastUser());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //TODO : setImage and set button action

        //set other properties of product
        if(product != null){
            name.setText(product.getName());
            price.setText(Double.toString(product.getPrice()));
            status.setText(product.getStatus().toString());
        }
    }
}
