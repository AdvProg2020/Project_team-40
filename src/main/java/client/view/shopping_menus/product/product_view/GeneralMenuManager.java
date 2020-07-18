package client.view.shopping_menus.product.product_view;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.restlet.resource.ResourceException;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
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
        if (!Client.getInstance().isLoggedIn() || Client.getInstance().getRole().equals("Customer")) {
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
            HashMap<String, String> queries = new HashMap<>();
            queries.put("username", Client.getInstance().getUsername());
            queries.put("count", Integer.toString(countSpinner.getValueFactory().getValue()));
            RequestHandler.put("/shop/cart/", ProductMenuManager.getProduct().getProductId(), queries, false, null);
        } catch(ResourceException e) {
            try {
                System.err.println(RequestHandler.getClientResource().getResponseEntity().getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
