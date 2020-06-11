package view.account_menus.manager_view.manage_products_view;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Product;
import model.users.User;
import view.MenuManager;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageProductsManager extends MenuManager implements Initializable {
    public VBox vBoxItems;
    private controller.accounts.ManagerAccountController managerAccountController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        managerAccountController = controller.accounts.ManagerAccountController.getInstance();
        loadProducts();
    }

    private void loadProducts() {
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

}
