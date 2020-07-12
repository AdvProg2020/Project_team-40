package client.view.shopping_menus.product.product_view;

import client.view.ThisUser;
import com.jfoenix.controls.JFXButton;
import server.controller.menus.ProductController;
import exceptions.MenuException;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import server.model.users.Customer;
import server.model.users.User;
import client.view.MenuManager;

import java.net.URL;
import java.util.ResourceBundle;

public class GeneralMenuManager extends MenuManager implements Initializable{

    public Text productName, sellerName, companyName, category, rating, descriptionText, priceText;
    public Spinner<Integer> countSpinner;
    public ImageView imageView;
    public JFXButton cartButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //Set texts using product information
        productName.setText(ProductMenuManager.getProduct().getName());
        sellerName.setText(ProductMenuManager.getProduct().getSellerUsername());
        companyName.setText(ProductMenuManager.getProduct().getCompany());
        category.setText(ProductMenuManager.getProduct().getCategory());
        rating.setText(ProductMenuManager.getProduct().getAverageScore() + " / " + "5.0");
        descriptionText.setText(ProductMenuManager.getProduct().getExplanation());
        priceText.setText(ProductMenuManager.getProduct().getPrice() + " / " + ProductMenuManager.getProduct().getBasePrice());
        if (User.getUserByUsername(ThisUser.getUsername()) == null || User.getLoggedInUser() instanceof Customer) {
            //Work the functionality of product count
            SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, ProductMenuManager.getProduct().getCount(), 0);
            countSpinner.setValueFactory(spinnerValueFactory);
        }
        else {
            cartButton.setDisable(true);
            countSpinner.setDisable(true);
        }
        //Set image
        try {
            Image image = new Image(getClass().getResourceAsStream("/product_images/" + ProductMenuManager.getProduct().getName() + ".jpg"));
            imageView.setImage(image);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void handleCart(){
        try {
            ProductController.getInstance().addProductToCart(ProductMenuManager.getProduct().getProductId(), (Integer)countSpinner.getValueFactory().getValue());
        } catch(MenuException e) {
            e.printStackTrace();
        }
    }

}
