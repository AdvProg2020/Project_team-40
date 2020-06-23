package view.account_menus.manager_view.category_view;

import com.jfoenix.controls.JFXButton;
import controller.accounts.AccountController;
import controller.accounts.ManagerAccountController;
import exceptions.AccountsException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Category;
import model.users.Seller;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoryItem implements Initializable {
    public JFXButton deleteCategoryButton;
    public JFXButton viewCategoryButton;
    public JFXButton editCategoryButton;
    public AnchorPane categoryPane;
    private ManagerAccountController managerAccountController;
    private AccountController accountController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accountController = AccountController.getInstance();
        if(accountController.getThisUser() instanceof Seller) {
            categoryPane.getChildren().remove(deleteCategoryButton);
            categoryPane.getChildren().remove(editCategoryButton);
        } else {
            managerAccountController = ManagerAccountController.getInstance();
        }
    }

    private void loadCategories(VBox vBoxItems) {
        vBoxItems.getChildren().clear();
        for (String categoryName : managerAccountController.getAllCategories()) {
            try {
                Category category = Category.getCategoryByName(categoryName);
                AnchorPane item = (AnchorPane) FXMLLoader.load(getClass().getResource("/layouts/manager_menus/manager_category_menus/category_item.fxml"));
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
        Label nameLabel =((Label) hBox.getChildren().get(0));
        Label parentCategoryLabel =((Label) hBox.getChildren().get(1));
        nameLabel.setText(category.getName());
        parentCategoryLabel.setText(category.getParentCategoryName());
    }


    private void setLabelsContent(CategoryView categoryView, Category category) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                categoryView.setCategory(category);
                categoryView.setNameLabel(category.getName());
                categoryView.setParentCategoryLabel(category.getParentCategoryName());
                categoryView.setProductsList(category.getProductIDs());
                categoryView.setSubCategoriesList(category.getSubCategoriesNames());
            }
        });
    }

        public void handleDeleteCategory(ActionEvent event) {
            HBox item = (HBox)((deleteCategoryButton.getParent()).getParent());
            VBox items =(VBox)(item.getParent()).getParent();
            String name =((Label)item.getChildren().get(0)).getText();
            try {
                managerAccountController.removeCategory(name);
            } catch (AccountsException e) {
                System.err.println(e.getMessage());
            }
            Platform.runLater(() -> loadCategories(items));
    }


    public void handleViewCategory(ActionEvent event) {
        HBox item = (HBox)((viewCategoryButton.getParent()).getParent());
        String name =((Label)item.getChildren().get(0)).getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/manager_menus/manager_category_menus/category.fxml"));
        try {
            Category category = Category.getCategoryByName(name);
            AnchorPane pane = loader.load();
            CategoryView categoryView = loader.getController();
            setLabelsContent(categoryView, category);
            Stage userWindow = new Stage();
            userWindow.setScene(new Scene(pane, 1300, 550));
            userWindow.initModality(Modality.APPLICATION_MODAL);
            userWindow.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void handleEditCategory(ActionEvent event) {
        HBox item = (HBox) ((editCategoryButton.getParent()).getParent());
        String name = ((Label) item.getChildren().get(0)).getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/manager_menus/manager_category_menus/edit_category.fxml"));
        try {
            Category category = Category.getCategoryByName(name);
            AnchorPane pane = loader.load();
            CategoryEdit editCategoryController = loader.getController();
            editCategoryController.setCategory(category);
            editCategoryController.setOldValues();
            Stage userWindow = new Stage();
            userWindow.setScene(new Scene(pane, 800, 600));
            userWindow.initModality(Modality.APPLICATION_MODAL);
            userWindow.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
