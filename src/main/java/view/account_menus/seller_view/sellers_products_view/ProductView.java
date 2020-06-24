package view.account_menus.seller_view.sellers_products_view;

import controller.accounts.SellerAccountController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Product;
import org.controlsfx.control.PropertySheet;
import view.MenuManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProductView extends MenuManager {
    public Label nameLabel;
    public Label iDLabel;
    public Label priceLabel;
    public Label companyLabel;
    public Label categoryLabel;
    public Label basePriceLabel;
    public Label quantityLabel;
    public Label statusLabel;
    public Label hasOffLabel;
    public Label explanationLabel;

    public VBox propertyList;
    private Product product;
    private SellerAccountController sellerAccountController;

    public void setProduct(Product product) {
        sellerAccountController = SellerAccountController.getInstance();
        this.product = product;
        nameLabel.setText(product.getName());
        iDLabel.setText(product.getProductId());
        priceLabel.setText(Double.toString(product.getPrice()));
        basePriceLabel.setText(Double.toString(product.getBasePrice()));
        quantityLabel.setText(Integer.toString(product.getCount()));
        statusLabel.setText(String.valueOf(product.getStatus()));
        hasOffLabel.setText(String.valueOf(product.isInOff()));
        explanationLabel.setText(product.getExplanation());
        categoryLabel.setText(product.getCategory());

        addPropertiesToVBox();
    }

    private void addPropertiesToVBox() {
        HashMap<String, Double> extraValueProperties = product.getExtraValueProperties();
        HashMap<String, String> extraStringProperties = product.getExtraStringProperties();
        for(Map.Entry<String, Double> entry : extraValueProperties.entrySet()) {
            AnchorPane item = null;
            try {
                item = (AnchorPane) FXMLLoader.load(getClass().
                        getResource("/layouts/manager_menus/manager_category_menus/category_item.fxml"));//TODO:CHANGE THE ADDRESS
                HBox hBox = (HBox) item.getChildren().get(0);
                setLabelsContent(entry, hBox);
                propertyList.getChildren().add(item);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setLabelsContent(Map.Entry<String, Double> entry, HBox hBox) {

    }

    public void handleCloseWindow() {
    }
}
