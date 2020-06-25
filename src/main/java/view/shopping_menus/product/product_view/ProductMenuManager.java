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



//    public VBox digestSection;
//    public VBox commentsSection;
//    public VBox sellerSection;
//    public Button commentButton;
//    public TextField titleField;
//    public TextArea commentField;
//    public JFXTreeTableView<Attribute> attributesTreeView;
//
    private static Product product;
    private static String productID;

    public Pane secondaryInnerPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
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

    //    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle){
//
//        product.increaseVisitCount();
//        productName.setText(product.getName());
//        sellerName.setText(product.getSellerUsername());
//        companyName.setText(product.getCompany());
//        category.setText(product.getCategory());
//        rating.setText(product.getAverageScore() + " / 5");
//        productID = product.getProductId();
//

//

//
//        initializeDigest();
//        initializeComments();
//        initializeSellers();
//    }
//
//    private void initializeDigest(){
//
//        descriptionText.setText(product.getExplanation());
//
//        JFXTreeTableColumn<Attribute, String> nameCol = new JFXTreeTableColumn<>("Attribute name");
//        nameCol.setPrefWidth(500);
//        nameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Attribute, String>, ObservableValue<String>>(){
//            @Override
//            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Attribute, String> param){
//                return param.getValue().getValue().name;
//            }
//        });
//
//        JFXTreeTableColumn<Attribute, String> valueCol = new JFXTreeTableColumn<>("Attribute value");
//        valueCol.setPrefWidth(500);
//        valueCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Attribute, String>, ObservableValue<String>>(){
//            @Override
//            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Attribute, String> param){
//                return param.getValue().getValue().value;
//            }
//        });
//
//        ObservableList<Attribute> attributes = FXCollections.observableArrayList();
//        try {
//            HashMap<String, String> attributesHashMap = ProductController.getInstance().getProductAttributes(productID);
//            for(Map.Entry<String, String> entry : attributesHashMap.entrySet()) {
//                Attribute attribute = new Attribute(entry.getKey(), entry.getValue());
//                attributes.add(attribute);
//            }
//        } catch(MenuException e) {
//            e.printStackTrace();
//        }
//
//        final TreeItem<Attribute> root = new RecursiveTreeItem<>(attributes, RecursiveTreeObject::getChildren);
//        attributesTreeView.getColumns().setAll(nameCol, valueCol);
//        attributesTreeView.setRoot(root);
//        attributesTreeView.setShowRoot(false);
//        attributesTreeView.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
//    }
//
//    class Attribute extends RecursiveTreeObject<Attribute> {
//        StringProperty name;
//        StringProperty value;
//
//        public Attribute(String name, String value){
//            this.name = new SimpleStringProperty(name);
//            if(value == null)
//                value = "-";
//            this.value = new SimpleStringProperty(value);
//        }
//    }
//
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
