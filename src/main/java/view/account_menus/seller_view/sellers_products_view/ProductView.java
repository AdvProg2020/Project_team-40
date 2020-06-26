package view.account_menus.seller_view.sellers_products_view;

import controller.accounts.SellerAccountController;
import exceptions.AccountsException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Product;
import view.MenuManager;

import javax.security.auth.login.AccountException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ProductView extends MenuManager implements Initializable {
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

    public Label nameError;
    public Label companyError;
    public Label priceError;
    public Label quantityError;

    public Button editName;
    public Button editCompany;
    public Button editPrice;
    public Button editQuantityAdd;
    private Button editQuantitySubtract;
    public Button editStatus;

    private ToggleGroup statusOptions;
    private VBox radioButtons;
    private RadioButton createButton;
    private RadioButton confirmButton;
    private RadioButton editButton;

    private TextField nameField;
    private TextField companyField;
    private TextField priceField;
    private TextField quantityField;

    public GridPane informationTable;
    public VBox propertyList;
    private SellerAccountController sellerAccountController;
    private static Product product;
    private Product thisProduct;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sellerAccountController = SellerAccountController.getInstance();
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
        thisProduct = product;

        addPropertiesToVBox();
    }

    //TODO: ADD EDIT CATEGORY OPTION IF POSSIBLE

    public static void setProduct(Product product) {
        ProductView.product = product;
    }

    private void addPropertiesToVBox() {
        HashMap<String, Double> extraValueProperties = thisProduct.getExtraValueProperties();
        HashMap<String, String> extraStringProperties = thisProduct.getExtraStringProperties();
        for(Map.Entry<String, Double> entry : extraValueProperties.entrySet()) {
            setLabelsContent(entry.getKey(), Double.toString(entry.getValue()));
        }
        for(Map.Entry<String, String> entry : extraStringProperties.entrySet()) {
            setLabelsContent(entry.getKey(), entry.getValue());
        }
    }

    private void setLabelsContent(String propertyName, String property) {
        try {
            AnchorPane item = FXMLLoader.load(getClass().
                    getResource("/layouts/seller_menus/manage_product_menus/property_item.fxml"));
        HBox hBox = (HBox) item.getChildren().get(0);
        Label propertyNameLabel =(Label) hBox.getChildren().get(0);
        Label propertyValueLabel = (Label) hBox.getChildren().get(1);
        propertyNameLabel.setText(propertyName);
        propertyValueLabel.setText(property);
        propertyList.getChildren().add(item);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleCloseWindow(ActionEvent event) {
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }

    public void changeName() {
        editName.setText("save");
        editName.setOnMouseClicked(e -> saveName());
        if(nameField == null)
            nameField = new TextField();
        informationTable.add(nameField, 2, 0);
        nameField.setText(thisProduct.getName());
    }

    private void saveName() {
        if(!nameField.getText().isBlank()) {
            editName.setText("edit");
            try {
                sellerAccountController.editProduct(thisProduct.getProductId(), "name", nameField.getText(),
                        thisProduct.getExtraValueProperties(), thisProduct.getExtraStringProperties());
                informationTable.getChildren().remove(nameField);
                nameLabel.setText(nameField.getText());
                nameField.setText("");
                editName.setOnMouseClicked(e -> changeName());
                nameError.setText("Wait for manager's acceptance!");
            } catch (AccountsException e) {
                nameError.setText(e.getMessage());
            }
        } else {
            nameError.setText("Fill this field!");
        }
    }

    public void changeCompany() {
        editCompany.setText("save");
        editCompany.setOnMouseClicked(e -> saveCompany());
        if(companyField == null)
            companyField = new TextField();
        informationTable.add(companyField, 2, 2);
        companyField.setText(thisProduct.getCompany());
    }

    private void saveCompany() {
        if(!companyField.getText().isBlank()) {
            editCompany.setText("edit");
            try {
                sellerAccountController.editProduct(thisProduct.getProductId(), "company", companyField.getText(),
                        thisProduct.getExtraValueProperties(), thisProduct.getExtraStringProperties());
                informationTable.getChildren().remove(companyField);
                companyLabel.setText(companyField.getText());
                companyField.setText("");
                editCompany.setOnMouseClicked(e -> changeCompany());
                nameError.setText("Wait for manager's acceptance!");
            } catch (AccountsException e) {
                companyError.setText(e.getMessage());
            }
        } else {
            companyError.setText("Fill this field!");
        }
    }

    public void changePrice() {
        editPrice.setText("save");
        editPrice.setOnMouseClicked(e -> savePrice());
        if(priceField == null)
            priceField = new TextField();
        informationTable.add(priceField, 2, 4);
        priceField.setText(Double.toString(thisProduct.getPrice()));
    }

    private void savePrice() {
        if(!priceField.getText().isBlank()) {
            try {
                Double.parseDouble(priceField.getText());
                sellerAccountController.editProduct(thisProduct.getProductId(), "price", priceField.getText(),
                        thisProduct.getExtraValueProperties(), thisProduct.getExtraStringProperties());
                informationTable.getChildren().remove(priceField);
                priceLabel.setText(priceField.getText());
                priceField.setText("");
                editPrice.setOnMouseClicked(e -> changePrice());
                priceError.setText("");
            } catch(NumberFormatException e) {
                priceError.setText("Enter a valid number!");
            } catch (AccountsException e) {
                priceError.setText(e.getMessage());
            }
        } else {
            priceError.setText("Fill this field!");
        }
    }

    public void changeQuantity() {
        editQuantityAdd.setText("add");
        if(editQuantitySubtract == null)
            editQuantitySubtract = new Button("cut");
        editQuantitySubtract.setOnMouseClicked(e -> saveQuantity(true));
        editQuantityAdd.setOnMouseClicked(e -> saveQuantity(false));
        informationTable.add(editQuantitySubtract, 5, 5);
        if(quantityField == null)
            quantityField = new TextField();
        informationTable.add(quantityField, 2, 5);
    }

    private void saveQuantity(boolean isSubtraction) {
        if(!quantityField.getText().isBlank()) {
            try {
                int count = Integer.parseInt(quantityField.getText());
                if(isSubtraction) {
                    sellerAccountController.decreaseProductCount(count, thisProduct.getProductId());
                    quantityLabel.setText(Integer.toString(Integer.parseInt(quantityLabel.getText()) - count));
                } else {
                    sellerAccountController.increaseProductsCount(count, thisProduct.getProductId());
                    quantityLabel.setText(Integer.toString(Integer.parseInt(quantityLabel.getText()) + count));
                }
                informationTable.getChildren().remove(quantityField);
                informationTable.getChildren().remove(editQuantitySubtract);
                quantityField.setText("");
                editQuantitySubtract.setOnMouseClicked(e -> changeQuantity());
                editQuantityAdd.setOnMouseClicked(e -> changeQuantity());
                quantityError.setText("");
            } catch(NumberFormatException e) {
                quantityError.setText("Enter a valid number!");
            } catch (AccountException e) {
                quantityError.setText(e.getMessage());
            }
        } else {
            quantityError.setText("Fill this field!");
        }
    }

    public void changeStatus() {
        if(statusOptions == null)
            initializeStatusOptions();
        informationTable.add(radioButtons, 2, 6);
        editStatus.setOnMouseClicked(e -> saveStatus());
        editStatus.setText("save");
        informationTable.getChildren().remove(statusLabel);
    }

    private void saveStatus() {
        try {
            if (editButton.isSelected()) {
                sellerAccountController.editProduct(thisProduct.getProductId(), "status", "editing",
                        thisProduct.getExtraValueProperties(), thisProduct.getExtraStringProperties());
                statusLabel.setText("editing");
            } else if(confirmButton.isSelected()) {
                sellerAccountController.editProduct(thisProduct.getProductId(), "status", "confirmed",
                        thisProduct.getExtraValueProperties(), thisProduct.getExtraStringProperties());
                statusLabel.setText("confirmed");
            } else {
                sellerAccountController.editProduct(thisProduct.getProductId(), "status", "creating",
                        thisProduct.getExtraValueProperties(), thisProduct.getExtraStringProperties());
                statusLabel.setText("creating");
            }
            informationTable.add(statusLabel, 2, 6);
            informationTable.getChildren().remove(radioButtons);
            editStatus.setText("edit");
            editStatus.setOnMouseClicked(e -> changeStatus());
        } catch (AccountsException e) {}
    }

    private void initializeStatusOptions() {
        statusOptions = new ToggleGroup();
        createButton = new RadioButton("creating");
        statusOptions.getToggles().add(createButton);
        editButton = new RadioButton("editing");
        statusOptions.getToggles().add(editButton);
        confirmButton = new RadioButton("confirmed");
        statusOptions.getToggles().add(confirmButton);
        radioButtons = new VBox();
        radioButtons.getChildren().add(0, createButton);
        radioButtons.getChildren().add(1, editButton);
        radioButtons.getChildren().add(2, confirmButton);
    }
}
