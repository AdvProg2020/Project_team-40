package view.shopping_menus.product.product_view;

import controller.menus.ProductController;
import exceptions.MenuException;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import view.MenuManager;

import java.net.URL;
import java.util.ResourceBundle;

public class GeneralMenuManager extends MenuManager implements Initializable{

    public Text productName, sellerName, companyName, category, rating, descriptionText;
    public Spinner countSpinner;
    public ImageView imageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //Set texts using product information
        productName.setText(ProductMenuManager.getProduct().getName());
        sellerName.setText(ProductMenuManager.getProduct().getSellerUsername());
        companyName.setText(ProductMenuManager.getProduct().getCompany());
        category.setText(ProductMenuManager.getProduct().getCategory());
        rating.setText(Double.toString(ProductMenuManager.getProduct().getAverageScore()));
        descriptionText.setText(ProductMenuManager.getProduct().getExplanation());

        //Work the functionality of product count
        SpinnerValueFactory spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, ProductMenuManager.getProduct().getCount(), 0);
        countSpinner.setValueFactory(spinnerValueFactory);

        //Set image
        try {
            Image image = new Image(getClass().getResourceAsStream("/product_images/" + ProductMenuManager.getProduct().getName() + ".jpg"));
            imageView.setImage(image);
        }catch(Exception e){
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
