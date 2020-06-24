package view.account_menus.seller_view.sellers_products_view;

import controller.accounts.SellerAccountController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Product;
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

    public Label priceError;
    public Label quantityError;


    public VBox propertyList;
    private Product product;
    private SellerAccountController sellerAccountController;

    //TODO: ADD EDIT CATEGORY OPTION IF POSSIBLE

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
        PropertyManager.setProduct(product);

        addPropertiesToVBox();
    }

    private void addPropertiesToVBox() {
        HashMap<String, Double> extraValueProperties = product.getExtraValueProperties();
        HashMap<String, String> extraStringProperties = product.getExtraStringProperties();
        for(Map.Entry<String, Double> entry : extraValueProperties.entrySet()) {
            AnchorPane item = null;
            try {
                item = (AnchorPane) FXMLLoader.load(getClass().
                        getResource("/layouts/seller_menus/manage_product_menus/property_item.fxml"));
                HBox hBox = (HBox) item.getChildren().get(0);
                setLabelsContent(entry, hBox);
                propertyList.getChildren().add(item);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setLabelsContent(Map.Entry<String, Double> entry, HBox hBox) {
        Label propertyNameLabel =(Label) hBox.getChildren().get(0);
        Label propertyValueLabel = (Label) hBox.getChildren().get(1);
        propertyNameLabel.setText(entry.getKey());
        propertyValueLabel.setText(Double.toString(entry.getValue()));
    }

    public void handleCloseWindow(ActionEvent event) {
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }
}
