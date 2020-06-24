package view.account_menus.seller_view.sellers_products_view;

import controller.accounts.SellerAccountController;
import exceptions.AccountsException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
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

    public void loadProducts() {
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
        //TODO: SEE IF IT IS NEEDED TO SAVE THE ID OF THE PRODUCTS
        Label category = (Label) hBox.getChildren().get(1);
        category.setText(product.getCategory());
    }

    public void addProduct() {
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().
                    getResource("/layouts/seller_menus/manage_product_menus/add_product.fxml"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 1200, 600));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    loadProducts();
                }
            });
        }
    }
}
