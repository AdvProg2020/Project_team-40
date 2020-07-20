package client.view.account_menus.seller_view.sellers_products_view;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.ChangeListener;
import client.view.MenuManager;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
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
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import server.model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    private static Product product;
    private Product thisProduct;
    private HashMap<String, String> requestQueries;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTextFieldsListeners();
        requestQueries = new HashMap<>();
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

    private void addTextFieldsListeners() {
        nameField.textProperty().addListener(new ChangeListener(30, nameField));
        companyField.textProperty().addListener(new ChangeListener(50, companyField));
        priceField.textProperty().addListener(new ChangeListener(20, priceField));
    }

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

    private void handleEditProduct(String field, String newField){
        requestQueries.clear();
        ArrayList<HashMap> properties = new ArrayList<>();
        properties.add(thisProduct.getExtraStringProperties());
        properties.add(thisProduct.getExtraValueProperties());
        requestQueries.put("username", Client.getInstance().getUsername());
        requestQueries.put("productID", this.thisProduct.getProductId());
        requestQueries.put("field", field);
        requestQueries.put("newField", newField);

        String entity = new YaGson().toJson(properties, new TypeToken<ArrayList<HashMap>>(){}.getType());
        RequestHandler.put("/accounts/seller_account_controller/product/", entity, requestQueries, true, null);
    }

    private void saveName() {
        if(!nameField.getText().isBlank()) {
            editName.setText("edit");
            try {
                handleEditProduct("name", nameField.getText());
                informationTable.getChildren().remove(nameField);
                nameLabel.setText(nameField.getText());
                nameField.setText("");
                editName.setOnMouseClicked(e -> changeName());
                nameError.setText("Wait for manager's acceptance!");
            } catch (ResourceException e) {
                if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                    logout();
                else {
                    try {
                        nameError.setText(RequestHandler.getClientResource().getResponseEntity().getText());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
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
                handleEditProduct("company", companyField.getText());
                informationTable.getChildren().remove(companyField);
                companyLabel.setText(companyField.getText());
                companyField.setText("");
                editCompany.setOnMouseClicked(e -> changeCompany());
                nameError.setText("Wait for manager's acceptance!");
            } catch (ResourceException e) {
                if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                    logout();
                else {
                    try {
                        companyError.setText(RequestHandler.getClientResource().getResponseEntity().getText());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
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
        if (!priceField.getText().isBlank()) {
            try {
                handleEditProduct("price", priceField.getText());
                informationTable.getChildren().remove(priceField);
                priceLabel.setText(priceField.getText());
                priceField.setText("");
                editPrice.setOnMouseClicked(e -> changePrice());
                priceError.setText("");
            } catch (NumberFormatException e) {
                priceError.setText("Enter a valid number!");
            } catch (ResourceException e) {
                if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                    logout();
                else {
                    try {
                        companyError.setText(RequestHandler.getClientResource().getResponseEntity().getText());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
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
                    requestQueries.clear();
                    requestQueries.put("addedQuantity", Integer.toString(count));
                    RequestHandler.put("/accounts/seller_account_controller/product_count/decrease/", product.getProductId(), requestQueries, true, null);
                    quantityLabel.setText(Integer.toString(Integer.parseInt(quantityLabel.getText()) - count));
                } else {
                    requestQueries.clear();
                    requestQueries.put("addedQuantity", Integer.toString(count));
                    RequestHandler.put("/accounts/seller_account_controller/product_count/decrease/", product.getProductId(), requestQueries, true, null);
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
            } catch (ResourceException e) {
                if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                    logout();
                else {
                    try {
                        quantityError.setText(RequestHandler.getClientResource().getResponseEntity().getText());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
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
                handleEditProduct("status", "editing");
                statusLabel.setText("editing");
            } else if(confirmButton.isSelected()) {
                handleEditProduct("status", "confirmed");
                statusLabel.setText("confirmed");
            } else {
                handleEditProduct("status", "creating");
                statusLabel.setText("creating");
            }
            informationTable.add(statusLabel, 2, 6);
            informationTable.getChildren().remove(radioButtons);
            editStatus.setText("edit");
            editStatus.setOnMouseClicked(e -> changeStatus());
        } catch (ResourceException e) {
            if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                logout();
        }
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
