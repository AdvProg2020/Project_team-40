package view.account_menus.seller_view.sellers_products_view;

import com.jfoenix.controls.JFXButton;
import controller.accounts.AccountController;
import controller.accounts.SellerAccountController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Category;
import model.Product;
import view.MenuManager;
import view.account_menus.manager_view.category_view.CategoryView;

public class ProductItemManager extends MenuManager {
    private SellerAccountController sellerAccountController = SellerAccountController.getInstance();
    private AccountController accountController = AccountController.getInstance();
    public JFXButton viewProductButton;

    public void handleDeleteProduct() {
        HBox item = (HBox) viewProductButton.getParent().getParent();
        String productName =((Label)item.getChildren().get(0)).getText();
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("/layouts/seller_menus/manage_product_menus/product_item.fxml"));
        try {
            Product product = Product.getProductWithSellerAndName(productName, accountController.getThisUser().getUsername());
            AnchorPane pane = loader.load();
            ProductView productView = loader.getController();
            setLabelsContent(productView, product);
            Stage userWindow = new Stage();
            userWindow.setScene(new Scene(pane, 1300, 550));
            userWindow.initModality(Modality.APPLICATION_MODAL);
            userWindow.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setLabelsContent(ProductView productView, Product product) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                productView.setProduct(product);
                productView.setNameLabel(product.getName());
                //TODO: SHOW ANY NEEDED INFORMATION
            }
        });
    }

    public void handleViewProduct() {

    }

    public void handleEditProduct() {
    }
}
