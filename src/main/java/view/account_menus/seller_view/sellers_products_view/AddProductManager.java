package view.account_menus.seller_view.sellers_products_view;

import controller.accounts.SellerAccountController;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import model.Category;
import view.MenuManager;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AddProductManager extends MenuManager implements Initializable {
    public VBox informationBox;
    private SellerAccountController sellerAccountController;
    private ToggleGroup toggleGroupCategory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toggleGroupCategory = new ToggleGroup();
        sellerAccountController = SellerAccountController.getInstance();
        HashMap<String, Category> categoryHashMap = sellerAccountController.getAllCategories();
        for(Category category : categoryHashMap.values()) {
            RadioButton categoryButton = new RadioButton(category.getName());
            toggleGroupCategory.getToggles().add(categoryButton);
            informationBox.getChildren().add(categoryButton);
        }
    }

    public void createProduct() {}
}
