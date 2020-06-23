package view.shopping_menus.product.product_view;

import controller.menus.ProductController;
import exceptions.MenuException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Cart;
import model.Comment;
import model.Product;

import java.io.IOException;
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
    public Button commentButton;
    public TextField titleField;
    public TextArea commentField;

    private SpinnerValueFactory<Integer> spinnerValue;
    private int count = 0;

    private static Product product;
    private static String productID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        product.increaseVisitCount();
        productName.setText(product.getName());
        sellerName.setText(product.getSellerUsername());
        companyName.setText(product.getCompany());
        category.setText(product.getCategory());
        rating.setText(product.getAverageScore() + " / 5");
        productID = product.getProductId();

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

        initializeDigest();
        initializeComments();
        initializeSellers();
    }

    private void initializeDigest(){
        digestSection.getChildren().add(new Text(product.getExplanation()));
    }

    private void initializeComments(){
        commentButton.setOnAction(actionEvent -> {
            try {
                ProductController.getInstance().addComment(productID, titleField.getText(), commentField.getText());
            } catch(MenuException e) {
                e.printStackTrace();
            }
        });

        try {
            for(Comment comment : ProductController.getInstance().getComments(productID)) {
                CommentItemManager.setLastComment(comment);
                Node node = (Node) FXMLLoader.load(getClass().getResource("/layouts/shopping_menus/comment_item.fxml"));
                commentsSection.getChildren().add(node);
            }
        } catch(MenuException e) {
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void initializeSellers(){
        for(String seller : ProductController.getInstance().getSellersForProduct(product.getName())) {
            try {
                SellerItemManager.setLastSeller(seller);
                Node node = (Node) FXMLLoader.load(getClass().getResource("/layouts/shopping_menus/seller_item.fxml"));
                sellerSection.getChildren().add(node);
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void setProduct(Product product){
        ProductMenuManager.product = product;
    }
}
