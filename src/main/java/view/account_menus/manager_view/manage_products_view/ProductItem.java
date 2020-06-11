package view.account_menus.manager_view.manage_products_view;

import com.jfoenix.controls.JFXButton;
import controller.accounts.ManagerAccountController;
import exceptions.AccountsException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductItem implements Initializable {
    public JFXButton deleteProductButton;
    private ManagerAccountController managerAccountController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        managerAccountController = ManagerAccountController.getInstance();
    }
    private void loadProducts(VBox vBoxItems) {
        vBoxItems.getChildren().clear();
        for (String productId : managerAccountController.getAllProducts()) {
            try {
                Product product = Product.getProductById(productId);
                AnchorPane item = (AnchorPane) FXMLLoader.load(getClass().getResource("/layouts/empty_layouts/product_item.fxml"));
                HBox hBox = (HBox) item.getChildren().get(0);
                setLabelsContent(product, hBox);
                vBoxItems.getChildren().add(item);
            }
            catch (Exception e) {
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



    public void handleDeleteProduct(ActionEvent event) {
        HBox item = (HBox)(deleteProductButton.getParent());
        VBox items =(VBox)(item.getParent()).getParent();
        String productId =((Label)item.getChildren().get(0)).getText();
        try {
            managerAccountController.removeProduct(productId);
        } catch (AccountsException e) {
            System.err.println(e.getMessage());
        }
        Platform.runLater(() -> loadProducts(items));
    }

}
