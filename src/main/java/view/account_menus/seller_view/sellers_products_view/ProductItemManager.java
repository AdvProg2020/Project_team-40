package view.account_menus.seller_view.sellers_products_view;

import com.jfoenix.controls.JFXButton;
import controller.accounts.AccountController;
import controller.accounts.SellerAccountController;
import exceptions.AccountsException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Category;
import model.Product;
import view.MenuManager;

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
        HBox item = (HBox) viewProductButton.getParent().getParent();
        String productName =((Label)item.getChildren().get(0)).getText();
        Product product = Product.getProductWithSellerAndName(productName, accountController.getThisUser().getUsername());
        try {
            sellerAccountController.removeProductFromSeller(product.getProductId());
            vBoxItems.getChildren().remove(itemPane);
        } catch (AccountsException e) {
            e.printStackTrace();
        }
    }

    private void setLabelsContent(ProductView productView, Product product) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                productView.setProduct(product);
            }
        });
    }

    public void handleViewProduct() {
        //TODO:
    }

    public void handleEditProduct() {
        //TODO:
    }
}
