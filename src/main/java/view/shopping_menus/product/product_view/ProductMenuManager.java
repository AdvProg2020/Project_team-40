package view.shopping_menus.product.product_view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import controller.menus.ProductController;
import exceptions.MenuException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.Cart;
import model.Comment;
import model.Product;
import view.MenuManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ProductMenuManager extends MenuManager implements Initializable{



//    public VBox commentsSection;
//    public VBox sellerSection;
//    public Button commentButton;
//    public TextField titleField;
//    public TextArea commentField;

    private static Product product;
    private static String productID;

    public Pane secondaryInnerPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        product.increaseVisitCount();
        setSecondaryPane(secondaryInnerPane);
        setSecondaryInnerPane("/layouts/shopping_menus/general_menu.fxml");
    }

    public void goToGeneral(){
        setSecondaryInnerPane("/layouts/shopping_menus/general_menu.fxml");
    }
    public void goToDigest(){
        setSecondaryInnerPane("/layouts/shopping_menus/digest_menu.fxml");
    }
    public void goToComments(){
        setSecondaryInnerPane("/layouts/shopping_menus/comments_menu.fxml");
    }
    public void goToAddComments(){
        setSecondaryInnerPane("/layouts/shopping_menus/add_comment_menu.fxml");
    }
    public void goToSellers(){
        setSecondaryInnerPane("/layouts/shopping_menus/sellers_menu.fxml");
    }
    public void goToRateProduct(){
        setSecondaryInnerPane("/layouts/shopping_menus/rate_product_menu.fxml");
    }
    public void goToSimilarProducts(){
        setSecondaryInnerPane("/layouts/shopping_menus/similar_products_menu.fxml");
    }


//    private void initializeComments(){
//        //Initialize submit comment section
//        commentButton.setOnAction(actionEvent -> {
//            try {
//                ProductController.getInstance().addComment(productID, titleField.getText(), commentField.getText());
//            } catch(MenuException e) {
//                e.printStackTrace();
//            }
//        });
//
//        //Display previous comments
//        ArrayList<Comment> comments = null;
//        try {
//             comments = ProductController.getInstance().getComments(productID);
//        } catch(MenuException e) {
//            e.printStackTrace();
//        }
//        try {
//            for(Comment comment : comments) {
//                CommentItemManager.setLastComment(comment);
//                Node node = (Node) FXMLLoader.load(getClass().getResource("/layouts/shopping_menus/comment_item.fxml"));
//                commentsSection.getChildren().add(node);
//            }
//        } catch(IOException e){
//            e.printStackTrace();
//        }
//    }
//
//    private void initializeSellers(){
//        ArrayList<String> sellers = ProductController.getInstance().getSellersForProduct(product.getName());
//        for(String seller : sellers) {
//            try {
//                SellerItemManager.setLastSeller(seller);
//                Node node = (Node) FXMLLoader.load(getClass().getResource("/layouts/shopping_menus/seller_item.fxml"));
//                sellerSection.getChildren().add(node);
//            } catch(IOException e){
//                e.printStackTrace();
//            }
//        }
//    }
//
    public static void setProduct(Product product){
        ProductMenuManager.product = product;
    }

    public static Product getProduct(){
        return product;
    }
}
