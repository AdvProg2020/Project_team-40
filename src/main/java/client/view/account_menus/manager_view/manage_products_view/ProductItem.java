package client.view.account_menus.manager_view.manage_products_view;

import client.controller.RequestHandler;
import client.view.MenuManager;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import server.model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ProductItem extends MenuManager implements Initializable {
    public JFXButton deleteProductButton;
    private HashMap<String, String> requestQueries;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        requestQueries = new HashMap<>();
    }

    private void loadProducts(VBox vBoxItems) {
        vBoxItems.getChildren().clear();
        ArrayList<?> products =(ArrayList<?>) RequestHandler.get("/accounts/manager_account_controller/products/", requestQueries, true, ArrayList.class);
        assert products != null;
        for (Object obj : products) {
            try {
                Product product = (Product)obj;
                AnchorPane item = (AnchorPane) FXMLLoader.load(getClass().getResource("/layouts/manager_menus/manager_products_menu/product_item.fxml"));
                HBox hBox = (HBox) item.getChildren().get(0);
                setLabelsContent(product, hBox);
                vBoxItems.getChildren().add(item);
            }
            catch (ResourceException e) {
                if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                    logout();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setLabelsContent(Product product, HBox hBox) {
        Label productIdLabel =((Label) hBox.getChildren().get(0));
        Label nameLabel =((Label) hBox.getChildren().get(1));
        Label companyLabel =((Label) hBox.getChildren().get(2));
        Label sellerLabel =((Label) hBox.getChildren().get(3));
        Label basePriceLabel =((Label) hBox.getChildren().get(4));
        Label priceLabel =((Label) hBox.getChildren().get(5));
        Label categoryLabel =((Label) hBox.getChildren().get(6));
        Label scoreLabel =((Label) hBox.getChildren().get(7));

        productIdLabel.setText(product.getProductId());
        nameLabel.setText(product.getName());
        companyLabel.setText(product.getCompany());
        sellerLabel.setText(product.getSellerUsername());
        basePriceLabel.setText(Double.toString(product.getBasePrice()));
        priceLabel.setText(Double.toString(product.getPrice()));
        categoryLabel.setText(product.getCategory());
        scoreLabel.setText(Double.toString(product.getAverageScore()));
    }



    public void handleDeleteProduct() {
        HBox item = (HBox)(deleteProductButton.getParent());
        VBox items =(VBox)(item.getParent()).getParent();
        String productId =((Label)item.getChildren().get(0)).getText();
        try {
            requestQueries.put("productID", productId);
            RequestHandler.delete("/accounts/manager_account_controller/product/", requestQueries, true, null);
        } catch (ResourceException e) {
            if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                logout();
        }
        Platform.runLater(() -> loadProducts(items));
    }

}
