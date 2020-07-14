package client.view.account_menus.seller_view.sellers_products_view;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import com.jfoenix.controls.JFXButton;
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
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import server.model.Product;
import server.model.users.Seller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SellersProductsManager extends MenuManager implements Initializable {
    public VBox vBoxItems;
    public JFXButton addButton;
    public AnchorPane anchorPane;
    private HashMap<String, String> requestQueries;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        requestQueries = new HashMap<>();
        if(!((Seller) Client.getInstance().getUser()).isManagerPermission())
            anchorPane.getChildren().remove(addButton);
        loadProducts();
        ProductItemManager.setVBoxItems(vBoxItems);
    }

    public void loadProducts() {
        try {
            requestQueries.clear();
            requestQueries.put("username", Client.getInstance().getUsername());
            ArrayList<Product> products = (ArrayList) RequestHandler.get("/accounts/seller_account_controller/products/", requestQueries, true, ArrayList.class);
            assert products != null;
            vBoxItems.getChildren().clear();
            for (Product product : products) {
                AnchorPane item = FXMLLoader.load(getClass().
                        getResource("/layouts/seller_menus/manage_product_menus/product_item.fxml"));
                HBox hBox = (HBox) item.getChildren().get(0);
                setLabelsContent(product, hBox);
                vBoxItems.getChildren().add(item);

            }
        }
        catch (ResourceException e){
            if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                logout();
        } catch (IOException e) {
            e.printStackTrace();
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
            stage.setScene(new Scene(root, 800, 600));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    loadProducts();
                }
            });
        }
    }
}
