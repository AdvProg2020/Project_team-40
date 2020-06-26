package view.account_menus.seller_view.sellers_products_view;

import com.jfoenix.controls.JFXTextField;
import controller.accounts.SellerAccountController;
import exceptions.AccountsException;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import model.Category;
import model.Product;
import model.enumerations.PropertyType;
import view.MenuManager;
import view.ValidInput;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AddProductManager extends MenuManager implements Initializable {
    private SellerAccountController sellerAccountController;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toggleGroupCategory = new ToggleGroup();
        sellerAccountController = SellerAccountController.getInstance();
        HashMap<String, Category> categoryHashMap = sellerAccountController.getAllCategories();
        for(Category category : categoryHashMap.values()) {
            RadioButton categoryButton = new RadioButton(category.getName());
            categoryButton.setOnMouseClicked(e -> showCategoryProperties(categoryButton));
            toggleGroupCategory.getToggles().add(categoryButton);
            informationBox.getChildren().add(categoryButton);
        }
        informationBox.getChildren().add(propertyBox = new VBox());
    }

    private void showCategoryProperties(RadioButton radioButton) {
        HashMap<String, PropertyType> categoryProperties = sellerAccountController.
                getCategoryProperties(radioButton.getText());
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
            field.setPromptText(property);
            propertyBox.getChildren().add(field);
        }
    }

    public void createProduct() {
        boolean hasError = hasPropertiesError();
        hasError = validateNameOrCompany(hasError, nameField);
        hasError = validateNameOrCompany(hasError, companyField);

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

    private boolean validateNameOrCompany(boolean hasError, JFXTextField field) {
        if(field.getText().isBlank()) {
            field.setText("Fill this field!");
            hasError = true;
        } else {
            field.setText("");
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
            Product product = sellerAccountController.createNewProduct(nameField.getText(), companyField.getText(),
                    Double.parseDouble(priceField.getText()), Integer.parseInt(priceField.getText()),
                    ((RadioButton) toggleGroupCategory.getSelectedToggle()).getText(), descriptionField.getText());
            product.setExtraValueProperties(extraValueProperties);
            product.setExtraStringProperties(getExtraStringProperties());
        } catch (AccountsException e) {
            e.printStackTrace();
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
}
