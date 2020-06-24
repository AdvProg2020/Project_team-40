package view.account_menus.manager_view.category_view;

import com.jfoenix.controls.JFXButton;
import controller.accounts.AccountController;
import controller.accounts.ManagerAccountController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Category;
import model.users.Seller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CategoryMenuManager implements Initializable {
    public JFXButton addCategory;
    public JFXButton refreshButton;
    public VBox vBoxItems;
    public Label mainLabel;
    public AnchorPane mainPane;
    ManagerAccountController managerAccountController;
    AccountController accountController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accountController = AccountController.getInstance();
        managerAccountController = controller.accounts.ManagerAccountController.getInstance();
        if(accountController.getThisUser() instanceof Seller) {
            initializeForSeller();
        }
        loadCategories();
    }

    private void initializeForSeller() {
        mainLabel.setText("Categories");
        mainPane.getChildren().remove(addCategory);
        mainPane.getChildren().remove(refreshButton);
    }

    private void loadCategories() {
        vBoxItems.getChildren().clear();
        for (String categoryName : managerAccountController.getAllCategories()) {
            try {
                Category category = Category.getCategoryByName(categoryName);
                AnchorPane item = (AnchorPane) FXMLLoader.load(getClass().
                        getResource("/layouts/manager_menus/manager_category_menus/category_item.fxml"));
                HBox hBox = (HBox) item.getChildren().get(0);
                setLabelsContent(category, hBox);
                vBoxItems.getChildren().add(item);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setLabelsContent(Category category, HBox hBox) {
        Label nameLabel =(Label) hBox.getChildren().get(0);
        Label parentCategoryLabel = (Label) hBox.getChildren().get(1);
        nameLabel.setText(category.getName());
        parentCategoryLabel.setText(category.getParentCategoryName());
    }

    public void handleAddCategory() {
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layouts/manager_menus/manager_category_menus/add_category.fxml"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 1200, 600));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    loadCategories();
                }
            });
        }
    }

    public void handleRefresh() {
        loadCategories();
    }
}
