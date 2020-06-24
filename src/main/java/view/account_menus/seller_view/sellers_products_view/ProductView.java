package view.account_menus.seller_view.sellers_products_view;

import controller.accounts.SellerAccountController;
import exceptions.AccountsException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
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

    public void changeName() {
        editName.setText("save");
        editName.setOnMouseClicked(e -> saveName());
        if(nameField == null)
            nameField = new TextField();
        informationTable.add(nameField, 2, 0);
        nameField.setText(product.getName());
    }

    private void saveName() {
        if(!nameField.getText().isBlank()) {
            editName.setText("edit");
            try {
                sellerAccountController.editProduct(product.getProductId(), "name", nameField.getText(),
                        product.getExtraValueProperties(), product.getExtraStringProperties());
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
        nameField.setText(product.getCompany());
    }

    private void saveCompany() {
        if(!companyField.getText().isBlank()) {
            editCompany.setText("edit");
            try {
                sellerAccountController.editProduct(product.getProductId(), "company", companyField.getText(),
                        product.getExtraValueProperties(), product.getExtraStringProperties());
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
        priceField.setText(Double.toString(product.getPrice()));
    }

    private void savePrice() {
        if(!priceField.getText().isBlank()) {
            try {
                Double.parseDouble(priceField.getText());
                sellerAccountController.editProduct(product.getProductId(), "price", priceField.getText(),
                        product.getExtraValueProperties(), product.getExtraStringProperties());
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
        quantityField.setText(Double.toString(product.getCount()));
    }

    private void saveQuantity(boolean isSubtraction) {
        if(!quantityField.getText().isBlank()) {
            try {
                int count = Integer.parseInt(quantityField.getText());
                if(isSubtraction) {
                    sellerAccountController.decreaseProductCount(count, product.getProductId());
                    quantityLabel.setText(Integer.toString(Integer.parseInt(quantityLabel.getText()) - count));
                } else {
                    sellerAccountController.increaseProductsCount(count, product.getProductId());
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
    }

    private void saveStatus() {
        try {
            if (editButton.isSelected()) {
                sellerAccountController.editProduct(product.getProductId(), "status", "editing",
                        product.getExtraValueProperties(), product.getExtraStringProperties());
            } else if(confirmButton.isSelected()) {
                sellerAccountController.editProduct(product.getProductId(), "status", "confirmed",
                        product.getExtraValueProperties(), product.getExtraStringProperties());
            } else {
                sellerAccountController.editProduct(product.getProductId(), "status", "creating",
                        product.getExtraValueProperties(), product.getExtraStringProperties());
            }
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
