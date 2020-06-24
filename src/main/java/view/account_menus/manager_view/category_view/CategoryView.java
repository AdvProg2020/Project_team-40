package view.account_menus.manager_view.category_view;

import com.jfoenix.controls.JFXButton;
import controller.accounts.ManagerAccountController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Category;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;

public class CategoryView implements Initializable {
    public Label nameLabel;
    public Label parentCategoryLabel;
    public JFXButton backButton;
    public ListView<String> productsList;
    public ListView<String> subCategoriesList;
    private ManagerAccountController managerAccountController;
    private Category category;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        managerAccountController = ManagerAccountController.getInstance();
    }

    public void setNameLabel(String name) {
        nameLabel.setText(name);
    }

    public void setParentCategoryLabel(String parentName) {
        parentCategoryLabel.setText(parentName);
    }

    public void setProductsList(ArrayList<String> products) {
        for (String product : products) {
            productsList.getItems().add(product);
        }
    }

    public void setSubCategoriesList(ArrayList<String> subCategories) {
        for (String category : subCategories) {
            subCategoriesList.getItems().add(category);
        }
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void handleCloseWindow(ActionEvent event) {
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }

}
