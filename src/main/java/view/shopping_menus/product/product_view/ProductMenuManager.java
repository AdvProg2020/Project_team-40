package view.shopping_menus.product.product_view;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Cart;
import model.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductMenuManager implements Initializable{
    public Text productName;
    public Text sellerName;
    public Text companyName;
    public Text rating;
    public Text category;
    public Spinner countSpinner;
    public Button cartButton;
    public VBox digestSection;
    public VBox commentsSection;
    public VBox sellerSection;

    private SpinnerValueFactory<Integer> spinnerValue;
    private int count = 0;

    private static Product product;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        product.increaseVisitCount();
        productName.setText(product.getName());
        sellerName.setText(product.getSellerUsername());
        companyName.setText(product.getCompany());
        category.setText(product.getCategory());
        rating.setText(product.getAverageScore() + " / 5");

        spinnerValue = new SpinnerValueFactory<Integer>(){
            @Override
            public void decrement(int i){
                count -= i;
                if(count < 0)
                    count = 0;
            }

            @Override
            public void increment(int i){
                count += i;
            }
        };
        countSpinner.setValueFactory(spinnerValue);

        cartButton.setOnAction(actionEvent -> {
            Cart.getThisCart().addProduct(product.getProductId(), count);
        });

    }

    public static void setProduct(Product product){
        ProductMenuManager.product = product;
    }
}
