package view.account_menus.seller_view.sellers_products_view;

import com.jfoenix.controls.JFXButton;
import controller.accounts.AccountController;
import controller.accounts.SellerAccountController;
import exceptions.AccountsException;
import javafx.fxml.FXMLLoader;
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

public class ProductItemManager extends MenuManager {
    private SellerAccountController sellerAccountController = SellerAccountController.getInstance();
    private AccountController accountController = AccountController.getInstance();
    public JFXButton viewProductButton;
    public static VBox vBoxItems;
    public AnchorPane itemPane;

    public static void setVBoxItems(VBox vBoxItems) {
        ProductItemManager.vBoxItems = vBoxItems;
    }

    public void handleDeleteProduct() {
       Product product = getProduct();
        try {
            sellerAccountController.removeProductFromSeller(product.getProductId());
            vBoxItems.getChildren().remove(itemPane);
        } catch (AccountsException e) {
            e.printStackTrace();
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
        return Product.getProductWithSellerAndName(productName, accountController.getThisUser().getUsername());
    }
}
