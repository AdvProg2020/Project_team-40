package client.view.account_menus.seller_view.sellers_products_view;

import client.view.MenuManager;
import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import server.model.Product;

public class PropertyManager extends MenuManager {
    public JFXButton editButton;
    public HBox hBox;
    public Label valueLabel;
    public Label propertyNameLabel;
    public TextField valueTexField;
    private static Product product;

    public static void setProduct(Product product) {
        PropertyManager.product = product;
    }

    public void handleEditProperty() {
        editButton.setOnMouseClicked(e -> saveChange());
        hBox.getChildren().remove(valueLabel);
        if(valueTexField == null)
            valueTexField = new TextField();
        valueTexField.setText(valueLabel.getText());
        hBox.getChildren().add(1, valueTexField);
    }

    private void saveChange() {
        editButton.setOnMouseClicked(e -> handleEditProperty());
        String propertyName = propertyNameLabel.getText();
        String newField = valueTexField.getText();
        try{
            double value = Double.parseDouble(newField);
            product.getExtraValueProperties().replace(propertyName, value);
        } catch (Exception e) {
            product.getExtraStringProperties().replace(propertyName, newField);
        }
        hBox.getChildren().remove(valueTexField);
        hBox.getChildren().add(1, valueLabel);
    }
}
