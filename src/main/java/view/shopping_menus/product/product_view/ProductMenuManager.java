package view.shopping_menus.product.product_view;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import controller.menus.ProductController;
import exceptions.MenuException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.Cart;
import model.Comment;
import model.Product;
import view.MenuManager;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ProductMenuManager extends MenuManager implements Initializable{
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
        digestSection.setSpacing(20);

        //add description
        Text title = new Text("Description :");
        title.setStyle("-fx-font-size: 28");
        digestSection.getChildren().add(title);
        digestSection.getChildren().add(new Text(product.getExplanation()));

        //add attributes
        Text title2 = new Text("Attributes :");
        title2.setStyle("-fx-font-size: 28");
        digestSection.getChildren().add(title2);

        JFXTreeTableView<Attribute> treeView = new JFXTreeTableView<>();
        digestSection.getChildren().add(treeView);

        JFXTreeTableColumn<Attribute, String> nameCol = new JFXTreeTableColumn<>("Attribute name");
        nameCol.setPrefWidth(400);
        nameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Attribute, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Attribute, String> param){
                return param.getValue().getValue().name;
            }
        });

        JFXTreeTableColumn<Attribute, String> valueCol = new JFXTreeTableColumn<>("Attribute value");
        valueCol.setPrefWidth(400);
        valueCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Attribute, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Attribute, String> param){
                return param.getValue().getValue().value;
            }
        });

        ObservableList<Attribute> attributes = FXCollections.observableArrayList();
        try {
            HashMap<String, String> attributesHashMap = ProductController.getInstance().getProductAttributes(productID);
            for(Map.Entry<String, String> entry : attributesHashMap.entrySet()) {
                Attribute attribute = new Attribute(entry.getKey(), entry.getValue());
                attributes.add(attribute);
                System.out.println(attribute);
            }
        } catch(MenuException e) {
            e.printStackTrace();
        }

        final TreeItem<Attribute> root = new RecursiveTreeItem<>(attributes, RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(nameCol, valueCol);
        treeView.setRoot(root);
        treeView.setShowRoot(false);

    }

    class Attribute extends RecursiveTreeObject<Attribute> {
        StringProperty name;
        StringProperty value;

        public Attribute(String name, String value){
            this.name = new SimpleStringProperty(name);
            if(value == null)
                value = "";
            this.value = new SimpleStringProperty(value);
        }

        @Override
        public String toString(){
            return "Attribute{" +
                    "name=" + name +
                    ", value=" + value +
                    '}';
        }
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

    public static Product getProduct(){
        return product;
    }
}
