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
import java.util.ResourceBundle;
import java.util.Set;

public class AddProductManager extends MenuManager implements Initializable {
    private SellerAccountController sellerAccountController;
    private ToggleGroup toggleGroupCategory;
    private HashMap<String, Double> extraValueProperties;
    private HashMap<String, String> extraStringProperties;
    private HashMap<String, JFXTextField> valuePropertyField;
    private HashMap<String, JFXTextField> stringPropertyField;
    public VBox propertyBox;
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

    }

    private void showCategoryProperties(RadioButton radioButton) {
        HashMap<String, PropertyType> categoryProperties = sellerAccountController.
                getCategoryProperties(radioButton.getText());
        propertyBox.getChildren().clear();
        extraStringProperties = new HashMap<>();
        extraValueProperties = new HashMap<>();
        valuePropertyField = new HashMap<>();
        stringPropertyField = new HashMap<>();
        initializeHashMap(extraStringProperties.keySet(), stringPropertyField);
        initializeHashMap(extraValueProperties.keySet(), valuePropertyField);
    }

    private void initializeHashMap(Set<String> keySet, HashMap<String, JFXTextField> propertyField) {
        for(String property : keySet) {
            JFXTextField field =  new JFXTextField();
            propertyField.put(property, field);
            field.setPromptText(property);
            propertyBox.getChildren().add(field);
        }
    }

    public void createProduct() {
        if(valuePropertyField == null) {
            categoryError.setText("Choose a category!");
        } else if(isAFieldEmpty()) {
            categoryError.setText("Fill all fields!");
        } else if(nameField.getText().isBlank()) {
            nameError.setText("Enter name!");
        } else if(companyField.getText().isBlank()) {
            companyError.setText("Enter company!");
        } else if(priceField.getText().isBlank()) {
            priceError.setText("Enter price!");
        } else if(countField.getText().isBlank()) {
            countError.setText("Enter count!");
        } else if(!ValidInput.INTEGER.getStringMatcher(countField.getText()).matches()) {
            countError.setText("Enter a number!");
        } else if(!ValidInput.DOUBLE.getStringMatcher(priceField.getText()).matches()) {
            priceError.setText("Enter a number!");
        } else {
            finalizeCreatingProduct();
        }
    }

    private void finalizeCreatingProduct() {
        try {
            Product product = sellerAccountController.createNewProduct(nameField.getText(), companyField.getText(),
                    Double.parseDouble(priceField.getText()), Integer.parseInt(priceField.getText()),
                    ((RadioButton) toggleGroupCategory.getSelectedToggle()).getText(), descriptionField.getText());
     //       product.setExtraValueProperties();
       //     product.setExtraValueProperties();
        } catch (AccountsException e) {
            e.printStackTrace();
        }
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
