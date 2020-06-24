package view.shopping_menus.products_and_offs_menus.products_view;

import controller.menus.AllProductsController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Product;
import view.MenuManager;
import view.shopping_menus.product.product_view.ProductMenuManager;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductItemManager extends MenuManager implements Initializable{
    private static final String PATH = "/images/";
    private Product product;

    @FXML
    private ImageView image;
    @FXML
    private Label name;
    @FXML
    private Label price;
    @FXML
    private Label score;
    @FXML
    private Button digest;

    public ProductItemManager(){
        initializeProduct();
    }

    private void initializeProduct(){
        product = AllProductsController.getInstance().getAllProducts().get(ProductsMenuManager.getIndexOfLastUser());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        //TODO : test
        //Image frame = new Image(PATH + product.getName());
        //image.setImage(frame);

        digest.setOnAction(actionEvent -> {
            ProductMenuManager.setProduct(product);
            setMainInnerPane("/layouts/shopping_menus/product_menu.fxml");
        });

        //set other properties of product
        if(product != null){
            name.setText(product.getName());
            price.setText(Double.toString(product.getPrice()));
            score.setText(Double.toString(product.getAverageScore()));
        }
    }

}
