package view.account_menus.seller_view.sellers_products_view;

import com.jfoenix.controls.JFXTextField;
import controller.accounts.SellerAccountController;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import model.Category;
import model.enumerations.PropertyType;
import view.MenuManager;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;

public class AddProductManager extends MenuManager implements Initializable {
    public VBox informationBox;
    private SellerAccountController sellerAccountController;
    private ToggleGroup toggleGroupCategory;
    private HashMap<String, Double> extraValueProperties;
    private HashMap<String, String> extraStringProperties;
    private HashMap<String, JFXTextField> valuePropertyField;
    private HashMap<String, JFXTextField> stringPropertyField;
    public VBox propertyBox;

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


    public void createProduct() {}
}
