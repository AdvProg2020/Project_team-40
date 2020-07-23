package client.view.account_menus.seller_view.sellers_products_view;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.ChangeListener;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import exceptions.AccountsException;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import server.model.Category;
import server.model.Product;
import server.model.enumerations.PropertyType;
import client.view.MenuManager;
import client.view.ValidInput;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AddProductManager extends MenuManager implements Initializable {
    private ToggleGroup toggleGroupCategory;
    private HashMap<String, JFXTextField> valuePropertyField;
    private HashMap<String, JFXTextField> stringPropertyField;
    private VBox propertyBox;
    public VBox informationBox;
    public Label categoryError;
    public JFXTextField nameField;
    public JFXTextField companyField;
    public JFXTextField priceField;
    public JFXTextField countField;
    public JFXTextField descriptionField;
    public Label nameError;
    public Label companyError;
    public Label priceError;
    public Label countError;
    public JFXButton doneButton;
    private HashMap<String, String> requestQueries;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTextFieldsListeners();
        requestQueries = new HashMap<>();
        toggleGroupCategory = new ToggleGroup();
        HashMap<String, Category> categoryHashMap =  RequestHandler.get("/accounts/seller_account_controller/all_categories/",
                requestQueries, true, new TypeToken<HashMap<String, Category>>(){}.getType());
        assert categoryHashMap != null;
        for(Category category : categoryHashMap.values()) {
            RadioButton categoryButton = new RadioButton(category.getName());
            categoryButton.setOnMouseClicked(e -> showCategoryProperties(categoryButton));
            toggleGroupCategory.getToggles().add(categoryButton);
            informationBox.getChildren().add(categoryButton);
        }
        informationBox.getChildren().add(propertyBox = new VBox());
    }

    private void addTextFieldsListeners() {
        nameField.textProperty().addListener(new ChangeListener(30, nameField));
        companyField.textProperty().addListener(new ChangeListener(50, companyField));
        priceField.textProperty().addListener(new ChangeListener(20, priceField));
        countField.textProperty().addListener(new ChangeListener(10, countField));
    }

    private void showCategoryProperties(RadioButton radioButton) {
        requestQueries.clear();
        requestQueries.put("category", radioButton.getText());
        HashMap<String, PropertyType> categoryProperties = RequestHandler.get("/accounts/seller_account_controller/category_properties/",
                requestQueries, true , new TypeToken<HashMap<String, PropertyType>>(){}.getType());
        assert categoryProperties != null;
        propertyBox.getChildren().clear();
        constructHashMaps(categoryProperties);
    }

    private void constructHashMaps(HashMap<String, PropertyType> categoryProperties) {
        valuePropertyField = new HashMap<>();
        stringPropertyField = new HashMap<>();
        for(String property : categoryProperties.keySet()) {
            JFXTextField field =  new JFXTextField();
            if(categoryProperties.get(property).equals(PropertyType.RANGE))
                valuePropertyField.put(property, field);
            else
                stringPropertyField.put(property, field);
            field.setPrefWidth(290);
            field.setPrefHeight(61);
            field.setPromptText(property);
            propertyBox.getChildren().add(field);
        }
    }

    public void createProduct() {
        boolean hasError = hasPropertiesError();
        hasError = validateNameOrCompany(hasError, nameField, nameError);
        hasError = validateNameOrCompany(hasError, companyField, companyError);

        if(priceField.getText().isBlank()) {
            priceError.setText("Enter price!");
            hasError = true;
        } else if(!ValidInput.DOUBLE.getStringMatcher(priceField.getText()).matches()) {
            priceError.setText("Enter a number!");
            hasError = true;
        } else {
            priceError.setText("");
        }

        if(countField.getText().isBlank()) {
            countError.setText("Enter count!");
            hasError = true;
        } else if(!ValidInput.INTEGER.getStringMatcher(countField.getText()).matches()) {
            countError.setText("Enter a number!");
            hasError = true;
        } else {
            countError.setText("");
        }

        if(!hasError)
            finalizeCreatingProduct();
    }

    private boolean validateNameOrCompany(boolean hasError, JFXTextField field, Label error) {
        if(field.getText().isBlank()) {
            error.setText("Fill this field!");
            hasError = true;
        } else {
            error.setText("");
        }
        return hasError;
    }

    private boolean hasPropertiesError() {
        if(valuePropertyField == null) {
            categoryError.setText("Choose a category!");
            return true;
        } else if(isAFieldEmpty()) {
            categoryError.setText("Fill all fields!");
            return true;
        }
        categoryError.setText("");
        return false;
    }

    private void finalizeCreatingProduct() {
        try {
            HashMap<String, Double> extraValueProperties = validateExtraValueProperties();
            requestQueries.clear();
            requestQueries.put("name", nameField.getText());
            requestQueries.put("price", priceField.getText());
            requestQueries.put("description", descriptionField.getText());
            requestQueries.put("company", companyField.getText());
            requestQueries.put("category", ((RadioButton) toggleGroupCategory.getSelectedToggle()).getText());
            requestQueries.put("quantity", countField.getText());

            Product product = RequestHandler.post("/accounts/seller_account_controller/product/", Client.getInstance().getUsername(), requestQueries,
                    true, Product.class);

            assert product != null;
            product.setExtraValueProperties(extraValueProperties);
            product.setExtraStringProperties(getExtraStringProperties());
            ((Stage)doneButton.getScene().getWindow()).close();
        } catch (ResourceException e) {
            if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
            {
                ((Stage)doneButton.getScene().getWindow()).close();
                logout();
            }
            else
                e.printStackTrace();
        } catch (AccountsException e) {
            System.err.println(e.getMessage());
        }
    }

    private HashMap<String, String> getExtraStringProperties() {
        HashMap<String, String> extraStringProperties = new HashMap<>();
        for(Map.Entry<String, JFXTextField> field : valuePropertyField.entrySet()) {
            extraStringProperties.put(field.getKey(), field.getValue().getText());
        }
        return extraStringProperties;
    }

    private HashMap<String, Double> validateExtraValueProperties() throws AccountsException {
        HashMap<String, Double> extraValueProperties = new HashMap<>();
        for(Map.Entry<String, JFXTextField> field : valuePropertyField.entrySet()) {
            if(!ValidInput.DOUBLE.getStringMatcher(field.getValue().getText()).matches()) {
                categoryError.setText("Enter valid inputs!");
                throw new AccountsException("Invalid input!");
            }
            extraValueProperties.put(field.getKey(), Double.parseDouble(field.getValue().getText()));
        }
        return extraValueProperties;
    }

    private boolean isAFieldEmpty() {
        boolean isEmpty = false;
        for(JFXTextField field : valuePropertyField.values()) {
            if(field.getText().isBlank())
                isEmpty = true;
        }

        for(JFXTextField field : stringPropertyField.values()) {
            if(field.getText().isBlank())
                isEmpty = true;
        }
        return isEmpty;
    }

    public void attach(){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(propertyBox.getScene().getWindow());
        if(file != null){
            //TODO complete
        }
    }

}
