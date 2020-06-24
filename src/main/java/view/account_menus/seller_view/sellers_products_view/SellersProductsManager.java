package view.account_menus.seller_view.sellers_products_view;

import controller.accounts.SellerAccountController;
import exceptions.AccountsException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Product;
import view.MenuManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SellersProductsManager extends MenuManager implements Initializable {
    private SellerAccountController sellerAccountController;
    public VBox vBoxItems;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sellerAccountController = SellerAccountController.getInstance();
        loadProducts();
    }

    private void loadProducts() {
        ArrayList<String> productIds = sellerAccountController.getSellerProductIDs();
        vBoxItems.getChildren().clear();
        for(String productId : productIds) {
            try {
                Product product = sellerAccountController.getProductDetails(productId);
                AnchorPane item = (AnchorPane) FXMLLoader.load(getClass().
                        getResource("/layouts/seller_menus/manage_product_menus/product_item.fxml"));
                HBox hBox = (HBox) item.getChildren().get(0);
                setLabelsContent(product, hBox);
                vBoxItems.getChildren().add(item);
            } catch (AccountsException | IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void setLabelsContent(Product product, HBox hBox) {
        Label productName = (Label) hBox.getChildren().get(0);
        productName.setText(product.getName());
        Label category = (Label) hBox.getChildren().get(1);
        category.setText(product.getCategory());
    }

    public void handleRefresh() {

    }

    public void addProduct() {

    }

}
