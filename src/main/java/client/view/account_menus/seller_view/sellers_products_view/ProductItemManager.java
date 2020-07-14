package client.view.account_menus.seller_view.sellers_products_view;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import server.model.Product;

import java.io.IOException;
import java.util.HashMap;

public class ProductItemManager extends MenuManager {
    public JFXButton viewProductButton;
    public static VBox vBoxItems;
    public AnchorPane itemPane;

    public static void setVBoxItems(VBox vBoxItems) {
        ProductItemManager.vBoxItems = vBoxItems;
    }

    public void handleDeleteProduct() {
       Product product = getProduct();
        try {
            HashMap<String, String> queries = new HashMap<>();
            queries.put("username", Client.getInstance().getUsername());
            queries.put("productID", product.getProductId());
            RequestHandler.delete("/accounts/seller_account_controller/product/", queries, true, null);
            vBoxItems.getChildren().remove(itemPane);
        } catch (ResourceException e) {
            if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                logout();
        }
    }

    public void handleViewProduct() {
        ProductView.setProduct(getProduct());
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layouts/seller_menus/manage_product_menus/product.fxml"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 1197, 540));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Product getProduct() {
        HBox item = (HBox) viewProductButton.getParent().getParent();
        String productName =((Label)item.getChildren().get(0)).getText();
        return Product.getProductWithSellerAndName(productName, Client.getInstance().getUsername());
    }
}
